package com.dofast.module.wms.dal.mysql.rtissueline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.rtissueline.RtIssueLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.rtissueline.vo.*;

/**
 * 生产退料单行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface RtIssueLineMapper extends BaseMapperX<RtIssueLineDO> {
    default int deleteByRtId(Long rtId){
        return delete(new LambdaQueryWrapperX<RtIssueLineDO>().eq(RtIssueLineDO::getRtId,rtId));
    }
    default PageResult<RtIssueLineDO> selectPage(RtIssueLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RtIssueLineDO>()
                .eqIfPresent(RtIssueLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtIssueLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(RtIssueLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtIssueLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RtIssueLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtIssueLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtIssueLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtIssueLineDO::getQuantityRt, reqVO.getQuantityRt())
                .eqIfPresent(RtIssueLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtIssueLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtIssueLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RtIssueLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtIssueLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtIssueLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RtIssueLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtIssueLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtIssueLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RtIssueLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RtIssueLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtIssueLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtIssueLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtIssueLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtIssueLineDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RtIssueLineDO::getVendorCode, reqVO.getVendorCode())
                .betweenIfPresent(RtIssueLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RtIssueLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(RtIssueLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(RtIssueLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(RtIssueLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(RtIssueLineDO::getErpStatus, reqVO.getErpStatus())
                .orderByDesc(RtIssueLineDO::getId));
    }

    default List<RtIssueLineDO> selectList(RtIssueLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RtIssueLineDO>()
                .eqIfPresent(RtIssueLineDO::getId, reqVO.getId())
                .eqIfPresent(RtIssueLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtIssueLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(RtIssueLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtIssueLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RtIssueLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtIssueLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtIssueLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtIssueLineDO::getQuantityRt, reqVO.getQuantityRt())
                .eqIfPresent(RtIssueLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtIssueLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtIssueLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RtIssueLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtIssueLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtIssueLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RtIssueLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtIssueLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtIssueLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RtIssueLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RtIssueLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtIssueLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtIssueLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtIssueLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtIssueLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(RtIssueLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RtIssueLineDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(RtIssueLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(RtIssueLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(RtIssueLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(RtIssueLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(RtIssueLineDO::getErpStatus, reqVO.getErpStatus())
                .orderByDesc(RtIssueLineDO::getId));
    }

    default List<RtIssueLineDO> selectList(RtIssueLineListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RtIssueLineDO>()
                .eqIfPresent(RtIssueLineDO::getRtId, reqVO.getRtId())
                .eqIfPresent(RtIssueLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(RtIssueLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(RtIssueLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(RtIssueLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(RtIssueLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(RtIssueLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RtIssueLineDO::getQuantityRt, reqVO.getQuantityRt())
                .eqIfPresent(RtIssueLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RtIssueLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RtIssueLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RtIssueLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RtIssueLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RtIssueLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RtIssueLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RtIssueLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RtIssueLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RtIssueLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RtIssueLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(RtIssueLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(RtIssueLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(RtIssueLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(RtIssueLineDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(RtIssueLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RtIssueLineDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(RtIssueLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(RtIssueLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(RtIssueLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(RtIssueLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(RtIssueLineDO::getErpStatus, reqVO.getErpStatus())
                .orderByDesc(RtIssueLineDO::getId));
    }

}
