package com.dofast.module.cal.dal.mysql.team;

import java.util.*;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.dofast.framework.mybatis.core.mapper.BaseMapperX;
import com.dofast.module.cal.dal.dataobject.team.TeamDO;
import org.apache.ibatis.annotations.Mapper;
import com.dofast.module.cal.controller.admin.team.vo.*;

/**
 * 班组 Mapper
 *
 * @author 惠智造
 */
@Mapper
public interface TeamMapper extends BaseMapperX<TeamDO> {

    default PageResult<TeamDO> selectPage(TeamPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<TeamDO>()
                .eqIfPresent(TeamDO::getTeamCode, reqVO.getTeamCode())
                .likeIfPresent(TeamDO::getTeamName, reqVO.getTeamName())
                .eqIfPresent(TeamDO::getCalendarType, reqVO.getCalendarType())
                .eqIfPresent(TeamDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TeamDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TeamDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TeamDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TeamDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(TeamDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TeamDO::getPrincipalId, reqVO.getPrincipalId())
                .likeIfPresent(TeamDO::getPrincipalName, reqVO.getPrincipalName())
                .eqIfPresent(TeamDO::getPersonCount, reqVO.getPersonCount())
                .eqIfPresent(TeamDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TeamDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TeamDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TeamDO::getNightPrincipalId, reqVO.getNightPrincipalId())
                .likeIfPresent(TeamDO::getNightPrincipalName, reqVO.getNightPrincipalName())
                .orderByDesc(TeamDO::getId));
    }

    default List<TeamDO> selectList(TeamExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<TeamDO>()
                .eqIfPresent(TeamDO::getTeamCode, reqVO.getTeamCode())
                .likeIfPresent(TeamDO::getTeamName, reqVO.getTeamName())
                .eqIfPresent(TeamDO::getCalendarType, reqVO.getCalendarType())
                .eqIfPresent(TeamDO::getRemark, reqVO.getRemark())
                .eqIfPresent(TeamDO::getAttr1, reqVO.getAttr1())
                .eqIfPresent(TeamDO::getAttr2, reqVO.getAttr2())
                .eqIfPresent(TeamDO::getAttr3, reqVO.getAttr3())
                .eqIfPresent(TeamDO::getAttr4, reqVO.getAttr4())
                .betweenIfPresent(TeamDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(TeamDO::getPrincipalId, reqVO.getPrincipalId())
                .likeIfPresent(TeamDO::getPrincipalName, reqVO.getPrincipalName())
                .eqIfPresent(TeamDO::getPersonCount, reqVO.getPersonCount())
                .eqIfPresent(TeamDO::getMachineryName, reqVO.getMachineryName())
                .eqIfPresent(TeamDO::getMachineryCode, reqVO.getMachineryCode())
                .eqIfPresent(TeamDO::getMachineryId, reqVO.getMachineryId())
                .eqIfPresent(TeamDO::getNightPrincipalId, reqVO.getNightPrincipalId())
                .likeIfPresent(TeamDO::getNightPrincipalName, reqVO.getNightPrincipalName())
                .orderByDesc(TeamDO::getId));
    }

}
