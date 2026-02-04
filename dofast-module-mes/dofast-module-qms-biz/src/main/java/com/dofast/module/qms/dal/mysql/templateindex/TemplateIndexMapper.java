package com.dofast.module.qms.dal.mysql.templateindex;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.qms.dal.dataobject.templateindex.TemplateIndexDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.qms.controller.admin.templateindex.vo.*;

/**
 * 检测模板-检测项 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface TemplateIndexMapper extends BaseMapperX<TemplateIndexDO> {
    default int deleteByTemplateId(Long templateId){
        return delete(new LambdaQueryWrapperX<TemplateIndexDO>().eq(TemplateIndexDO::getTemplateId,templateId));
    }
    default PageResult<TemplateIndexDO> selectPage(TemplateIndexPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TemplateIndexDO>()
                .eqIfPresent(TemplateIndexDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateIndexDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(TemplateIndexDO::getIndexCode, reqVO.getIndexCode())
                .likeIfPresent(TemplateIndexDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(TemplateIndexDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(TemplateIndexDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(TemplateIndexDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(TemplateIndexDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(TemplateIndexDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TemplateIndexDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(TemplateIndexDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(TemplateIndexDO::getDocUrl, reqVO.getDocUrl())
                .eqIfPresent(TemplateIndexDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateIndexDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateIndexDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateIndexDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateIndexDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(TemplateIndexDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateIndexDO::getId));
    }
    default List<TemplateIndexDO> selectList(TemplateIndexListVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TemplateIndexDO>()
                .eqIfPresent(TemplateIndexDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateIndexDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(TemplateIndexDO::getIndexCode, reqVO.getIndexCode())
                .eqIfPresent(TemplateIndexDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(TemplateIndexDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(TemplateIndexDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(TemplateIndexDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(TemplateIndexDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(TemplateIndexDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TemplateIndexDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(TemplateIndexDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(TemplateIndexDO::getDocUrl, reqVO.getDocUrl())
                .eqIfPresent(TemplateIndexDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateIndexDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateIndexDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateIndexDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateIndexDO::getAttr4, reqVO.getAttr4())
                .eqIfPresent(TemplateIndexDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateIndexDO::getId));
    }

    default List<TemplateIndexDO> selectList(TemplateIndexExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TemplateIndexDO>()
                .eqIfPresent(TemplateIndexDO::getTemplateId, reqVO.getTemplateId())
                .eqIfPresent(TemplateIndexDO::getIndexId, reqVO.getIndexId())
                .eqIfPresent(TemplateIndexDO::getIndexCode, reqVO.getIndexCode())
                .eqIfPresent(TemplateIndexDO::getIndexName, reqVO.getIndexName())
                .eqIfPresent(TemplateIndexDO::getIndexType, reqVO.getIndexType())
                .eqIfPresent(TemplateIndexDO::getQcTool, reqVO.getQcTool())
                .eqIfPresent(TemplateIndexDO::getCheckMethod, reqVO.getCheckMethod())
                .eqIfPresent(TemplateIndexDO::getStanderVal, reqVO.getStanderVal())
                .eqIfPresent(TemplateIndexDO::getUnitOfMeasure, reqVO.getUnitOfMeasure())
                .eqIfPresent(TemplateIndexDO::getThresholdMax, reqVO.getThresholdMax())
                .eqIfPresent(TemplateIndexDO::getThresholdMin, reqVO.getThresholdMin())
                .eqIfPresent(TemplateIndexDO::getDocUrl, reqVO.getDocUrl())
                .eqIfPresent(TemplateIndexDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TemplateIndexDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TemplateIndexDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TemplateIndexDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TemplateIndexDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(TemplateIndexDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TemplateIndexDO::getId));
    }

}
