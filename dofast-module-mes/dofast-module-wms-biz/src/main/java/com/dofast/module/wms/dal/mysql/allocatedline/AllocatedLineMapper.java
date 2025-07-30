package com.dofast.module.wms.dal.mysql.allocatedline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.allocatedline.AllocatedLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.allocatedline.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 调拨单身 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface AllocatedLineMapper extends BaseMapperX<AllocatedLineDO> {

    default PageResult<AllocatedLineDO> selectPage(AllocatedLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AllocatedLineDO>()
                .eqIfPresent(AllocatedLineDO::getAllocatedId, reqVO.getAllocatedId())
                .eqIfPresent(AllocatedLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(AllocatedLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(AllocatedLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(AllocatedLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(AllocatedLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(AllocatedLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(AllocatedLineDO::getQuantityAllocated, reqVO.getQuantityAllocated())
                .eqIfPresent(AllocatedLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(AllocatedLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(AllocatedLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(AllocatedLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(AllocatedLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(AllocatedLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(AllocatedLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(AllocatedLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(AllocatedLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(AllocatedLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(AllocatedLineDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(AllocatedLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(AllocatedLineDO::getId));
    }

    default List<AllocatedLineDO> selectList(AllocatedLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<AllocatedLineDO>()
                .eqIfPresent(AllocatedLineDO::getAllocatedId, reqVO.getAllocatedId())
                .eqIfPresent(AllocatedLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(AllocatedLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(AllocatedLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(AllocatedLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(AllocatedLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(AllocatedLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(AllocatedLineDO::getQuantityAllocated, reqVO.getQuantityAllocated())
                .eqIfPresent(AllocatedLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(AllocatedLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(AllocatedLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(AllocatedLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(AllocatedLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(AllocatedLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(AllocatedLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(AllocatedLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(AllocatedLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(AllocatedLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(AllocatedLineDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(AllocatedLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AllocatedLineDO::getDeleted, 0)
                .orderByDesc(AllocatedLineDO::getId));
    }

}
