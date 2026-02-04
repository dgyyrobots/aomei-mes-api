package com.dofast.module.wms.dal.mysql.itemrecptline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.itemrecptline.ItemRecptLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.itemrecptline.vo.*;

/**
 * 物料入库单行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ItemRecptLineMapper extends BaseMapperX<ItemRecptLineDO> {
    default int deleteByRecptId(Long recptId){
        return delete(new LambdaQueryWrapperX<ItemRecptLineDO>().eq(ItemRecptLineDO::getRecptId,recptId));
    }
    default List<ItemRecptLineDO> selectList(ItemRecptLineListVO lineListVO) {
        return selectList(new LambdaQueryWrapperX<ItemRecptLineDO>()
                .eqIfPresent(ItemRecptLineDO::getRecptId,lineListVO.getRecptId())
                .eqIfPresent(ItemRecptLineDO::getItemId,lineListVO.getItemId())
                .eqIfPresent(ItemRecptLineDO::getItemCode,lineListVO.getItemCode())
                .likeIfPresent(ItemRecptLineDO::getItemName,lineListVO.getItemName())
                .eqIfPresent(ItemRecptLineDO::getSpecification,lineListVO.getSpecification())
                .eqIfPresent(ItemRecptLineDO::getUnitOfMeasure,lineListVO.getUnitOfMeasure())
                .eqIfPresent(ItemRecptLineDO::getQuantityRecived,lineListVO.getQuantityRecived())
                .eqIfPresent(ItemRecptLineDO::getBatchCode,lineListVO.getBatchCode())
                .eqIfPresent(ItemRecptLineDO::getWarehouseId,lineListVO.getWarehouseId())
                .eqIfPresent(ItemRecptLineDO::getWarehouseCode,lineListVO.getWarehouseCode())
                .likeIfPresent(ItemRecptLineDO::getWarehouseName,lineListVO.getWarehouseName())
                .eqIfPresent(ItemRecptLineDO::getLocationId,lineListVO.getLocationId())
                .eqIfPresent(ItemRecptLineDO::getLocationCode,lineListVO.getLocationCode())
                .likeIfPresent(ItemRecptLineDO::getLocationName,lineListVO.getLocationName())
                .eqIfPresent(ItemRecptLineDO::getAreaId,lineListVO.getAreaId())
                .eqIfPresent(ItemRecptLineDO::getAreaCode,lineListVO.getAreaCode())
                .likeIfPresent(ItemRecptLineDO::getAreaName,lineListVO.getAreaName())
                .eqIfPresent(ItemRecptLineDO::getExpireDate,lineListVO.getExpireDate())
        );
    }
    default PageResult<ItemRecptLineDO> selectPage(ItemRecptLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ItemRecptLineDO>()
                .eqIfPresent(ItemRecptLineDO::getRecptId, reqVO.getRecptId())
                .eqIfPresent(ItemRecptLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ItemRecptLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ItemRecptLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ItemRecptLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ItemRecptLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ItemRecptLineDO::getQuantityRecived, reqVO.getQuantityRecived())
                .eqIfPresent(ItemRecptLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ItemRecptLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ItemRecptLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ItemRecptLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ItemRecptLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ItemRecptLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ItemRecptLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ItemRecptLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ItemRecptLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ItemRecptLineDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ItemRecptLineDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(ItemRecptLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemRecptLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemRecptLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemRecptLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemRecptLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemRecptLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemRecptLineDO::getId));
    }

    default List<ItemRecptLineDO> selectList(ItemRecptLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ItemRecptLineDO>()
                .eqIfPresent(ItemRecptLineDO::getRecptId, reqVO.getRecptId())
                .eqIfPresent(ItemRecptLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(ItemRecptLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(ItemRecptLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(ItemRecptLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(ItemRecptLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(ItemRecptLineDO::getQuantityRecived, reqVO.getQuantityRecived())
                .eqIfPresent(ItemRecptLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(ItemRecptLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ItemRecptLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ItemRecptLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ItemRecptLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ItemRecptLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ItemRecptLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ItemRecptLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ItemRecptLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ItemRecptLineDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ItemRecptLineDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(ItemRecptLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemRecptLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemRecptLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemRecptLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemRecptLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemRecptLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemRecptLineDO::getId));
    }

}
