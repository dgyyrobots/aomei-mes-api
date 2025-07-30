package com.dofast.module.cmms.service.dvrepairline;

import java.util.*;
import javax.validation.*;
import com.dofast.module.cmms.controller.admin.dvrepairline.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvrepairline.DvRepairLineDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 设备维修单行 Service 接口
 *
 * @author 芋道源码
 */
public interface DvRepairLineService {

    /**
     * 创建设备维修单行
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDvRepairLine(@Valid DvRepairLineCreateReqVO createReqVO);

    /**
     * 更新设备维修单行
     *
     * @param updateReqVO 更新信息
     */
    void updateDvRepairLine(@Valid DvRepairLineUpdateReqVO updateReqVO);

    /**
     * 删除设备维修单行
     *
     * @param id 编号
     */
    void deleteDvRepairLine(Long id);

    /**
     * 获得设备维修单行
     *
     * @param id 编号
     * @return 设备维修单行
     */
    DvRepairLineDO getDvRepairLine(Long id);

    /**
     * 获得设备维修单行列表
     *
     * @param ids 编号
     * @return 设备维修单行列表
     */
    List<DvRepairLineDO> getDvRepairLineList(Collection<Long> ids);

    /**
     * 获得设备维修单行分页
     *
     * @param pageReqVO 分页查询
     * @return 设备维修单行分页
     */
    PageResult<DvRepairLineDO> getDvRepairLinePage(DvRepairLinePageReqVO pageReqVO);

    /**
     * 获得设备维修单行列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备维修单行列表
     */
    List<DvRepairLineDO> getDvRepairLineList(DvRepairLineExportReqVO exportReqVO);

    void insertBatch(List<DvRepairLineDO> repairLineList);

}
