package com.dofast.module.wms.dal.mysql.rtsalseline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.rtsalseline.RtSalseLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.rtsalseline.vo.*;

/**
 * 产品销售退货行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RtSalseLineMapper extends BaseMapperX<RtSalseLineDO> {
    default List<RtSalseLineDO> selectList(RtSalseLineListVO reqVO){
        return selectList(new LambdaQueryWrapperX<RtSalseLineDO>()
                .eqIfPresent(RtSalseLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtSalseLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtSalseLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RtSalseLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtSalseLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtSalseLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtSalseLineDO::getQuantityRted, reqVO.getQuantityRted())
                .eqIfPresent(RtSalseLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtSalseLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtSalseLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RtSalseLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtSalseLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtSalseLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RtSalseLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtSalseLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtSalseLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RtSalseLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RtSalseLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtSalseLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtSalseLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtSalseLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtSalseLineDO::getAttr4, reqVO.getAttr4())
                .orderByDesc(RtSalseLineDO::getId));
    }
    default int deleteByRtId(Long rtId){
        return delete(new LambdaQueryWrapperX<RtSalseLineDO>().eq(RtSalseLineDO::getRtId,rtId));
    }
    default PageResult<RtSalseLineDO> selectPage(RtSalseLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RtSalseLineDO>()
                .eqIfPresent(RtSalseLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtSalseLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtSalseLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(RtSalseLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtSalseLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtSalseLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtSalseLineDO::getQuantityRted, reqVO.getQuantityRted())
                .eqIfPresent(RtSalseLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtSalseLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtSalseLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(RtSalseLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtSalseLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtSalseLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(RtSalseLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtSalseLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtSalseLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(RtSalseLineDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(RtSalseLineDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(RtSalseLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtSalseLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtSalseLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtSalseLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtSalseLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtSalseLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtSalseLineDO::getId));
    }

    default List<RtSalseLineDO> selectList(RtSalseLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RtSalseLineDO>()
                .eqIfPresent(RtSalseLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtSalseLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtSalseLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(RtSalseLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtSalseLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtSalseLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtSalseLineDO::getQuantityRted, reqVO.getQuantityRted())
                .eqIfPresent(RtSalseLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtSalseLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtSalseLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(RtSalseLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtSalseLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtSalseLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(RtSalseLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtSalseLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtSalseLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(RtSalseLineDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(RtSalseLineDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(RtSalseLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtSalseLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtSalseLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtSalseLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtSalseLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtSalseLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(RtSalseLineDO::getId));
    }

}
