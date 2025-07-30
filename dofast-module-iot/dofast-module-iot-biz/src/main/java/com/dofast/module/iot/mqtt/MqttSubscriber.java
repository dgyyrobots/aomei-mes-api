package com.dofast.module.iot.mqtt;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.dofast.module.cmms.api.dvmachinery.DvMachineryApi;
import com.dofast.module.cmms.api.dvmachinery.dto.DvMachineryDTO;
import com.dofast.module.iot.controller.admin.devicefeedbacklog.vo.DeviceFeedbackLogCreateReqVO;
import com.dofast.module.iot.convert.devicefeedbacklog.DeviceFeedbackLogConvert;
import com.dofast.module.iot.dal.dataobject.devicefeedbacklog.DeviceFeedbackLogDO;
import com.dofast.module.iot.service.devicefeedbacklog.DeviceFeedbackLogService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
public class MqttSubscriber {

    @Resource
    private IMqttClient mqttClient;

    @Resource
    private DeviceFeedbackLogService deviceFeedbackLogService;

    @Resource
    private DvMachineryApi machineryApi;

    private static final String SUBSCRIBE_TOPIC = "/+/+/property/post";
    private static final int SUBSCRIBE_QOS = 1;

    @PostConstruct
    public void init() {
        setupMqttCallback();
        initialSubscribe();
    }

    private void setupMqttCallback() {
        mqttClient.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                // log.info("[MQTT] 连接成功 | 重连状态: {}", reconnect);
                try {
                    if (reconnect) {
                        log.info("[MQTT] 执行重连后订阅恢复...");
                        doSubscribe();
                    }
                } catch (MqttException e) {
                    log.error("[MQTT] 重连后订阅恢复失败: {}", e.getMessage());
                }
            }

            @Override
            public void connectionLost(Throwable cause) {
                log.warn("[MQTT] 连接异常断开: {}", cause.getMessage());
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                // 消息处理已通过订阅回调实现
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // 发布消息时使用，订阅端无需处理
            }
        });
    }

    private void initialSubscribe() {
        try {
            if (mqttClient.isConnected()) {
                doSubscribe();
            } else {
                log.warn("[MQTT] 初始化订阅时连接未就绪");
            }
        } catch (MqttException e) {
            log.error("[MQTT] 初始订阅失败: {}", e.getMessage());
        }
    }

    private void doSubscribe() throws MqttException {
        try {
            mqttClient.subscribe(SUBSCRIBE_TOPIC, SUBSCRIBE_QOS, this::handleMessageArrived);
            // log.info("[MQTT] 成功订阅主题: {}", SUBSCRIBE_TOPIC);
        } catch (MqttException e) {
            log.error("[MQTT] 订阅主题失败 [{}] | 原因: {}", SUBSCRIBE_TOPIC, e.getMessage());
            throw e;
        }
    }

    private void handleMessageArrived(String topic, MqttMessage message) {
        try {
            String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
            // log.debug("[MQTT] 收到消息 | 主题: {} | 内容: {}", topic, payload);
            processPayload(topic, payload);
        } catch (Exception e) {
            log.error("[MQTT] 消息处理异常 | 主题: {} | 错误: {}", topic, e.getMessage());
        }
    }

    private void processPayload(String topic, String payload) {
        String deviceCode = extractDeviceCode(topic);
        JSONArray jsonArray = new JSONArray(payload);

        jsonArray.forEach(item -> {
            JSONObject jsonObject = (JSONObject) item;
            if ("cl".equals(jsonObject.getStr("id"))) {
                handleProductionData(deviceCode, jsonObject);
            }
        });
    }

    private String extractDeviceCode(String topic) {
        String[] parts = topic.split("/");
        return parts.length >= 3 ? parts[2] : "unknown";
    }

    private void handleProductionData(String deviceCode, JSONObject data) {
        try {
            BigDecimal production = new BigDecimal(data.getStr("value"));
            // log.info("设备 {} 最新产量: {} | 时间: {}", deviceCode, production, LocalDateTime.now());
            //String fullDeviceCode = deviceCode + "#"; 需要与ERP对接设备编码是否追加 " # "
            String fullDeviceCode = deviceCode;
            DvMachineryDTO machinery = machineryApi.getErpMachineryInfo(fullDeviceCode);
            if (machinery == null) {
                // log.warn("设备信息未找到: {}", fullDeviceCode);
                return;
            }
            handleProductionLogic(fullDeviceCode, production, machinery);
        } catch (Exception e) {
            // log.error("产量数据处理失败 | 设备: {} | 错误: {}", deviceCode, e.getMessage());
        }
    }

    private void handleProductionLogic(String deviceCode, BigDecimal production, DvMachineryDTO machinery) {
        // 获取上一条记录
        DeviceFeedbackLogDO lastLog = Optional.ofNullable(deviceFeedbackLogService.getFinalDeviceFeedbackLog(deviceCode)).orElse(null);
        // 比对production与lastLog的数量, 若当前production大于lastLog的数量, 则打上标记
        // 追加卡控, 若当前production大于lastLog的数量且涨幅超5%, 则打标记
        if (lastLog != null && production.multiply(new BigDecimal("1.1")).compareTo(lastLog.getQuantity()) < 0) {
            lastLog.setEnableStatus("CONFIRMED");
            deviceFeedbackLogService.updateDeviceFeedbackLog(
                    DeviceFeedbackLogConvert.INSTANCE.convert01(lastLog)
            );
        }
        DeviceFeedbackLogCreateReqVO logVO = new DeviceFeedbackLogCreateReqVO();
        logVO.setDeviceCode(deviceCode);
        logVO.setDeviceId(machinery.getId());
        logVO.setDeviceName(machinery.getMachineryName());
        logVO.setTenantId(158L);
        logVO.setQuantity(production);

        deviceFeedbackLogService.createDeviceFeedbackLog(logVO);
    }

    public void publish(String topic, String payload) throws MqttException {
        if (!mqttClient.isConnected()) {
            log.warn("发布消息时MQTT连接未就绪");
            return;
        }
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(SUBSCRIBE_QOS);
        mqttClient.publish(topic, message);
        log.debug("[MQTT] 消息已发布 | 主题: {}", topic);
    }
}