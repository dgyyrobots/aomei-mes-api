package com.dofast.module.iot.convert.devicefeedbacklog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.dofast.module.iot.controller.admin.devicefeedbacklog.vo.*;
import com.dofast.module.iot.dal.dataobject.devicefeedbacklog.DeviceFeedbackLogDO;

/**
 * 设备产量日志 Convert
 *
 * @author 惠智造
 */
@Mapper
public interface DeviceFeedbackLogConvert {

    DeviceFeedbackLogConvert INSTANCE = Mappers.getMapper(DeviceFeedbackLogConvert.class);

    DeviceFeedbackLogDO convert(DeviceFeedbackLogCreateReqVO bean);

    DeviceFeedbackLogDO convert(DeviceFeedbackLogUpdateReqVO bean);

    DeviceFeedbackLogRespVO convert(DeviceFeedbackLogDO bean);

    DeviceFeedbackLogUpdateReqVO convert01(DeviceFeedbackLogDO bean);


    List<DeviceFeedbackLogRespVO> convertList(List<DeviceFeedbackLogDO> list);

    PageResult<DeviceFeedbackLogRespVO> convertPage(PageResult<DeviceFeedbackLogDO> page);

    List<DeviceFeedbackLogExcelVO> convertList02(List<DeviceFeedbackLogDO> list);

}
