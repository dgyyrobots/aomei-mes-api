package com.dofast.module.qms.dal.mysql.iqcline;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.iqcline.IqcLineDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.iqcline.vo.*;

/**
 * 来料检验单行 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface IqcLineMapper extends BaseMapperX<IqcLineDO> {
    default int deleteByIqcId(Long iqcId){
        return delete(new LambdaQueryWrapperX<IqcLineDO>().eq(IqcLineDO::getIqcId,iqcId));
    }
    int updateCrMajMinQuantity(IqcLineDO iqcLineDO);
    default PageResult<IqcLineDO> selectPage(IqcLinePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<IqcLineDO>()
                .eqIfPresent(IqcLineDO::getIqcId, reqVO.getIqcId())
                .eqIfPresent(IqcLineDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(IqcLineDO::getIndexCode, reqVO.getIndexCode())
                .likeIfPresent(IqcLineDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(IqcLineDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(IqcLineDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(IqcLineDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(IqcLineDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(IqcLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IqcLineDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(IqcLineDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(IqcLineDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IqcLineDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IqcLineDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IqcLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IqcLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IqcLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IqcLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IqcLineDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(IqcLineDO::getInspectionValue1, reqVO.getInspectionValue1())
                .eqIfPresent(IqcLineDO::getInspectionValue2, reqVO.getInspectionValue2())
                .eqIfPresent(IqcLineDO::getInspectionValue3, reqVO.getInspectionValue3())
                .eqIfPresent(IqcLineDO::getInspectionValue4, reqVO.getInspectionValue4())
                .eqIfPresent(IqcLineDO::getInspectionValue5, reqVO.getInspectionValue5())
                .eqIfPresent(IqcLineDO::getInspectionResult, reqVO.getInspectionResult())
                .betweenIfPresent(IqcLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IqcLineDO::getId));
    }

    default List<IqcLineDO> selectList(IqcLineExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<IqcLineDO>()
                .eqIfPresent(IqcLineDO::getIqcId, reqVO.getIqcId())
                .eqIfPresent(IqcLineDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(IqcLineDO::getIndexCode, reqVO.getIndexCode())
                .likeIfPresent(IqcLineDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(IqcLineDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(IqcLineDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(IqcLineDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(IqcLineDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(IqcLineDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(IqcLineDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(IqcLineDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(IqcLineDO::getCrQuantity, reqVO.getCrQuantity())
                .eqIfPresent(IqcLineDO::getMajQuantity, reqVO.getMajQuantity())
                .eqIfPresent(IqcLineDO::getMinQuantity, reqVO.getMinQuantity())
                .eqIfPresent(IqcLineDO::getRemark, reqVO.getRemark())
                .eqIfPresent(IqcLineDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(IqcLineDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(IqcLineDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(IqcLineDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(IqcLineDO::getInspectionValue1, reqVO.getInspectionValue1())
                .eqIfPresent(IqcLineDO::getInspectionValue2, reqVO.getInspectionValue2())
                .eqIfPresent(IqcLineDO::getInspectionValue3, reqVO.getInspectionValue3())
                .eqIfPresent(IqcLineDO::getInspectionValue4, reqVO.getInspectionValue4())
                .eqIfPresent(IqcLineDO::getInspectionValue5, reqVO.getInspectionValue5())
                .eqIfPresent(IqcLineDO::getInspectionResult, reqVO.getInspectionResult())
                .betweenIfPresent(IqcLineDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(IqcLineDO::getId));
    }

}
