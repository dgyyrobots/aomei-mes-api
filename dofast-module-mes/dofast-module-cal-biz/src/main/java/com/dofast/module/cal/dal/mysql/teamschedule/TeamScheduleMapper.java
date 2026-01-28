package com.dofast.module.cal.dal.mysql.teamschedule;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cal.dal.dataobject.teamschedule.TeamScheduleDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cal.controller.admin.teamschedule.vo.*;

/**
 * 班组排班 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface TeamScheduleMapper extends BaseMapperX<TeamScheduleDO> {

    default PageResult<TeamScheduleDO> selectPage(TeamSchedulePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TeamScheduleDO>()
                .eqIfPresent(TeamScheduleDO::getTeamId, reqVO.getTeamId())
                .eqIfPresent(TeamScheduleDO::getUserId, reqVO.getUserId())
                .eqIfPresent(TeamScheduleDO::getWorkDate, reqVO.getWorkDate())
                .eqIfPresent(TeamScheduleDO::getShiftType, reqVO.getShiftType())
                .eqIfPresent(TeamScheduleDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TeamScheduleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TeamScheduleDO::getId));
    }

    default List<TeamScheduleDO> selectList(TeamScheduleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TeamScheduleDO>()
                .eqIfPresent(TeamScheduleDO::getTeamId, reqVO.getTeamId())
                .eqIfPresent(TeamScheduleDO::getUserId, reqVO.getUserId())
                .eqIfPresent(TeamScheduleDO::getWorkDate, reqVO.getWorkDate())
                .eqIfPresent(TeamScheduleDO::getShiftType, reqVO.getShiftType())
                .eqIfPresent(TeamScheduleDO::getRemark, reqVO.getRemark())
                .betweenIfPresent(TeamScheduleDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(TeamScheduleDO::getId));
    }

}
