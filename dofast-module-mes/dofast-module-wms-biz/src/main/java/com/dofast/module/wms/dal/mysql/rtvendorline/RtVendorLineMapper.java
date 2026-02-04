package com.dofast.module.wms.dal.mysql.rtvendorline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.rtvendorline.RtVendorLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.rtvendorline.vo.*;

/**
 * 供应商退货行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RtVendorLineMapper extends BaseMapperX<RtVendorLineDO> {

    default PageResult<RtVendorLineDO> selectPage(RtVendorLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RtVendorLineDO>()
                .eqIfPresent(RtVendorLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtVendorLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(RtVendorLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtVendorLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RtVendorLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtVendorLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtVendorLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtVendorLineDO::getQuantityRted, reqVO.getQuantityRted())
                .eqIfPresent(RtVendorLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtVendorLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtVendorLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RtVendorLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtVendorLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtVendorLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RtVendorLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtVendorLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtVendorLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RtVendorLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RtVendorLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtVendorLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtVendorLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtVendorLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtVendorLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtVendorLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtVendorLineDO::getId));
    }

    default List<RtVendorLineDO> selectList(RtVendorLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RtVendorLineDO>()
                .eqIfPresent(RtVendorLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtVendorLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(RtVendorLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtVendorLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(RtVendorLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtVendorLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtVendorLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtVendorLineDO::getQuantityRted, reqVO.getQuantityRted())
                .eqIfPresent(RtVendorLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtVendorLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtVendorLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(RtVendorLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtVendorLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtVendorLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(RtVendorLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtVendorLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtVendorLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(RtVendorLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RtVendorLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtVendorLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtVendorLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtVendorLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtVendorLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtVendorLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtVendorLineDO::getId));
    }

}
