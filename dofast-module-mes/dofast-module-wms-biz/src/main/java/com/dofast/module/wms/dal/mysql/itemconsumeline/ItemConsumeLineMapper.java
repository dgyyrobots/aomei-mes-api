package com.dofast.module.wms.dal.mysql.itemconsumeline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.itemconsumeline.ItemConsumeLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.itemconsumeline.vo.*;

/**
 * 物料消耗记录行 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ItemConsumeLineMapper extends BaseMapperX<ItemConsumeLineDO> {

    default PageResult<ItemConsumeLineDO> selectPage(ItemConsumeLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ItemConsumeLineDO>()
                .eqIfPresent(ItemConsumeLineDO::getRecordId, reqVO.getRecordId())
                .eqIfPresent(ItemConsumeLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(ItemConsumeLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ItemConsumeLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(ItemConsumeLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ItemConsumeLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ItemConsumeLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ItemConsumeLineDO::getQuantityConsume, reqVO.getQuantityConsume())
                .eqIfPresent(ItemConsumeLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ItemConsumeLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ItemConsumeLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(ItemConsumeLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ItemConsumeLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ItemConsumeLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(ItemConsumeLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ItemConsumeLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ItemConsumeLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(ItemConsumeLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(ItemConsumeLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemConsumeLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemConsumeLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemConsumeLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemConsumeLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemConsumeLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemConsumeLineDO::getId));
    }

    default List<ItemConsumeLineDO> selectList(ItemConsumeLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ItemConsumeLineDO>()
                .eqIfPresent(ItemConsumeLineDO::getRecordId, reqVO.getRecordId())
                .eqIfPresent(ItemConsumeLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(ItemConsumeLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ItemConsumeLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ItemConsumeLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ItemConsumeLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ItemConsumeLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ItemConsumeLineDO::getQuantityConsume, reqVO.getQuantityConsume())
                .eqIfPresent(ItemConsumeLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ItemConsumeLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ItemConsumeLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ItemConsumeLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ItemConsumeLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ItemConsumeLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ItemConsumeLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ItemConsumeLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ItemConsumeLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ItemConsumeLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(ItemConsumeLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemConsumeLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemConsumeLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemConsumeLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemConsumeLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemConsumeLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemConsumeLineDO::getId));
    }

}
