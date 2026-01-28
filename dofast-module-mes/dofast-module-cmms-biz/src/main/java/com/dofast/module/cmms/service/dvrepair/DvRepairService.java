package com.dofast.module.cmms.service.dvrepair;

import java.time.LocalDateTime;
import java.util.*;
import javax.validation.*;
import com.dofast.module.cmms.controller.admin.dvrepair.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvrepair.DvRepairDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 设备维修单 Service 接口
 *
 * @author 芋道源码
 */
public interface DvRepairService {
    /**
     * 检测维修单编号是否唯一
     * @param dvRepair
     * @return
     */
    public String checkCodeUnique(DvRepairBaseVO dvRepair);
    /**
     * 创建设备维修单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDvRepair(@Valid DvRepairCreateReqVO createReqVO);

    /**
     * 更新设备维修单
     *
     * @param updateReqVO 更新信息
     */
    void updateDvRepair(@Valid DvRepairUpdateReqVO updateReqVO);

    /**
     * 删除设备维修单
     *
     * @param id 编号
     */
    void deleteDvRepair(Long id);

    /**
     * 获得设备维修单
     *
     * @param id 编号
     * @return 设备维修单
     */
    DvRepairDO getDvRepair(Long id);

    /**
     * 获得设备维修单列表
     *
     * @param ids 编号
     * @return 设备维修单列表
     */
    List<DvRepairDO> getDvRepairList(Collection<Long> ids);

    /**
     * 获得设备维修单分页
     *
     * @param pageReqVO 分页查询
     * @return 设备维修单分页
     */
    PageResult<DvRepairDO> getDvRepairPage(DvRepairPageReqVO pageReqVO);

    /**
     * 获得设备维修单列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备维修单列表
     */
    List<DvRepairDO> getDvRepairList(DvRepairExportReqVO exportReqVO);

    /**
     * 查询当前周期内是否已生成点检维修单
     *
     * @param planId       点检计划ID
     * @param machineryId  设备ID
     * @param cycleStart   周期开始时间
     * @param cycleEnd     周期结束时间
     * @return 已存在数量
     */
    int countInspectionRepairInCycle(Long planId, Long machineryId, LocalDateTime cycleStart, LocalDateTime cycleEnd);

    void updateBatch(List<DvRepairDO> repairList);
}
