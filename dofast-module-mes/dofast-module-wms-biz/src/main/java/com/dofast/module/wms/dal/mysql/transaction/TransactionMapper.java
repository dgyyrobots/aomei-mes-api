package com.dofast.module.wms.dal.mysql.transaction;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.transaction.TransactionDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.transaction.vo.*;

/**
 * 库存事务 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TransactionMapper extends BaseMapperX<TransactionDO> {

    default PageResult<TransactionDO> selectPage(TransactionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TransactionDO>()
                .eqIfPresent(TransactionDO::getTransactionType, reqVO.getTransactionType())
                .eqIfPresent(TransactionDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TransactionDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TransactionDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TransactionDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TransactionDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TransactionDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(TransactionDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(TransactionDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(TransactionDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(TransactionDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(TransactionDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(TransactionDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(TransactionDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(TransactionDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(TransactionDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(TransactionDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(TransactionDO::getVendorCode, reqVO.getVendorCode())
                .likeIfPresent(TransactionDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(TransactionDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(TransactionDO::getSourceDocType, reqVO.getSourceDocType())
                .eqIfPresent(TransactionDO::getSourceDocId, reqVO.getSourceDocId())
                .eqIfPresent(TransactionDO::getSourceDocCode, reqVO.getSourceDocCode())
                .eqIfPresent(TransactionDO::getSourceDocLineId, reqVO.getSourceDocLineId())
                .eqIfPresent(TransactionDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(TransactionDO::getTransactionFlag, reqVO.getTransactionFlag())
                .eqIfPresent(TransactionDO::getTransactionQuantity, reqVO.getTransactionQuantity())
                .betweenIfPresent(TransactionDO::getTransactionDate, reqVO.getTransactionDate())
                .eqIfPresent(TransactionDO::getRelatedTransactionId, reqVO.getRelatedTransactionId())
                .betweenIfPresent(TransactionDO::getErpDate, reqVO.getErpDate())
                .eqIfPresent(TransactionDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TransactionDO::getWorkorderCode, reqVO.getWorkorderCode())
                .betweenIfPresent(TransactionDO::getRecptDate, reqVO.getRecptDate())
                .betweenIfPresent(TransactionDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(TransactionDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TransactionDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TransactionDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TransactionDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TransactionDO::getParentBatchCode, reqVO.getParentBatchCode())
                .betweenIfPresent(TransactionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TransactionDO::getOriginId, reqVO.getOriginId())
                .eqIfPresent(TransactionDO::getConfirmStatus, reqVO.getConfirmStatus())
                .orderByDesc(TransactionDO::getId));
    }

    default List<TransactionDO> selectList(TransactionExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TransactionDO>()
                .eqIfPresent(TransactionDO::getTransactionType, reqVO.getTransactionType())
                .eqIfPresent(TransactionDO::getItemId, reqVO.getItemId())
                .eqIfPresent(TransactionDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(TransactionDO::getItemName, reqVO.getItemName())
                .eqIfPresent(TransactionDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(TransactionDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TransactionDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(TransactionDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(TransactionDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(TransactionDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(TransactionDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(TransactionDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(TransactionDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(TransactionDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(TransactionDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(TransactionDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(TransactionDO::getVendorId, reqVO.getVendorId())
                .eqIfPresent(TransactionDO::getVendorCode, reqVO.getVendorCode())
                .likeIfPresent(TransactionDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(TransactionDO::getVendorNick, reqVO.getVendorNick())
                .eqIfPresent(TransactionDO::getSourceDocType, reqVO.getSourceDocType())
                .eqIfPresent(TransactionDO::getSourceDocId, reqVO.getSourceDocId())
                .eqIfPresent(TransactionDO::getSourceDocCode, reqVO.getSourceDocCode())
                .eqIfPresent(TransactionDO::getSourceDocLineId, reqVO.getSourceDocLineId())
                .eqIfPresent(TransactionDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(TransactionDO::getTransactionFlag, reqVO.getTransactionFlag())
                .eqIfPresent(TransactionDO::getTransactionQuantity, reqVO.getTransactionQuantity())
                .betweenIfPresent(TransactionDO::getTransactionDate, reqVO.getTransactionDate())
                .eqIfPresent(TransactionDO::getRelatedTransactionId, reqVO.getRelatedTransactionId())
                .betweenIfPresent(TransactionDO::getErpDate, reqVO.getErpDate())
                .eqIfPresent(TransactionDO::getWorkorderId, reqVO.getWorkorderId())
                .eqIfPresent(TransactionDO::getWorkorderCode, reqVO.getWorkorderCode())
                .betweenIfPresent(TransactionDO::getRecptDate, reqVO.getRecptDate())
                .betweenIfPresent(TransactionDO::getExpireDate, reqVO.getExpireDate())
                .eqIfPresent(TransactionDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TransactionDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TransactionDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TransactionDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TransactionDO::getParentBatchCode, reqVO.getParentBatchCode())
                .betweenIfPresent(TransactionDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TransactionDO::getOriginId, reqVO.getOriginId())
                .eqIfPresent(TransactionDO::getConfirmStatus, reqVO.getConfirmStatus())
                .orderByDesc(TransactionDO::getId));
    }

}
