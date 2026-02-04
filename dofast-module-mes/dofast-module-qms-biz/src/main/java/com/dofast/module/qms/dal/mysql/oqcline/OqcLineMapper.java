package com.dofast.module.qms.dal.mysql.oqcline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.oqcline.OqcLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.oqcline.vo.*;

/**
 * 出货检验单行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface OqcLineMapper extends BaseMapperX<OqcLineDO> {
    int updateCrMajMinQuantity(OqcLineDO oqcLineDO);
    default int deleteByOqcId(Long oqcId){
        return delete(new LambdaQueryWrapperX<OqcLineDO>().eq(OqcLineDO::getOqcId,oqcId));
    }
    default PageResult<OqcLineDO> selectPage(OqcLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<OqcLineDO>()
                .eqIfPresent(OqcLineDO::getOqcId, reqVO.getOqcId())
                .eqIfPresent(OqcLineDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(OqcLineDO::getIndexCode, reqVO.getIndexCode())
                .likeIfPresent(OqcLineDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(OqcLineDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(OqcLineDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(OqcLineDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(OqcLineDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(OqcLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(OqcLineDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(OqcLineDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(OqcLineDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(OqcLineDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(OqcLineDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(OqcLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OqcLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(OqcLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(OqcLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(OqcLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(OqcLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OqcLineDO::getId));
    }

    default List<OqcLineDO> selectList(OqcLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<OqcLineDO>()
                .eqIfPresent(OqcLineDO::getOqcId, reqVO.getOqcId())
                .eqIfPresent(OqcLineDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(OqcLineDO::getIndexCode, reqVO.getIndexCode())
                .eqIfPresent(OqcLineDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(OqcLineDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(OqcLineDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(OqcLineDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(OqcLineDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(OqcLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(OqcLineDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(OqcLineDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(OqcLineDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(OqcLineDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(OqcLineDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(OqcLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(OqcLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(OqcLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(OqcLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(OqcLineDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(OqcLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(OqcLineDO::getId));
    }

}
