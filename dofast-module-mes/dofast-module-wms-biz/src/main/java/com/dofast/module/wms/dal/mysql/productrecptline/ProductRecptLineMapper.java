package com.dofast.module.wms.dal.mysql.productrecptline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.productrecptline.ProductRecptLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.productrecptline.vo.*;

/**
 * 产品入库记录表行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductRecptLineMapper extends BaseMapperX<ProductRecptLineDO> {
    default List<ProductRecptLineDO> selectList(ProductRecptLineListVO reqVO){
        return selectList(new LambdaQueryWrapperX<ProductRecptLineDO>()
                .eqIfPresent(ProductRecptLineDO::getRecptId, reqVO.getRecptId())
                .eqIfPresent(ProductRecptLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(ProductRecptLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ProductRecptLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ProductRecptLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ProductRecptLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ProductRecptLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ProductRecptLineDO::getQuantityRecived, reqVO.getQuantityRecived())
                .eqIfPresent(ProductRecptLineDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ProductRecptLineDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(ProductRecptLineDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ProductRecptLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ProductRecptLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ProductRecptLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ProductRecptLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ProductRecptLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ProductRecptLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ProductRecptLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ProductRecptLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProductRecptLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ProductRecptLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(ProductRecptLineDO::getRemark, reqVO.getRemark())
                .orderByDesc(ProductRecptLineDO::getId)
        );
    }
    default int deleteByRecptId(Long recptId){
        return delete(new LambdaQueryWrapperX<ProductRecptLineDO>().eq(ProductRecptLineDO::getRecptId,recptId));
    }
    default PageResult<ProductRecptLineDO> selectPage(ProductRecptLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductRecptLineDO>()
                .eqIfPresent(ProductRecptLineDO::getRecptId, reqVO.getRecptId())
                .eqIfPresent(ProductRecptLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(ProductRecptLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ProductRecptLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(ProductRecptLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ProductRecptLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ProductRecptLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ProductRecptLineDO::getQuantityRecived, reqVO.getQuantityRecived())
                .eqIfPresent(ProductRecptLineDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ProductRecptLineDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(ProductRecptLineDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ProductRecptLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ProductRecptLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ProductRecptLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(ProductRecptLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ProductRecptLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ProductRecptLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(ProductRecptLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ProductRecptLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProductRecptLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(ProductRecptLineDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ProductRecptLineDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(ProductRecptLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductRecptLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductRecptLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductRecptLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductRecptLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductRecptLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductRecptLineDO::getId));
    }

    default List<ProductRecptLineDO> selectList(ProductRecptLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProductRecptLineDO>()
                .eqIfPresent(ProductRecptLineDO::getRecptId, reqVO.getRecptId())
                .eqIfPresent(ProductRecptLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(ProductRecptLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ProductRecptLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ProductRecptLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ProductRecptLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ProductRecptLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ProductRecptLineDO::getQuantityRecived, reqVO.getQuantityRecived())
                .eqIfPresent(ProductRecptLineDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ProductRecptLineDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(ProductRecptLineDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ProductRecptLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ProductRecptLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ProductRecptLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ProductRecptLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ProductRecptLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ProductRecptLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ProductRecptLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ProductRecptLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProductRecptLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ProductRecptLineDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ProductRecptLineDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(ProductRecptLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductRecptLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductRecptLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductRecptLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductRecptLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductRecptLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductRecptLineDO::getId));
    }

}
