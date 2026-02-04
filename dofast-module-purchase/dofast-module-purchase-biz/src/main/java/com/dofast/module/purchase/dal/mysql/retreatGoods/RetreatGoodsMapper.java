package com.dofast.module.purchase.dal.mysql.retreatGoods;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.purchase.dal.dataobject.goods.GoodsDO;
import com.dofast.module.purchase.dal.dataobject.retreatGoods.RetreatGoodsDO;
import liquibase.pro.packaged.R;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.purchase.controller.admin.retreatGoods.vo.*;

/**
 * ERP仓退单单身 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface RetreatGoodsMapper extends BaseMapperX<RetreatGoodsDO> {

    default PageResult<RetreatGoodsDO> selectPage(RetreatGoodsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<RetreatGoodsDO>()
                .eqIfPresent(RetreatGoodsDO::getRetreatId, reqVO.getRetreatId())
                .eqIfPresent(RetreatGoodsDO::getGoodsNumber, reqVO.getGoodsNumber())
                .likeIfPresent(RetreatGoodsDO::getGoodsName, reqVO.getGoodsName())
                .eqIfPresent(RetreatGoodsDO::getGoodsSpecs, reqVO.getGoodsSpecs())
                .eqIfPresent(RetreatGoodsDO::getCompany, reqVO.getCompany())
                .eqIfPresent(RetreatGoodsDO::getMonovalent, reqVO.getMonovalent())
                .eqIfPresent(RetreatGoodsDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(RetreatGoodsDO::getReceiveNum, reqVO.getReceiveNum())
                .eqIfPresent(RetreatGoodsDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RetreatGoodsDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RetreatGoodsDO::getConsequence, reqVO.getConsequence())
                .eqIfPresent(RetreatGoodsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RetreatGoodsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RetreatGoodsDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RetreatGoodsDO::getWarehouseCode, reqVO.getWarehouseCode())
                .likeIfPresent(RetreatGoodsDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RetreatGoodsDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RetreatGoodsDO::getLocationCode, reqVO.getLocationCode())
                .likeIfPresent(RetreatGoodsDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RetreatGoodsDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RetreatGoodsDO::getAreaCode, reqVO.getAreaCode())
                .likeIfPresent(RetreatGoodsDO::getAreaName, reqVO.getAreaName())

                .eqIfPresent(RetreatGoodsDO::getPoNo, reqVO.getPoNo())
                .eqIfPresent(RetreatGoodsDO::getPurchaseBatch, reqVO.getPurchaseBatch())
                .eqIfPresent(RetreatGoodsDO::getPurchaseConsequence, reqVO.getPurchaseConsequence())
                .eqIfPresent(RetreatGoodsDO::getPurchaseBatchConsequence, reqVO.getPurchaseBatchConsequence())
                .eqIfPresent(RetreatGoodsDO::getErpReceiveCode, reqVO.getErpReceiveCode())
                .eqIfPresent(RetreatGoodsDO::getReceiveSeq, reqVO.getReceiveSeq())
                .eqIfPresent(RetreatGoodsDO::getReasonCode, reqVO.getReasonCode())
                .orderByDesc(RetreatGoodsDO::getId));
    }

    default List<RetreatGoodsDO> selectList(RetreatGoodsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<RetreatGoodsDO>()
                .eqIfPresent(RetreatGoodsDO::getRetreatId, reqVO.getRetreatId())
                .eqIfPresent(RetreatGoodsDO::getGoodsNumber, reqVO.getGoodsNumber())
                .eqIfPresent(RetreatGoodsDO::getGoodsName, reqVO.getGoodsName())
                .eqIfPresent(RetreatGoodsDO::getGoodsSpecs, reqVO.getGoodsSpecs())
                .eqIfPresent(RetreatGoodsDO::getCompany, reqVO.getCompany())
                .eqIfPresent(RetreatGoodsDO::getMonovalent, reqVO.getMonovalent())
                .eqIfPresent(RetreatGoodsDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(RetreatGoodsDO::getReceiveNum, reqVO.getReceiveNum())
                .eqIfPresent(RetreatGoodsDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(RetreatGoodsDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(RetreatGoodsDO::getConsequence, reqVO.getConsequence())
                .eqIfPresent(RetreatGoodsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(RetreatGoodsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(RetreatGoodsDO::getWarehouseId, reqVO.getWarehouseId())
                .eqIfPresent(RetreatGoodsDO::getWarehouseCode, reqVO.getWarehouseCode())
                .eqIfPresent(RetreatGoodsDO::getWarehouseName, reqVO.getWarehouseName())
                .eqIfPresent(RetreatGoodsDO::getLocationId, reqVO.getLocationId())
                .eqIfPresent(RetreatGoodsDO::getLocationCode, reqVO.getLocationCode())
                .eqIfPresent(RetreatGoodsDO::getLocationName, reqVO.getLocationName())
                .eqIfPresent(RetreatGoodsDO::getAreaId, reqVO.getAreaId())
                .eqIfPresent(RetreatGoodsDO::getAreaCode, reqVO.getAreaCode())
                .eqIfPresent(RetreatGoodsDO::getAreaName, reqVO.getAreaName())
                .eqIfPresent(RetreatGoodsDO::getPurchaseBatch, reqVO.getPurchaseBatch())
                .eqIfPresent(RetreatGoodsDO::getPurchaseConsequence, reqVO.getPurchaseConsequence())
                .eqIfPresent(RetreatGoodsDO::getPurchaseBatchConsequence, reqVO.getPurchaseBatchConsequence())

                .eqIfPresent(RetreatGoodsDO::getErpReceiveCode, reqVO.getErpReceiveCode())
                .eqIfPresent(RetreatGoodsDO::getReceiveSeq, reqVO.getReceiveSeq())
                .eqIfPresent(RetreatGoodsDO::getReasonCode, reqVO.getReasonCode())
                .orderByDesc(RetreatGoodsDO::getId));
    }

}
