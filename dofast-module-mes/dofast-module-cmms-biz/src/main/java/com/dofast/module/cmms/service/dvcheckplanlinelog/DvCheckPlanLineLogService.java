package com.dofast.module.cmms.service.dvcheckplanlinelog;

import java.util.*;
import javax.validation.*;
import com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanlinelog.DvCheckPlanLineLogDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 点检计划记录单身 Service 接口
 *
 * @author 惠智造
 */
public interface DvCheckPlanLineLogService {

    /**
     * 创建点检计划记录单身
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDvCheckPlanLineLog(@Valid DvCheckPlanLineLogCreateReqVO createReqVO);

    /**
     * 更新点检计划记录单身
     *
     * @param updateReqVO 更新信息
     */
    void updateDvCheckPlanLineLog(@Valid DvCheckPlanLineLogUpdateReqVO updateReqVO);

    /**
     * 删除点检计划记录单身
     *
     * @param id 编号
     */
    void deleteDvCheckPlanLineLog(Long id);

    /**
     * 获得点检计划记录单身
     *
     * @param id 编号
     * @return 点检计划记录单身
     */
    DvCheckPlanLineLogDO getDvCheckPlanLineLog(Long id);

    /**
     * 获得点检计划记录单身列表
     *
     * @param ids 编号
     * @return 点检计划记录单身列表
     */
    List<DvCheckPlanLineLogDO> getDvCheckPlanLineLogList(Collection<Long> ids);

    /**
     * 获得点检计划记录单身分页
     *
     * @param pageReqVO 分页查询
     * @return 点检计划记录单身分页
     */
    PageResult<DvCheckPlanLineLogDO> getDvCheckPlanLineLogPage(DvCheckPlanLineLogPageReqVO pageReqVO);

    /**
     * 获得点检计划记录单身列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 点检计划记录单身列表
     */
    List<DvCheckPlanLineLogDO> getDvCheckPlanLineLogList(DvCheckPlanLineLogExportReqVO exportReqVO);

}
