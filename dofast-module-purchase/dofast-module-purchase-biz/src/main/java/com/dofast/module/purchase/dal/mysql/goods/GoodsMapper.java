package com.dofast.module.purchase.dal.mysql.goods;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.purchase.dal.dataobject.goods.GoodsDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.purchase.controller.admin.goods.vo.*;

/**
 * 采购商品明细 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface GoodsMapper extends BaseMapperX<GoodsDO> {

    default PageResult<GoodsDO> selectPage(GoodsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<GoodsDO>()
                .eqIfPresent(GoodsDO::getPurchaseId, reqVO.getPurchaseId())
                .eqIfPresent(GoodsDO::getGoodsNumber, reqVO.getGoodsNumber())
                .likeIfPresent(GoodsDO::getGoodsName, reqVO.getGoodsName())
                .eqIfPresent(GoodsDO::getGoodsSpecs, reqVO.getGoodsSpecs())
                .eqIfPresent(GoodsDO::getMonovalent, reqVO.getMonovalent())
                .eqIfPresent(GoodsDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(GoodsDO::getTaxes, reqVO.getTaxes())
                .eqIfPresent(GoodsDO::getTotal, reqVO.getTotal())
                .likeIfPresent(GoodsDO::getCategoryName, reqVO.getCategoryName())
                .likeIfPresent(GoodsDO::getBrandName, reqVO.getBrandName())
                .betweenIfPresent(GoodsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(GoodsDO::getCompany, reqVO.getCompany())
                .eqIfPresent(GoodsDO::getPoNo, reqVO.getPoNo())
                .eqIfPresent(GoodsDO::getReceiveNum, reqVO.getReceiveNum())
                .eqIfPresent(GoodsDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(GoodsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(GoodsDO::getReceiveTime, reqVO.getReceiveTime())
                .eqIfPresent(GoodsDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(GoodsDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(GoodsDO::getConsequence, reqVO.getConsequence())
                .eqIfPresent(GoodsDO::getErpReceiveCode, reqVO.getErpReceiveCode())
                .likeIfPresent(GoodsDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(GoodsDO::getReceiveSeq, reqVO.getReceiveSeq())
                .eqIfPresent(GoodsDO::getReceivedNum, reqVO.getReceivedNum())
                .inIfPresent(GoodsDO::getBatchCode , reqVO.getBatchCodeList())
                .orderByDesc(GoodsDO::getId));
    }

    default List<GoodsDO> selectList(GoodsExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<GoodsDO>()
                .eqIfPresent(GoodsDO::getPurchaseId, reqVO.getPurchaseId())
                .eqIfPresent(GoodsDO::getGoodsNumber, reqVO.getGoodsNumber())
                .likeIfPresent(GoodsDO::getGoodsName, reqVO.getGoodsName())
                .eqIfPresent(GoodsDO::getGoodsSpecs, reqVO.getGoodsSpecs())
                .eqIfPresent(GoodsDO::getMonovalent, reqVO.getMonovalent())
                .eqIfPresent(GoodsDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(GoodsDO::getTaxes, reqVO.getTaxes())
                .eqIfPresent(GoodsDO::getTotal, reqVO.getTotal())
                .likeIfPresent(GoodsDO::getCategoryName, reqVO.getCategoryName())
                .likeIfPresent(GoodsDO::getBrandName, reqVO.getBrandName())
                .betweenIfPresent(GoodsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(GoodsDO::getCompany, reqVO.getCompany())
                .eqIfPresent(GoodsDO::getPoNo, reqVO.getPoNo())
                .eqIfPresent(GoodsDO::getReceiveNum, reqVO.getReceiveNum())
                .eqIfPresent(GoodsDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(GoodsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(GoodsDO::getReceiveTime, reqVO.getReceiveTime())
                .eqIfPresent(GoodsDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(GoodsDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(GoodsDO::getConsequence, reqVO.getConsequence())
                .eqIfPresent(GoodsDO::getErpReceiveCode, reqVO.getErpReceiveCode())
                .likeIfPresent(GoodsDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(GoodsDO::getReceiveSeq, reqVO.getReceiveSeq())
                .eqIfPresent(GoodsDO::getReceivedNum, reqVO.getReceivedNum())
                .orderByDesc(GoodsDO::getId));
    }

    default GoodsDO selectOriginOrder(GoodsExportReqVO reqVO) {
        return selectOne(new LambdaQueryWrapperX<GoodsDO>()
                .eqIfPresent(GoodsDO::getPurchaseId, reqVO.getPurchaseId())
                .eqIfPresent(GoodsDO::getGoodsNumber, reqVO.getGoodsNumber())
                .likeIfPresent(GoodsDO::getGoodsName, reqVO.getGoodsName())
                .eqIfPresent(GoodsDO::getGoodsSpecs, reqVO.getGoodsSpecs())
                .eqIfPresent(GoodsDO::getMonovalent, reqVO.getMonovalent())
                .eqIfPresent(GoodsDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(GoodsDO::getTaxes, reqVO.getTaxes())
                .eqIfPresent(GoodsDO::getTotal, reqVO.getTotal())
                .likeIfPresent(GoodsDO::getCategoryName, reqVO.getCategoryName())
                .likeIfPresent(GoodsDO::getBrandName, reqVO.getBrandName())
                .betweenIfPresent(GoodsDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(GoodsDO::getCompany, reqVO.getCompany())
                .eqIfPresent(GoodsDO::getPoNo, reqVO.getPoNo())
                .eqIfPresent(GoodsDO::getReceiveNum, reqVO.getReceiveNum())
                .eqIfPresent(GoodsDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(GoodsDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(GoodsDO::getReceiveTime, reqVO.getReceiveTime())
                .eqIfPresent(GoodsDO::getBatchCode, reqVO.getBatchCode())
                .eqIfPresent(GoodsDO::getParentBatchCode, reqVO.getParentBatchCode())
                .eqIfPresent(GoodsDO::getConsequence, reqVO.getConsequence())
                .eqIfPresent(GoodsDO::getErpReceiveCode, reqVO.getErpReceiveCode())
                .likeIfPresent(GoodsDO::getVendorName, reqVO.getVendorName())
                .eqIfPresent(GoodsDO::getReceiveSeq, reqVO.getReceiveSeq())
                .eqIfPresent(GoodsDO::getReceivedNum, reqVO.getReceivedNum())
                .orderByAsc(GoodsDO::getId)
                .last("LIMIT 1"));
    }


}
