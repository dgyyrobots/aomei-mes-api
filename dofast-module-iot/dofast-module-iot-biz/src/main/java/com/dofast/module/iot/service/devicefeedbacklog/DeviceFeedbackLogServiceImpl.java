package com.dofast.module.iot.service.devicefeedbacklog;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.*;
import com.dofast.module.iot.controller.admin.devicefeedbacklog.vo.*;
import com.dofast.module.iot.dal.dataobject.devicefeedbacklog.DeviceFeedbackLogDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.iot.convert.devicefeedbacklog.DeviceFeedbackLogConvert;
import com.dofast.module.iot.dal.mysql.devicefeedbacklog.DeviceFeedbackLogMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.iot.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 设备产量日志 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class DeviceFeedbackLogServiceImpl implements DeviceFeedbackLogService {

    @Resource
    private DeviceFeedbackLogMapper deviceFeedbackLogMapper;

    @Override
    public Long createDeviceFeedbackLog(DeviceFeedbackLogCreateReqVO createReqVO) {
        // 插入
        DeviceFeedbackLogDO deviceFeedbackLog = DeviceFeedbackLogConvert.INSTANCE.convert(createReqVO);
        deviceFeedbackLogMapper.insert(deviceFeedbackLog);
        // 返回
        return deviceFeedbackLog.getId();
    }

    @Override
    public void updateDeviceFeedbackLog(DeviceFeedbackLogUpdateReqVO updateReqVO) {
        // 校验存在
        validateDeviceFeedbackLogExists(updateReqVO.getId());
        // 更新
        DeviceFeedbackLogDO updateObj = DeviceFeedbackLogConvert.INSTANCE.convert(updateReqVO);
        deviceFeedbackLogMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeviceFeedbackLog(Long id) {
        // 校验存在
        validateDeviceFeedbackLogExists(id);
        // 删除
        deviceFeedbackLogMapper.deleteById(id);
    }

    private void validateDeviceFeedbackLogExists(Long id) {
        if (deviceFeedbackLogMapper.selectById(id) == null) {
            throw exception(DEVICE_FEEDBACK_LOG_NOT_EXISTS);
        }
    }

    @Override
    public DeviceFeedbackLogDO getDeviceFeedbackLog(Long id) {
        return deviceFeedbackLogMapper.selectById(id);
    }

    @Override
    public List<DeviceFeedbackLogDO> getDeviceFeedbackLogList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return deviceFeedbackLogMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DeviceFeedbackLogDO> getDeviceFeedbackLogPage(DeviceFeedbackLogPageReqVO pageReqVO) {
        return deviceFeedbackLogMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DeviceFeedbackLogDO> getDeviceFeedbackLogList(DeviceFeedbackLogExportReqVO exportReqVO) {
        return deviceFeedbackLogMapper.selectList(exportReqVO);
    }

    @Override
    public DeviceFeedbackLogDO getFinalDeviceFeedbackLog(String deviceCode){
        return deviceFeedbackLogMapper.getFinalDeviceFeedbackLog(deviceCode);
    }

    @Override
    public int deleteByCreateTimeRange(String startTime, String endTime){
        return deviceFeedbackLogMapper.deleteByCreateTimeRange(startTime, endTime);
    }



}
