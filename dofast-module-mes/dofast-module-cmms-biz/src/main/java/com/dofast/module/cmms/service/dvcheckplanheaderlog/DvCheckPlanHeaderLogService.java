package com.dofast.module.cmms.service.dvcheckplanheaderlog;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import javax.validation.*;
import com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog.DvCheckPlanHeaderLogDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 点检计划记录单头 Service 接口
 *
 * @author 惠智造
 */
public interface DvCheckPlanHeaderLogService {

    /**
     * 创建点检计划记录单头
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDvCheckPlanHeaderLog(@Valid DvCheckPlanHeaderLogCreateReqVO createReqVO);

    /**
     * 更新点检计划记录单头
     *
     * @param updateReqVO 更新信息
     */
    void updateDvCheckPlanHeaderLog(@Valid DvCheckPlanHeaderLogUpdateReqVO updateReqVO);

    /**
     * 删除点检计划记录单头
     *
     * @param id 编号
     */
    void deleteDvCheckPlanHeaderLog(Long id);

    /**
     * 获得点检计划记录单头
     *
     * @param id 编号
     * @return 点检计划记录单头
     */
    DvCheckPlanHeaderLogDO getDvCheckPlanHeaderLog(Long id);

    /**
     * 获得点检计划记录单头列表
     *
     * @param ids 编号
     * @return 点检计划记录单头列表
     */
    List<DvCheckPlanHeaderLogDO> getDvCheckPlanHeaderLogList(Collection<Long> ids);

    /**
     * 获得点检计划记录单头分页
     *
     * @param pageReqVO 分页查询
     * @return 点检计划记录单头分页
     */
    PageResult<DvCheckPlanHeaderLogDO> getDvCheckPlanHeaderLogPage(DvCheckPlanHeaderLogPageReqVO pageReqVO);

    /**
     * 获得点检计划记录单头列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 点检计划记录单头列表
     */
    List<DvCheckPlanHeaderLogDO> getDvCheckPlanHeaderLogList(DvCheckPlanHeaderLogExportReqVO exportReqVO);

    int selectCountInCycle(String planCode , LocalDateTime startTime , LocalDateTime endTime);

}
