package com.dofast.module.mes.dal.mysql.qualityabnormity;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.mes.dal.dataobject.qualityabnormity.QualityAbnormityDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.mes.controller.admin.qualityabnormity.vo.*;

/**
 * 品质异常信息 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface QualityAbnormityMapper extends BaseMapperX<QualityAbnormityDO> {

    default PageResult<QualityAbnormityDO> selectPage(QualityAbnormityPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QualityAbnormityDO>()
                .betweenIfPresent(QualityAbnormityDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(QualityAbnormityDO::getItemCode, reqVO.getItemCode())
                .likeIfPresent(QualityAbnormityDO::getItemName, reqVO.getItemName())
                .eqIfPresent(QualityAbnormityDO::getBatchesCode, reqVO.getBatchesCode())
                .eqIfPresent(QualityAbnormityDO::getBatches, reqVO.getBatches())
                .eqIfPresent(QualityAbnormityDO::getBadDescription, reqVO.getBadDescription())
                .eqIfPresent(QualityAbnormityDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(QualityAbnormityDO::getBadCode, reqVO.getBadCode())
                .betweenIfPresent(QualityAbnormityDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(QualityAbnormityDO::getInspector, reqVO.getInspector())
                .betweenIfPresent(QualityAbnormityDO::getReinspectDate, reqVO.getReinspectDate())
                .eqIfPresent(QualityAbnormityDO::getReinspector, reqVO.getReinspector())
                .eqIfPresent(QualityAbnormityDO::getReinspectConclusion, reqVO.getReinspectConclusion())
                .eqIfPresent(QualityAbnormityDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(QualityAbnormityDO::getSaleNum, reqVO.getSaleNum())
                .eqIfPresent(QualityAbnormityDO::getInspectGroup, reqVO.getInspectGroup())
                .eqIfPresent(QualityAbnormityDO::getBadQuantity, reqVO.getBadQuantity())
                .orderByDesc(QualityAbnormityDO::getId));
    }

    default List<QualityAbnormityDO> selectList(QualityAbnormityExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<QualityAbnormityDO>()
                .betweenIfPresent(QualityAbnormityDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(QualityAbnormityDO::getItemCode, reqVO.getItemCode())
                .eqIfPresent(QualityAbnormityDO::getItemName, reqVO.getItemName())
                .eqIfPresent(QualityAbnormityDO::getBatchesCode, reqVO.getBatchesCode())
                .eqIfPresent(QualityAbnormityDO::getBatches, reqVO.getBatches())
                .eqIfPresent(QualityAbnormityDO::getBadDescription, reqVO.getBadDescription())
                .eqIfPresent(QualityAbnormityDO::getQuantity, reqVO.getQuantity())
                .eqIfPresent(QualityAbnormityDO::getBadCode, reqVO.getBadCode())
                .betweenIfPresent(QualityAbnormityDO::getInspectDate, reqVO.getInspectDate())
                .eqIfPresent(QualityAbnormityDO::getInspector, reqVO.getInspector())
                .betweenIfPresent(QualityAbnormityDO::getReinspectDate, reqVO.getReinspectDate())
                .eqIfPresent(QualityAbnormityDO::getReinspector, reqVO.getReinspector())
                .eqIfPresent(QualityAbnormityDO::getReinspectConclusion, reqVO.getReinspectConclusion())
                .eqIfPresent(QualityAbnormityDO::getOrderNum, reqVO.getOrderNum())
                .eqIfPresent(QualityAbnormityDO::getSaleNum, reqVO.getSaleNum())
                .eqIfPresent(QualityAbnormityDO::getInspectGroup, reqVO.getInspectGroup())
                .eqIfPresent(QualityAbnormityDO::getBadQuantity, reqVO.getBadQuantity())
                .orderByDesc(QualityAbnormityDO::getId));
    }

}
