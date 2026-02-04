package com.dofast.module.wms.dal.mysql.productproduceline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.productproduceline.ProductProduceLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.productproduceline.vo.*;

/**
 * 产品产出记录表行 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface ProductProduceLineMapper extends BaseMapperX<ProductProduceLineDO> {

    default PageResult<ProductProduceLineDO> selectPage(ProductProduceLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductProduceLineDO>()
                .eqIfPresent(ProductProduceLineDO::getRecordId, reqVO.getRecordId())
                .eqIfPresent(ProductProduceLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ProductProduceLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(ProductProduceLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ProductProduceLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ProductProduceLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ProductProduceLineDO::getQuantityProduce, reqVO.getQuantityProduce())
                .eqIfPresent(ProductProduceLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ProductProduceLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ProductProduceLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(ProductProduceLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ProductProduceLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ProductProduceLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(ProductProduceLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ProductProduceLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProductProduceLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(ProductProduceLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(ProductProduceLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductProduceLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductProduceLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductProduceLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductProduceLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductProduceLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ProductProduceLineDO::getOrderSource, reqVO.getOrderSource())
                .orderByDesc(ProductProduceLineDO::getId));
    }

    default List<ProductProduceLineDO> selectList(ProductProduceLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProductProduceLineDO>()
                .eqIfPresent(ProductProduceLineDO::getRecordId, reqVO.getRecordId())
                .eqIfPresent(ProductProduceLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ProductProduceLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ProductProduceLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ProductProduceLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ProductProduceLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ProductProduceLineDO::getQuantityProduce, reqVO.getQuantityProduce())
                .eqIfPresent(ProductProduceLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ProductProduceLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ProductProduceLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ProductProduceLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ProductProduceLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ProductProduceLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ProductProduceLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ProductProduceLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProductProduceLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ProductProduceLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(ProductProduceLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductProduceLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductProduceLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductProduceLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductProduceLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductProduceLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(ProductProduceLineDO::getOrderSource, reqVO.getOrderSource())
                .orderByDesc(ProductProduceLineDO::getId));
    }

}
