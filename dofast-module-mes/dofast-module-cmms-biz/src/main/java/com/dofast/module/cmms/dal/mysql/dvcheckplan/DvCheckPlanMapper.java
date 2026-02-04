package com.dofast.module.cmms.dal.mysql.dvcheckplan;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvcheckplan.DvCheckPlanDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvcheckplan.vo.*;

/**
 * 设备点检保养计划头 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface DvCheckPlanMapper extends BaseMapperX<DvCheckPlanDO> {

    default DvCheckPlanDO  checkPlanCodeUnique (DvCheckPlanBaseVO baseVO ){
        return selectOne(new LambdaQueryWrapperX<DvCheckPlanDO>().eq(DvCheckPlanDO::getPlanCode,baseVO.getPlanCode()));
    }
    default PageResult<DvCheckPlanDO> selectPage(DvCheckPlanPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvCheckPlanDO>()
                .eqIfPresent(DvCheckPlanDO::getPlanCode, reqVO.getPlanCode())
                .likeIfPresent(DvCheckPlanDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(DvCheckPlanDO::getPlanType, reqVO.getPlanType())
                .betweenIfPresent(DvCheckPlanDO::getStartDate, reqVO.getStartDate())
                .betweenIfPresent(DvCheckPlanDO::getEndDate, reqVO.getEndDate())
                .eqIfPresent(DvCheckPlanDO::getCycleType, reqVO.getCycleType())
                .eqIfPresent(DvCheckPlanDO::getCycleCount, reqVO.getCycleCount())
                .eqIfPresent(DvCheckPlanDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DvCheckPlanDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckPlanDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckPlanDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckPlanDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckPlanDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckPlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckPlanDO::getId));
    }

    default List<DvCheckPlanDO> selectList(DvCheckPlanExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvCheckPlanDO>()
                .eqIfPresent(DvCheckPlanDO::getPlanCode, reqVO.getPlanCode())
                .eqIfPresent(DvCheckPlanDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(DvCheckPlanDO::getPlanType, reqVO.getPlanType())
                .betweenIfPresent(DvCheckPlanDO::getStartDate, reqVO.getStartDate())
                .betweenIfPresent(DvCheckPlanDO::getEndDate, reqVO.getEndDate())
                .eqIfPresent(DvCheckPlanDO::getCycleType, reqVO.getCycleType())
                .eqIfPresent(DvCheckPlanDO::getCycleCount, reqVO.getCycleCount())
                .eqIfPresent(DvCheckPlanDO::getStatus, reqVO.getStatus())
                .eqIfPresent(DvCheckPlanDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckPlanDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckPlanDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckPlanDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckPlanDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckPlanDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckPlanDO::getId));
    }

}
