package com.dofast.module.wms.service.materialstock;

import java.util.*;
import javax.validation.*;
import com.dofast.module.wms.controller.admin.materialstock.vo.*;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.framework.common.pojo.PageResult;

/**
 * 库存记录 Service 接口
 *
 * @author 芋道源码
 */
public interface MaterialStockService {

    /**
     * 创建库存记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaterialStock(@Valid MaterialStockCreateReqVO createReqVO);

    /**
     * 更新库存记录
     *
     * @param updateReqVO 更新信息
     */
    void updateMaterialStock(@Valid MaterialStockUpdateReqVO updateReqVO);

    /**
     * 删除库存记录
     *
     * @param id 编号
     */
    void deleteMaterialStock(Long id);

    /**
     * 获得库存记录
     *
     * @param id 编号
     * @return 库存记录
     */
    MaterialStockDO getMaterialStock(Long id);

    /**
     * 获得库存记录列表
     *
     * @param ids 编号
     * @return 库存记录列表
     */
    List<MaterialStockDO> getMaterialStockList(Collection<Long> ids);

    /**
     * 获得库存记录分页
     *
     * @param pageReqVO 分页查询
     * @return 库存记录分页
     */
    PageResult<MaterialStockDO> getMaterialStockPage(MaterialStockPageReqVO pageReqVO);

    /**
     * 获得库存记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库存记录列表
     */
    List<MaterialStockDO> getMaterialStockList(MaterialStockExportReqVO exportReqVO);


    List<MaterialStockDO> getMaterialStockListContainZero(MaterialStockExportReqVO exportReqVO);


    Long getOriginIdByBatchCode(String batchCode);

}
