package com.dofast.module.wms.dal.mysql.itemrecpt;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.itemrecpt.ItemRecptDO;
import com.dofast.module.wms.dal.dataobject.itemrecpt.ItemRecptTxBean;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.itemrecpt.vo.*;

/**
 * 物料入库单 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface ItemRecptMapper extends BaseMapperX<ItemRecptDO> {
    public List<ItemRecptTxBean> getTxBeans(Long recptId);
    default ItemRecptDO checkRecptCodeUnique(ItemRecptBaseVO baseVO){
        return selectOne(new LambdaQueryWrapperX<ItemRecptDO>()
        .eq(ItemRecptDO::getRecptCode,baseVO.getRecptCode()));
    }
    default PageResult<ItemRecptDO> selectPage(ItemRecptPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ItemRecptDO>()
                .eqIfPresent(ItemRecptDO::getRecptCode, reqVO.getRecptCode())
                .likeIfPresent(ItemRecptDO::getRecptName, reqVO.getRecptName())
                .eqIfPresent(ItemRecptDO::getIqcId, reqVO.getIqcId())
                .eqIfPresent(ItemRecptDO::getIqcCode, reqVO.getIqcCode())
                .eqIfPresent(ItemRecptDO::getPoCode, reqVO.getPoCode())
                .eqIfPresent(ItemRecptDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(ItemRecptDO::getVendorCode, reqVO.getVendorCode())
                .likeIfPresent(ItemRecptDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(ItemRecptDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(ItemRecptDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ItemRecptDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(ItemRecptDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ItemRecptDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ItemRecptDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(ItemRecptDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ItemRecptDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ItemRecptDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(ItemRecptDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ItemRecptDO::getRecptDate, reqVO.getRecptDate())
                .eqIfPresent(ItemRecptDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ItemRecptDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemRecptDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemRecptDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemRecptDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemRecptDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemRecptDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemRecptDO::getId));
    }

    default List<ItemRecptDO> selectList(ItemRecptExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<ItemRecptDO>()
                .eqIfPresent(ItemRecptDO::getRecptCode, reqVO.getRecptCode())
                .eqIfPresent(ItemRecptDO::getRecptName, reqVO.getRecptName())
                .eqIfPresent(ItemRecptDO::getIqcId, reqVO.getIqcId())
                .eqIfPresent(ItemRecptDO::getIqcCode, reqVO.getIqcCode())
                .eqIfPresent(ItemRecptDO::getPoCode, reqVO.getPoCode())
                .eqIfPresent(ItemRecptDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(ItemRecptDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(ItemRecptDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(ItemRecptDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(ItemRecptDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(ItemRecptDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(ItemRecptDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(ItemRecptDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(ItemRecptDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(ItemRecptDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(ItemRecptDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(ItemRecptDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(ItemRecptDO::getAreaName, reqVO.getAreaName())
                .betweenIfPresent(ItemRecptDO::getRecptDate, reqVO.getRecptDate())
                .eqIfPresent(ItemRecptDO::getStatus, reqVO.getStatus())
                .eqIfPresent(ItemRecptDO::getRemark, reqVO.getRemark())
                .eqIfPresent(ItemRecptDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(ItemRecptDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(ItemRecptDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(ItemRecptDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(ItemRecptDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(ItemRecptDO::getId));
    }

}
