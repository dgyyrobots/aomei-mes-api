package com.dofast.module.wms.dal.mysql.allocatedrecord;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.allocatedrecord.vo.*;

/**
 * 调拨单身记录 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface AllocatedRecordMapper extends BaseMapperX<AllocatedRecordDO> {

    default PageResult<AllocatedRecordDO> selectPage(AllocatedRecordPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AllocatedRecordDO>()
                .eqIfPresent(AllocatedRecordDO::getAllocatedId, reqVO.getAllocatedId())
                .eqIfPresent(AllocatedRecordDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(AllocatedRecordDO::getItemId, reqVO.getItemId())
                .eqIfPresent(AllocatedRecordDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(AllocatedRecordDO::getItemName, reqVO.getItemName())
                .eqIfPresent(AllocatedRecordDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(AllocatedRecordDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(AllocatedRecordDO::getQuantityAllocated, reqVO.getQuantityAllocated())
                .eqIfPresent(AllocatedRecordDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(AllocatedRecordDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(AllocatedRecordDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(AllocatedRecordDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(AllocatedRecordDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(AllocatedRecordDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(AllocatedRecordDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(AllocatedRecordDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(AllocatedRecordDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(AllocatedRecordDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(AllocatedRecordDO::getRemark, reqVO.getRemark())
                .eqIfPresent(AllocatedRecordDO::getAllocatedFlag, reqVO.getAllocatedFlag())
                .betweenIfPresent(AllocatedRecordDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AllocatedRecordDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(AllocatedRecordDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(AllocatedRecordDO::getErpStatus, reqVO.getErpStatus())
                .eqIfPresent(AllocatedRecordDO::getConverQuantity, reqVO.getConverQuantity())
                .eqIfPresent(AllocatedRecordDO::getConverUnitOfMeasure, reqVO.getConverUnitOfMeasure())
                .orderByDesc(AllocatedRecordDO::getId));
    }

    default List<AllocatedRecordDO> selectList(AllocatedRecordExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<AllocatedRecordDO>()
                .eqIfPresent(AllocatedRecordDO::getAllocatedId, reqVO.getAllocatedId())
                .eqIfPresent(AllocatedRecordDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(AllocatedRecordDO::getItemId, reqVO.getItemId())
                .eqIfPresent(AllocatedRecordDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(AllocatedRecordDO::getItemName, reqVO.getItemName())
                .eqIfPresent(AllocatedRecordDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(AllocatedRecordDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(AllocatedRecordDO::getQuantityAllocated, reqVO.getQuantityAllocated())
                .eqIfPresent(AllocatedRecordDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(AllocatedRecordDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(AllocatedRecordDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(AllocatedRecordDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(AllocatedRecordDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(AllocatedRecordDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(AllocatedRecordDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(AllocatedRecordDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(AllocatedRecordDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(AllocatedRecordDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(AllocatedRecordDO::getRemark, reqVO.getRemark())
                .eqIfPresent(AllocatedRecordDO::getAllocatedFlag, reqVO.getAllocatedFlag())
                .betweenIfPresent(AllocatedRecordDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AllocatedRecordDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(AllocatedRecordDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(AllocatedRecordDO::getErpStatus, reqVO.getErpStatus())
                .eqIfPresent(AllocatedRecordDO::getConverQuantity, reqVO.getConverQuantity())
                .eqIfPresent(AllocatedRecordDO::getConverUnitOfMeasure, reqVO.getConverUnitOfMeasure())
                .orderByDesc(AllocatedRecordDO::getId));
    }

}
