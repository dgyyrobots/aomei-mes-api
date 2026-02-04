package com.dofast.module.wms.dal.mysql.issueline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.issueline.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 生产领料单行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface IssueLineMapper extends BaseMapperX<IssueLineDO> {
    default List<IssueLineDO> selectList(IssueLineListVO reqVO){
        return selectList(new LambdaQueryWrapperX<IssueLineDO>()
                .eqIfPresent(IssueLineDO::getIssueId, reqVO.getIssueId())
                .eqIfPresent(IssueLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(IssueLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(IssueLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(IssueLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(IssueLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(IssueLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IssueLineDO::getQuantityIssued, reqVO.getQuantityIssued())
                .eqIfPresent(IssueLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(IssueLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(IssueLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(IssueLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(IssueLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(IssueLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(IssueLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(IssueLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(IssueLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(IssueLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(IssueLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IssueLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IssueLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IssueLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IssueLineDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(IssueLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IssueLineDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IssueLineDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(IssueLineDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .likeIfPresent(IssueLineDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(IssueLineDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(IssueLineDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(IssueLineDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(IssueLineDO::getBarcodeNumber, reqVO.getBarcodeNumber())
                .eqIfPresent(IssueLineDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(IssueLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(IssueLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(IssueLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(IssueLineDO::getErpEnable, reqVO.getErpEnable())
                .eqIfPresent(IssueLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .orderByDesc(IssueLineDO::getId));
    }

    default int deleteByIssueHeaderId(Long headerId){
        return delete(new LambdaQueryWrapperX<IssueLineDO>().eq(IssueLineDO::getIssueId,headerId));
    }

    default PageResult<IssueLineDO> selectPage(IssueLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IssueLineDO>()
                .eqIfPresent(IssueLineDO::getIssueId, reqVO.getIssueId())
                .eqIfPresent(IssueLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(IssueLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(IssueLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(IssueLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(IssueLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(IssueLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IssueLineDO::getQuantityIssued, reqVO.getQuantityIssued())
                .eqIfPresent(IssueLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(IssueLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(IssueLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(IssueLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(IssueLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(IssueLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(IssueLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(IssueLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(IssueLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(IssueLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(IssueLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IssueLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IssueLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IssueLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IssueLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(IssueLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IssueLineDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IssueLineDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(IssueLineDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(IssueLineDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(IssueLineDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(IssueLineDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(IssueLineDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(IssueLineDO::getBarcodeNumber, reqVO.getBarcodeNumber())
                .eqIfPresent(IssueLineDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(IssueLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(IssueLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(IssueLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(IssueLineDO::getErpEnable, reqVO.getErpEnable())
                .eqIfPresent(IssueLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(IssueLineDO::getCreator, reqVO.getCreator())
                .orderByDesc(IssueLineDO::getId));
    }

    default List<IssueLineDO> selectList(IssueLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IssueLineDO>()
                .eqIfPresent(IssueLineDO::getIssueId, reqVO.getIssueId())
                .eqIfPresent(IssueLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(IssueLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(IssueLineDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(IssueLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(IssueLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(IssueLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IssueLineDO::getQuantityIssued, reqVO.getQuantityIssued())
                .eqIfPresent(IssueLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(IssueLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(IssueLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(IssueLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(IssueLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(IssueLineDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(IssueLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(IssueLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(IssueLineDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(IssueLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(IssueLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IssueLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IssueLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IssueLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IssueLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(IssueLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(IssueLineDO::getStatus, reqVO.getStatus())
                .eqIfPresent(IssueLineDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(IssueLineDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(IssueLineDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(IssueLineDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(IssueLineDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(IssueLineDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(IssueLineDO::getBarcodeNumber, reqVO.getBarcodeNumber())
                .eqIfPresent(IssueLineDO::getEnableFlag, reqVO.getEnableFlag())
                .eqIfPresent(IssueLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(IssueLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(IssueLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(IssueLineDO::getErpEnable, reqVO.getErpEnable())
                .eqIfPresent(IssueLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(IssueLineDO::getCreator, reqVO.getCreator())
                .orderByDesc(IssueLineDO::getId));
    }

    List<IssueLineDO> getIssueLineListByFeedbackCodes(@Param("feedbackCodes") List<String> feedbackCodes);



}
