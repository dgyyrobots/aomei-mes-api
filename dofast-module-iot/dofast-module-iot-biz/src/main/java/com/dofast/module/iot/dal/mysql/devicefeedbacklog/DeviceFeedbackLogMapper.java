package com.dofast.module.iot.dal.mysql.devicefeedbacklog;

import java.time.LocalDateTime;
import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.iot.dal.dataobject.devicefeedbacklog.DeviceFeedbackLogDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.iot.controller.admin.devicefeedbacklog.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 设备产量日志 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface DeviceFeedbackLogMapper extends BaseMapperX<DeviceFeedbackLogDO> {

    default PageResult<DeviceFeedbackLogDO> selectPage(DeviceFeedbackLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceFeedbackLogDO>()
                .eqIfPresent(DeviceFeedbackLogDO::getDeviceId, reqVO.getDeviceId())
                .likeIfPresent(DeviceFeedbackLogDO::getDeviceName, reqVO.getDeviceName())
                .eqIfPresent(DeviceFeedbackLogDO::getDeviceCode, reqVO.getDeviceCode())
                .eqIfPresent(DeviceFeedbackLogDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(DeviceFeedbackLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DeviceFeedbackLogDO::getEnableStatus, reqVO.getEnableStatus())
                .betweenIfPresent(DeviceFeedbackLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeviceFeedbackLogDO::getId));
    }

    default List<DeviceFeedbackLogDO> selectList(DeviceFeedbackLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeviceFeedbackLogDO>()
                .eqIfPresent(DeviceFeedbackLogDO::getDeviceId, reqVO.getDeviceId())
                .likeIfPresent(DeviceFeedbackLogDO::getDeviceName, reqVO.getDeviceName())
                .eqIfPresent(DeviceFeedbackLogDO::getDeviceCode, reqVO.getDeviceCode())
                .eqIfPresent(DeviceFeedbackLogDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(DeviceFeedbackLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DeviceFeedbackLogDO::getEnableStatus, reqVO.getEnableStatus())
                .betweenIfPresent(DeviceFeedbackLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeviceFeedbackLogDO::getId));
    }

    public DeviceFeedbackLogDO getFinalDeviceFeedbackLog(String deviceCode);


    public int deleteByCreateTimeRange(@Param("cutoffTime") String cutoffTime);

}
