package com.dofast.module.wms.dal.mysql.productrecpt;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.productrecpt.ProductRecptDO;
import com.dofast.module.wms.dal.dataobject.productrecpt.ProductRecptTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.productrecpt.vo.*;

/**
 * 产品入库录 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ProductRecptMapper extends BaseMapperX<ProductRecptDO> {
    public List<ProductRecptTxBean> getTxBean(Long recptId);
    default ProductRecptDO checkUnique(ProductRecptBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<ProductRecptDO>().eq(ProductRecptDO::getRecptCode,baseVO.getRecptCode()));
    }
    default PageResult<ProductRecptDO> selectPage(ProductRecptPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductRecptDO>()
                .eqIfPresent(ProductRecptDO::getRecptCode, reqVO.getRecptCode())
                .likeIfPresent(ProductRecptDO::getRecptName, reqVO.getRecptName())
                .eqIfPresent(ProductRecptDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ProductRecptDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(ProductRecptDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ProductRecptDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ProductRecptDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(ProductRecptDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ProductRecptDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ProductRecptDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ProductRecptDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ProductRecptDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(ProductRecptDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ProductRecptDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ProductRecptDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(ProductRecptDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ProductRecptDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProductRecptDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(ProductRecptDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ProductRecptDO::getRecptDate, reqVO.getRecptDate())
                .eqIfPresent(ProductRecptDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ProductRecptDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductRecptDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductRecptDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductRecptDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductRecptDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductRecptDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductRecptDO::getId));
    }

    default List<ProductRecptDO> selectList(ProductRecptExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ProductRecptDO>()
                .eqIfPresent(ProductRecptDO::getRecptCode, reqVO.getRecptCode())
                .eqIfPresent(ProductRecptDO::getRecptName, reqVO.getRecptName())
                .eqIfPresent(ProductRecptDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(ProductRecptDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(ProductRecptDO::getWorkorderName, reqVO.getWorkorderName())
                .eqIfPresent(ProductRecptDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ProductRecptDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ProductRecptDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ProductRecptDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ProductRecptDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ProductRecptDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ProductRecptDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ProductRecptDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ProductRecptDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ProductRecptDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ProductRecptDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ProductRecptDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ProductRecptDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ProductRecptDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ProductRecptDO::getRecptDate, reqVO.getRecptDate())
                .eqIfPresent(ProductRecptDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ProductRecptDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ProductRecptDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ProductRecptDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ProductRecptDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ProductRecptDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ProductRecptDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ProductRecptDO::getId));
    }

}
