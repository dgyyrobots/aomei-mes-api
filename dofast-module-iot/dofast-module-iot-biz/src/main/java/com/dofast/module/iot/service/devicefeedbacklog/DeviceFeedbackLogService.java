package com.dofast.module.iot.service.devicefeedbacklog;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.*;
import com.dofast.module.iot.controller.admin.devicefeedbacklog.vo.*;
import com.dofast.module.iot.dal.dataobject.devicefeedbacklog.DeviceFeedbackLogDO;
import com.dofast.framework.common.pojo.PageResult;
import org.apache.ibatis.annotations.Param;

/**
 * 设备产量日志 Service 接口
 *
 * @author 惠智造
 */
public interface DeviceFeedbackLogService {

    /**
     * 创建设备产量日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeviceFeedbackLog(@Valid DeviceFeedbackLogCreateReqVO createReqVO);

    /**
     * 更新设备产量日志
     *
     * @param updateReqVO 更新信息
     */
    void updateDeviceFeedbackLog(@Valid DeviceFeedbackLogUpdateReqVO updateReqVO);

    /**
     * 删除设备产量日志
     *
     * @param id 编号
     */
    void deleteDeviceFeedbackLog(Long id);

    /**
     * 获得设备产量日志
     *
     * @param id 编号
     * @return 设备产量日志
     */
    DeviceFeedbackLogDO getDeviceFeedbackLog(Long id);

    /**
     * 获得设备产量日志列表
     *
     * @param ids 编号
     * @return 设备产量日志列表
     */
    List<DeviceFeedbackLogDO> getDeviceFeedbackLogList(Collection<Long> ids);

    /**
     * 获得设备产量日志分页
     *
     * @param pageReqVO 分页查询
     * @return 设备产量日志分页
     */
    PageResult<DeviceFeedbackLogDO> getDeviceFeedbackLogPage(DeviceFeedbackLogPageReqVO pageReqVO);

    /**
     * 获得设备产量日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备产量日志列表
     */
    List<DeviceFeedbackLogDO> getDeviceFeedbackLogList(DeviceFeedbackLogExportReqVO exportReqVO);

    DeviceFeedbackLogDO getFinalDeviceFeedbackLog(String deviceCode);

    int deleteByCreateTimeRange(String startTime, String endTime);

}
