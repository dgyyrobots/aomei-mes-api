package com.dofast.module.cmms.dal.mysql.dvcheckplanlinelog;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanlinelog.DvCheckPlanLineLogDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo.*;

/**
 * 点检计划记录单身 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface DvCheckPlanLineLogMapper extends BaseMapperX<DvCheckPlanLineLogDO> {

    default PageResult<DvCheckPlanLineLogDO> selectPage(DvCheckPlanLineLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvCheckPlanLineLogDO>()
                .eqIfPresent(DvCheckPlanLineLogDO::getHeaderId, reqVO.getHeaderId())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectCode, reqVO.getSubjectCode())
                .likeIfPresent(DvCheckPlanLineLogDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectType, reqVO.getSubjectType())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectContent, reqVO.getSubjectContent())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectStandard, reqVO.getSubjectStandard())
                .eqIfPresent(DvCheckPlanLineLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckPlanLineLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckPlanLineLogDO::getId));
    }

    default List<DvCheckPlanLineLogDO> selectList(DvCheckPlanLineLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvCheckPlanLineLogDO>()
                .eqIfPresent(DvCheckPlanLineLogDO::getHeaderId, reqVO.getHeaderId())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectId, reqVO.getSubjectId())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectCode, reqVO.getSubjectCode())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectName, reqVO.getSubjectName())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectType, reqVO.getSubjectType())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectContent, reqVO.getSubjectContent())
                .eqIfPresent(DvCheckPlanLineLogDO::getSubjectStandard, reqVO.getSubjectStandard())
                .eqIfPresent(DvCheckPlanLineLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckPlanLineLogDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckPlanLineLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckPlanLineLogDO::getId));
    }

}
