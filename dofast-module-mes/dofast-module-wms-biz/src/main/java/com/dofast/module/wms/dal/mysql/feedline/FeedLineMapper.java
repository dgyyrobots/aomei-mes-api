package com.dofast.module.wms.dal.mysql.feedline;

import java.util.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dofast.framework.common.pojo.PageParam;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.wms.controller.admin.feedline.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 上料详情 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface FeedLineMapper extends BaseMapperX<FeedLineDO> {

    default PageResult<FeedLineDO> selectPage(FeedLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<FeedLineDO>()
                .eqIfPresent(FeedLineDO::getIssueId, reqVO.getIssueId())
                .eqIfPresent(FeedLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(FeedLineDO::getItemId, reqVO.getItemId())
                .likeIfPresent(FeedLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(FeedLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedLineDO::getQuantity, reqVO.getQuantity())
                .likeIfPresent(FeedLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(FeedLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(FeedLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(FeedLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(FeedLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(FeedLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(FeedLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(FeedLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(FeedLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(FeedLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(FeedLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FeedLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(FeedLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(FeedLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(FeedLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(FeedLineDO::getCreateTime, reqVO.getCreateTime())
                .likeIfPresent(FeedLineDO::getWorkorderCode, reqVO.getWorkorderCode())
                .likeIfPresent(FeedLineDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(FeedLineDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(FeedLineDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(FeedLineDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(FeedLineDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FeedLineDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(FeedLineDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(FeedLineDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(FeedLineDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedLineDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(FeedLineDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedLineDO::getBarcodeNumber, reqVO.getBarcodeNumber())
                .eqIfPresent(FeedLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(FeedLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(FeedLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(FeedLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .orderByDesc(FeedLineDO::getId));
    }

    default List<FeedLineDO> selectList(FeedLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<FeedLineDO>()
                .eqIfPresent(FeedLineDO::getIssueId, reqVO.getIssueId())
                .eqIfPresent(FeedLineDO::getMaterialStockId, reqVO.getMaterialStockId())
                .eqIfPresent(FeedLineDO::getItemId, reqVO.getItemId())
                .eqIfPresent(FeedLineDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(FeedLineDO::getItemName, reqVO.getItemName())
                .eqIfPresent(FeedLineDO::getSpecification, reqVO.getSpecification())
                .eqIfPresent(FeedLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(FeedLineDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(FeedLineDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(FeedLineDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(FeedLineDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(FeedLineDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(FeedLineDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(FeedLineDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(FeedLineDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(FeedLineDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(FeedLineDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(FeedLineDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(FeedLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(FeedLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(FeedLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(FeedLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(FeedLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(FeedLineDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(FeedLineDO::getWorkorderCode, reqVO.getWorkorderCode())
                .eqIfPresent(FeedLineDO::getTaskCode, reqVO.getTaskCode())
                .likeIfPresent(FeedLineDO::getTaskName, reqVO.getTaskName())
                .eqIfPresent(FeedLineDO::getWorkstationCode, reqVO.getWorkstationCode())
                .likeIfPresent(FeedLineDO::getWorkstationName, reqVO.getWorkstationName())
                .eqIfPresent(FeedLineDO::getStatus, reqVO.getStatus())
                .eqIfPresent(FeedLineDO::getVendorCode, reqVO.getVendorCode())
                .eqIfPresent(FeedLineDO::getFeedbackStatus, reqVO.getFeedbackStatus())
                .eqIfPresent(FeedLineDO::getFeedbackCode, reqVO.getFeedbackCode())
                .eqIfPresent(FeedLineDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(FeedLineDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(FeedLineDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(FeedLineDO::getBarcodeNumber, reqVO.getBarcodeNumber())
                .eqIfPresent(FeedLineDO::getSequence, reqVO.getSequence())
                .eqIfPresent(FeedLineDO::getSequenceOrder, reqVO.getSequenceOrder())
                .eqIfPresent(FeedLineDO::getErpBatchCode, reqVO.getErpBatchCode())
                .eqIfPresent(FeedLineDO::getParentBatchCode, reqVO.getParentBatchCode())
                .orderByDesc(FeedLineDO::getId));
    }

    /**
     * 生产领用汇总查询
     * @return 领用汇总列表
     */
    IPage<FeedLineSummaryVO> selectMaterialUsageSummary(Page<FeedLineSummaryVO> page, @Param("req") FeedLinePageReqVO req);

}
