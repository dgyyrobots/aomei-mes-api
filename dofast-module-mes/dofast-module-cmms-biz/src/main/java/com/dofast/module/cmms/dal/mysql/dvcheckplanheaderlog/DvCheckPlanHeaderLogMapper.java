package com.dofast.module.cmms.dal.mysql.dvcheckplanheaderlog;

import java.time.LocalDateTime;
import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog.DvCheckPlanHeaderLogDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

/**
 * 点检计划记录单头 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface DvCheckPlanHeaderLogMapper extends BaseMapperX<DvCheckPlanHeaderLogDO> {

    default PageResult<DvCheckPlanHeaderLogDO> selectPage(DvCheckPlanHeaderLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DvCheckPlanHeaderLogDO>()
                .eqIfPresent(DvCheckPlanHeaderLogDO::getPlanCode, reqVO.getPlanCode())
                .likeIfPresent(DvCheckPlanHeaderLogDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getPlanType, reqVO.getPlanType())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getMachineryCode, reqVO.getMachineryCode())
                .likeIfPresent(DvCheckPlanHeaderLogDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckPlanHeaderLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckPlanHeaderLogDO::getId));
    }

    default List<DvCheckPlanHeaderLogDO> selectList(DvCheckPlanHeaderLogExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DvCheckPlanHeaderLogDO>()
                .eqIfPresent(DvCheckPlanHeaderLogDO::getPlanCode, reqVO.getPlanCode())
                .likeIfPresent(DvCheckPlanHeaderLogDO::getPlanName, reqVO.getPlanName())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getPlanType, reqVO.getPlanType())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getMachineryCode, reqVO.getMachineryCode())
                .likeIfPresent(DvCheckPlanHeaderLogDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getRemark, reqVO.getRemark())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(DvCheckPlanHeaderLogDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(DvCheckPlanHeaderLogDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DvCheckPlanHeaderLogDO::getId));
    }

   public int selectCountInCycle(@Param("planCode")String planCode  , @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

}
