package com.dofast.module.wms.dal.mysql.electroformline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.electroformline.ElectroformLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.electroformline.vo.*;

/**
 * 制版房领料单身体 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ElectroformLineMapper extends BaseMapperX<ElectroformLineDO> {

    default PageResult<ElectroformLineDO> selectPage(ElectroformLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ElectroformLineDO>()
                .eqIfPresent(ElectroformLineDO::getIssueId, reqVO.getIssueId())
                .eqIfPresent(ElectroformLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(ElectroformLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ElectroformLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(ElectroformLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ElectroformLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ElectroformLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ElectroformLineDO::getQuantityIssued, reqVO.getQuantityIssued())
                .eqIfPresent(ElectroformLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ElectroformLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ElectroformLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(ElectroformLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ElectroformLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ElectroformLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(ElectroformLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ElectroformLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ElectroformLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(ElectroformLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(ElectroformLineDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ElectroformLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ElectroformLineDO::getId));
    }

    default List<ElectroformLineDO> selectList(ElectroformLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ElectroformLineDO>()
                .eqIfPresent(ElectroformLineDO::getIssueId, reqVO.getIssueId())
                .eqIfPresent(ElectroformLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(ElectroformLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ElectroformLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ElectroformLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ElectroformLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ElectroformLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ElectroformLineDO::getQuantityIssued, reqVO.getQuantityIssued())
                .eqIfPresent(ElectroformLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ElectroformLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ElectroformLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ElectroformLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ElectroformLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ElectroformLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ElectroformLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ElectroformLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ElectroformLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ElectroformLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(ElectroformLineDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(ElectroformLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ElectroformLineDO::getId));
    }

}
