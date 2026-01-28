package com.dofast.module.wms.api.ERPApi;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.dofast.framework.common.util.http.HttpUtils;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.controller.admin.interfacelog.vo.InterfaceLogCreateReqVO;
import com.dofast.module.mes.service.interfacelog.InterfaceLogService;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.system.dal.dataobject.dict.DictDataDO;
import com.dofast.module.system.service.dict.DictDataService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class WorkorderERPAPI {

    @Resource
    private InterfaceLogService interfaceLogService;

    @Resource
    private DictDataService dictDataService;

    @Resource
    private AdminUserApi adminUserApi;

    /**
     * 工单领/退料单生成接口
     */
    public String workOrderIssueCreate(Map<String, Object> params) {
        Map<String, Object> request = createBaseRequest("WorkOrderIssueCreate");

        DictDataDO dataDO = dictDataService.getDictData("erp_close_date", "close_date");
        String closeDateStr = null;
        Date closeDateObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (dataDO != null) {
            closeDateStr = dataDO.getLabel();
            if (closeDateStr != null && !closeDateStr.isEmpty()) {
                try {
                    closeDateObj = sdf.parse(closeDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        // 构建details
        List<Map<String, Object>> details = new ArrayList<>();
        List<Map<String, Object>> goodsList = (List<Map<String, Object>>) params.get("goodsList"); // 商品详情
        for (Map<String, Object> good : goodsList) {
            Map<String, Object> detail = new HashMap<>();
            detail.put("sfdc001", good.get("sfdc001")); // 工单单号
            detail.put("sfdc002", good.get("sfdc002")); // 工单项次
            detail.put("sfdc003", good.get("sfdc003")); // 工单项序
            detail.put("sfdc007", good.get("sfdc007")); // 申请数量
            detail.put("sfdc012", good.get("sfdc012")); // 仓库
            detail.put("sfdc013", good.get("sfdc013")); // 储位
            detail.put("sfdc014", good.get("sfdc014")); // 批号
            detail.put("sfdc015", good.get("sfdc015")); // 理由码
            detail.put("sfdc016", good.get("sfdc016")); // 库存管理特征
            detail.put("source_seq", good.get("source_seq")); // MES项次
            detail.put("typeud001", good.get("typeud001"));
            details.add(detail);
        }

        // 构建master
        Map<String, Object> master = new HashMap<>();
        master.put("sfdadocno", params.get("sfdadocno")); // 单别


        // 判断当前系统获取的时间与关账日期进行比对, 若当前时间大于关账日期则传递下个月1号的日期, 需要考虑跨年情况, 即12月跳转至下一年1月
        // 追加判定, 若关账日期为上个月, 则忽略, 传递今日日期

        // 判断关账日期是否为上个月
        boolean isCloseDateLastMonth = false;
        if (closeDateObj != null) {
            Calendar closeDateCal = Calendar.getInstance();
            closeDateCal.setTime(closeDateObj);

            Calendar lastMonthCal = Calendar.getInstance();
            lastMonthCal.add(Calendar.MONTH, -1);

            isCloseDateLastMonth = (closeDateCal.get(Calendar.YEAR) == lastMonthCal.get(Calendar.YEAR))
                    && (closeDateCal.get(Calendar.MONTH) == lastMonthCal.get(Calendar.MONTH));
        }

        // 设置过账日期（sfda001）
        if (isCloseDateLastMonth) {
            // 关账日期为上个月，直接使用当前日期
            master.put("sfda001", sdf.format(new Date()));
            master.put("sfdadocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        } else {
            // 否则检查当前日期是否超过关账日期
            /*if (closeDateObj != null && new Date().compareTo(closeDateObj) > 0) {
                // 当前日期超过关账日期，设置为下个月1号
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                master.put("sfda001", sdf.format(calendar.getTime()));
            } else {
                // 否则使用当前日期
                master.put("sfda001", sdf.format(new Date()));
            }*/
            // 2025-6-26 若当前存在关账日期, 则直接传递下个月
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            master.put("sfda001", sdf.format(calendar.getTime()));
            // master.put("sfdadocdt", new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // 单据日期
            master.put("sfdadocdt", sdf.format(calendar.getTime())); // 单据日期

        }

        //master.put("sfda001", new SimpleDateFormat("yyyy-MM-dd").format(new Date())); // 过账日期
        master.put("sfda002", params.get("sfda002")); // 发退料类别

        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        master.put("sfda004", adminUserRespDTO.getUsername()); // 申请人员

        master.put("source_no", params.get("source_no")); // MES单号
        master.put("detail", details); // 将details放入master

        // 构建parameter
        Map<String, Object> parameter = new HashMap<>();
        List<Map<String, Object>> masterList = new ArrayList<>();
        masterList.add(master);
        parameter.put("master", masterList);

        // 构建payload
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> stdData = new HashMap<>();
        stdData.put("parameter", parameter);
        payload.put("std_data", stdData);

        request.put("payload", payload);

        String result = HttpUtils.doPost("http://192.168.127.7/wstopprd/ws/r/awsp920", JSONObject.toJSONString(request)); //正式区
        // String result = HttpUtils.doPost("http://192.168.127.7/wtoptst/ws/r/awsp920", JSONObject.toJSONString(request)); //测试区

        // 记录操作日志
        InterfaceLogCreateReqVO log = new InterfaceLogCreateReqVO();
        log.setInterfaceName("工单领/退料单生成接口");
        log.setReceiver("ERP");
        log.setRequester("MES");
        log.setRequestType("POST");
        log.setRequestMap(JSONObject.toJSONString(request));
        log.setResultMap(result);
        interfaceLogService.createInterfaceLog(log);

        JSONObject jsonObject = JSONObject.parseObject(result);
        // 防止jsonObject.getString("code")空指针异常
        if (jsonObject == null) {
            return "error";
        }
        // 提取执行状态信息
        JSONObject reqPayload = jsonObject.getJSONObject("payload");
        JSONObject reqStdData = payload != null ? reqPayload.getJSONObject("std_data") : null;
        JSONObject reqExecution = stdData != null ? reqStdData.getJSONObject("execution") : null;
        String code = reqExecution != null ? reqExecution.getString("code") : null;
        String description = reqExecution != null ? reqExecution.getString("description") : null;

        if ("0".equals(code)) {
            return description != null ? description : "error";
        }
        return result;
    }

    /**
     * 完工入库单生成接口
     */
    public String workOrderFinishCreate(Map<String, Object> params) {
        Map<String, Object> request = createBaseRequest("WorkOrderFinishCreate");

        // 获取多个工单数据（假设传入参数包含workOrders列表）
        List<Map<String, Object>> workOrders = (List<Map<String, Object>>) params.get("workOrders");
        List<Map<String, Object>> masterList = new ArrayList<>();

        DictDataDO dataDO = dictDataService.getDictData("erp_close_date", "close_date");
        String closeDateStr = null;
        Date closeDateObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (dataDO != null) {
            closeDateStr = dataDO.getLabel();
            if (closeDateStr != null && !closeDateStr.isEmpty()) {
                try {
                    closeDateObj = sdf.parse(closeDateStr);
                } catch (ParseException e) {
                    e.printStackTrace(); // Handle parsing exception as needed
                }
            }
        }

        // 遍历每个工单构建数据
        for (Map<String, Object> order : workOrders) {
            // 构建details
            List<Map<String, Object>> details = new ArrayList<>();
            List<Map<String, Object>> goodsList = (List<Map<String, Object>>) order.get("goodsList");
            for (Map<String, Object> good : goodsList) {
                Map<String, Object> detail = new HashMap<>();
                detail.put("sfeb001", good.get("sfeb001"));  // 工单单号
                detail.put("sfeb003", good.get("sfeb003"));  // 入库类型
                detail.put("sfeb004", good.get("sfeb004"));  // 料号
                detail.put("sfeb005", good.get("sfeb005"));  // 产品特征
                detail.put("sfeb008", good.get("sfeb008"));  // 申请数量
                detail.put("sfeb013", good.get("sfeb013"));  // 库位
                detail.put("sfeb014", good.get("sfeb014"));  // 储位
                detail.put("sfeb015", good.get("sfeb015"));  // 批号
                detail.put("source_seq", good.get("source_seq")); // MES项次
                details.add(detail);
            }

            // 构建单个工单的master
            Map<String, Object> master = new HashMap<>();
            master.put("source_no", order.get("source_no"));    // MES单号
            master.put("sfeadocno", order.get("sfeadocno"));    // 单别

            // 判断当前系统获取的时间与关账日期进行比对, 若当前时间大于关账日期则传递下个月1号的日期, 需要考虑跨年情况, 即12月跳转至下一年1月
            // 追加判定, 若关账日期为上个月, 则忽略, 传递今日日期
            // 判断关账日期是否为上个月
            boolean isCloseDateLastMonth = false;
            if (closeDateObj != null) {
                Calendar closeDateCal = Calendar.getInstance();
                closeDateCal.setTime(closeDateObj);
                Calendar lastMonthCal = Calendar.getInstance();
                lastMonthCal.add(Calendar.MONTH, -1);
                isCloseDateLastMonth = (closeDateCal.get(Calendar.YEAR) == lastMonthCal.get(Calendar.YEAR))
                        && (closeDateCal.get(Calendar.MONTH) == lastMonthCal.get(Calendar.MONTH));
            }
            // 设置过账日期（sfda001）
            if (isCloseDateLastMonth) {
                // 关账日期为上个月，直接使用当前日期
                master.put("sfea001", sdf.format(new Date()));
                master.put("sfeadocdt", order.get("sfeadocdt"));
            } else {
                // 否则检查当前日期是否超过关账日期
                /*if (closeDateObj != null && new Date().compareTo(closeDateObj) > 0) {
                    // 当前日期超过关账日期，设置为下个月1号
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, 1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    master.put("sfea001", sdf.format(calendar.getTime()));
                } else {
                    // 否则使用当前日期
                    master.put("sfea001", sdf.format(new Date()));
                }*/
                // 2025-6-26 若当前存在关账日期, 则直接传递下个月
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, 1);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                master.put("sfea001", sdf.format(calendar.getTime()));
                // master.put("sfeadocdt", order.get("sfeadocdt"));    // 单据日期
                master.put("sfeadocdt", sdf.format(calendar.getTime()));    // 单据日期
            }
            //master.put("sfea001", order.get("sfea001"));        // 过账日期
            master.put("sfea002", order.get("sfea002"));        // 申请人员
            master.put("detail", details);                      // 当前工单的明细

            masterList.add(master);
        }

        // 构建parameter（支持多个master）
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("master", masterList);  // 这里改为列表结构

        // 构建payload
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> stdData = new HashMap<>();
        stdData.put("parameter", parameter);
        payload.put("std_data", stdData);
        request.put("payload", payload);

        // 发送请求
        String result = HttpUtils.doPost("http://192.168.127.7/wstopprd/ws/r/awsp920", JSONObject.toJSONString(request)); //正式区
        // String result = HttpUtils.doPost("http://192.168.127.7/wtoptst/ws/r/awsp920", JSONObject.toJSONString(request));
        // String result = "ERROR";

        // 记录操作日志
        InterfaceLogCreateReqVO log = new InterfaceLogCreateReqVO();
        log.setInterfaceName("完工入库单生成接口");
        log.setReceiver("ERP");
        log.setRequester("MES");
        log.setRequestType("POST");
        log.setRequestMap(JSONObject.toJSONString(request));
        log.setResultMap(result);
        interfaceLogService.createInterfaceLog(log);

        JSONObject jsonObject = JSONObject.parseObject(result);

        if (jsonObject == null) {
            return "error";
        }

        /*// 提取执行状态信息
        JSONObject reqPayload = jsonObject.getJSONObject("payload");
        JSONObject reqStdData = payload != null ? reqPayload.getJSONObject("std_data") : null;
        JSONObject reqExecution = stdData != null ? reqStdData.getJSONObject("execution") : null;
        String code = reqExecution != null ? reqExecution.getString("code") : null;
        String description = reqExecution != null ? reqExecution.getString("description") : null;

        if ("0".equals(code)) {
            return description != null ? description : "error";
        }*/
        return result;
    }


    /**
     * 报工单生成接口
     */
    public String workOrderReportCreate(Map<String, Object> params) {
        Map<String, Object> request = createBaseRequest("WorkingProcedureCreate");

        DictDataDO dataDO = dictDataService.getDictData("erp_close_date", "close_date");
        String closeDateStr = null;
        Date closeDateObj = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        if (dataDO != null) {
            closeDateStr = dataDO.getLabel();
            if (closeDateStr != null && !closeDateStr.isEmpty()) {
                try {
                    closeDateObj = sdf.parse(closeDateStr);
                } catch (ParseException e) {
                    e.printStackTrace(); // Handle parsing exception as needed
                }
            }
        }

        // 构建master
        Map<String, Object> master = new HashMap<>();
        master.put("sffbdocno", ""); // 单别
        master.put("sffbdocdt", null); // 单据日期
        master.put("sffb001", "3"); // 报工类别 默认值3
        master.put("sffb002", params.get("sffb002")); // 报工人员
        master.put("sffb005", params.get("sffb005")); // 工单号
        master.put("sffb007", params.get("sffb007")); // 作业编号(工序编号)
        String sffb008 = params.get("sffb008") == null ? null : (String) params.get("sffb008");
        if(sffb008!="null"){
            master.put("sffb008", params.get("sffb008") == null ? null : params.get("sffb008")); // 作业(工作序)
        }
        master.put("sffb009", params.get("sffb009")); // 工作站
        master.put("sffb010", params.get("sffb010")); // 设备编码

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        master.put("sffb013", timeFormat.format(new Date()));
        master.put("sffb014", params.get("sffb014")); // 工时(分)
        master.put("sffb015", params.get("sffb015")); // 机时(分)
        master.put("sffb016", params.get("sffb016")); // 单位
        master.put("sffb017", params.get("sffb017")); // 良品数量
        master.put("sffb018", params.get("sffb018")); // 不良品数量
        master.put("source_no", params.get("source_no")); // MES单号

        boolean isCloseDateLastMonth = false;
        if (closeDateObj != null) {
            Calendar closeDateCal = Calendar.getInstance();
            closeDateCal.setTime(closeDateObj);
            Calendar lastMonthCal = Calendar.getInstance();
            lastMonthCal.add(Calendar.MONTH, -1);
            isCloseDateLastMonth = (closeDateCal.get(Calendar.YEAR) == lastMonthCal.get(Calendar.YEAR))
                    && (closeDateCal.get(Calendar.MONTH) == lastMonthCal.get(Calendar.MONTH));
        }
        // 设置过账日期（sfda001）
        if (isCloseDateLastMonth) {
            // 关账日期为上个月，直接使用当前日期
            master.put("sfea001", sdf.format(new Date()));
            master.put("sffbdocdt", sdf.format(new Date())); // 单据日期
            master.put("sffb012", sdf.format(new Date())); // 完成日期
        } else {
            // 否则检查当前日期是否超过关账日期
                /*if (closeDateObj != null && new Date().compareTo(closeDateObj) > 0) {
                    // 当前日期超过关账日期，设置为下个月1号
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, 1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    master.put("sfea001", sdf.format(calendar.getTime()));
                } else {
                    // 否则使用当前日期
                    master.put("sfea001", sdf.format(new Date()));
                }*/
            // 2025-6-26 若当前存在关账日期, 则直接传递下个月
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            master.put("sfea001", sdf.format(calendar.getTime()));
            master.put("sffbdocdt", sdf.format(calendar.getTime())); // 单据日期
            master.put("sffb012", sdf.format(calendar.getTime())); // 完成日期
        }
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> stdData = new HashMap<>();
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("master", Collections.singletonList(master));
        stdData.put("parameter", parameter);
        payload.put("std_data", stdData);
        request.put("payload", payload);

        // 发送请求
        // String url = "http://192.168.6.215/wtoptst/ws/r/awsp920"; // 测试地址
        String result = HttpUtils.doPost("http://192.168.127.7/wstopprd/ws/r/awsp920", JSONObject.toJSONString(request)); //正式区
        //String result = "ERROR";
        // String result = HttpUtils.doPost("http://192.168.127.7/wtoptst/ws/r/awsp920", JSONObject.toJSONString(request));

        // 记录日志
        InterfaceLogCreateReqVO log = new InterfaceLogCreateReqVO();
        log.setInterfaceName("报工单资料生成接口");
        log.setReceiver("ERP");
        log.setRequester("MES");
        log.setRequestType("POST");
        log.setRequestMap(JSONObject.toJSONString(request));
        log.setResultMap(result);
        interfaceLogService.createInterfaceLog(log);

        JSONObject jsonObject = JSONObject.parseObject(result);
        // 防止jsonObject.getString("code")空指针异常
        if (jsonObject == null) {
            return "error";
        }

        // 提取执行状态信息
        JSONObject reqPayload = jsonObject.getJSONObject("payload");
        JSONObject reqStdData = payload != null ? reqPayload.getJSONObject("std_data") : null;
        JSONObject reqExecution = stdData != null ? reqStdData.getJSONObject("execution") : null;
        String code = reqExecution != null ? reqExecution.getString("code") : null;
        String description = reqExecution != null ? reqExecution.getString("description") : null;

        if ("0".equals(code)) {
            // 若当前为采购收货, 则解析ERP回传的收货单号用于绑定入库单号
            // 解析入库单号（source_no）
            JSONObject reqParameter = stdData != null ? reqStdData.getJSONObject("parameter") : null;
            JSONArray successReturn = parameter != null ? reqParameter.getJSONArray("success_return") : null;
            String reqERPCode = null;
            if (successReturn != null && !successReturn.isEmpty()) {
                JSONObject firstSuccess = successReturn.getJSONObject(0);
                reqERPCode = firstSuccess != null ? firstSuccess.getString("success_msg") : null; // 获取到的收货单号
            }
            // 示例：记录日志或处理sourceNo
            if (reqERPCode != null) {
                System.out.println("解析到的source_no: " + reqERPCode);
                return "SUCCESS," + reqERPCode; // 返回sourceNo
            } else {
                return "ERROR: 未找到ERP入库单号!";
            }
        } else {
            return description != null ? description : "error";
        }
        //return result;

    }


    /**
     * 撤销单据接口
     *
     * @param params
     * @return
     */
    public String docRollback(Map<String, Object> params) {
        Map<String, Object> request = createBaseRequest("DocRollback");
        String feedbackCOde = (String) params.get("feedbackCode");
        // 构建master
        Map<String, Object> master = new HashMap<>();
        master.put("doc_type", "1"); // 单据类型
        master.put("doc_no", feedbackCOde); // 单据编号
        Map<String, Object> payload = new HashMap<>();
        Map<String, Object> stdData = new HashMap<>();
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("master", Collections.singletonList(master));
        stdData.put("parameter", parameter);
        payload.put("std_data", stdData);
        request.put("payload", payload);
        System.out.println(JSONObject.toJSONString(request));
        // 发送请求
        String result = HttpUtils.doPost("http://192.168.127.7/wstopprd/ws/r/awsp920", JSONObject.toJSONString(request)); //正式区

        // String result = HttpUtils.doPost("http://192.168.127.7/wtoptst/ws/r/awsp920", JSONObject.toJSONString(request));
        // 记录日志
        InterfaceLogCreateReqVO log = new InterfaceLogCreateReqVO();
        log.setInterfaceName("撤销报工资料生成接口");
        log.setReceiver("ERP");
        log.setRequester("MES");
        log.setRequestType("POST");
        log.setRequestMap(JSONObject.toJSONString(request));
        log.setResultMap(result);
        interfaceLogService.createInterfaceLog(log);

        JSONObject jsonObject = JSONObject.parseObject(result);
        // 防止jsonObject.getString("code")空指针异常
        if (jsonObject == null) {
            return "error";
        }
        // 提取执行状态信息
        JSONObject reqPayload = jsonObject.getJSONObject("payload");
        JSONObject reqStdData = payload != null ? reqPayload.getJSONObject("std_data") : null;
        JSONObject reqExecution = stdData != null ? reqStdData.getJSONObject("execution") : null;
        String code = reqExecution != null ? reqExecution.getString("code") : null;
        String description = reqExecution != null ? reqExecution.getString("description") : null;

        if ("0".equals(code)) {
            return description != null ? description : "error";
        }
        return result;
    }


    private Map<String, Object> createBaseRequest(String interfaceName) {
        Map<String, Object> request = new HashMap<>();
        request.put("key", "f5458f5c0f9022db743a7c0710145903");
        request.put("type", "sync");

        Map<String, Object> hostInfo = new HashMap<>();
        hostInfo.put("prod", "MES");
        hostInfo.put("ip", getHostIp()); // 获取实际的IP地址
        hostInfo.put("lang", "zh_CN");
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        hostInfo.put("acct", adminUserRespDTO.getUsername());
        // hostInfo.put("acct", "tiptop");
        hostInfo.put("timestamp", String.valueOf(System.currentTimeMillis()));
        request.put("host", hostInfo);

        Map<String, Object> serviceInfo = new HashMap<>();
        serviceInfo.put("prod", "T100");
        serviceInfo.put("name", interfaceName);
        serviceInfo.put("ip", "192.168.127.7");
        serviceInfo.put("id", "topprd"); // 或者 "toptst"，根据环境选择
        request.put("service", serviceInfo);

        Map<String, Object> dataKey = new HashMap<>();
        // dataKey.put("EntId", Constant.ERP_PROD_DODE); // 根据文档要求修改
        dataKey.put("EntId", Constant.ERP_PROD_DODE);
        dataKey.put("CompanyId", "AM01");
        request.put("datakey", dataKey);

        return request;
    }

    private String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return "未知";
        }
    }
}