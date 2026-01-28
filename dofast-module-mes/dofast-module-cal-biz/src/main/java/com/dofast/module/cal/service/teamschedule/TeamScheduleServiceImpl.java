package com.dofast.module.cal.service.teamschedule;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.cal.controller.admin.teamschedule.vo.*;
import com.dofast.module.cal.dal.dataobject.teamschedule.TeamScheduleDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cal.convert.teamschedule.TeamScheduleConvert;
import com.dofast.module.cal.dal.mysql.teamschedule.TeamScheduleMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cal.enums.ErrorCodeConstants.*;



import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;


/**
 * 班组排班 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class TeamScheduleServiceImpl implements TeamScheduleService {

    @Resource
    private TeamScheduleMapper teamScheduleMapper;

    @Override
    public Long createTeamSchedule(TeamScheduleCreateReqVO createReqVO) {
        // 插入
        TeamScheduleDO teamSchedule = TeamScheduleConvert.INSTANCE.convert(createReqVO);
        teamScheduleMapper.insert(teamSchedule);
        // 返回
        return teamSchedule.getId();
    }

    @Override
    public void updateTeamSchedule(TeamScheduleUpdateReqVO updateReqVO) {
        // 校验存在
        validateTeamScheduleExists(updateReqVO.getId());
        // 更新
        TeamScheduleDO updateObj = TeamScheduleConvert.INSTANCE.convert(updateReqVO);
        teamScheduleMapper.updateById(updateObj);
    }

    @Override
    public void deleteTeamSchedule(Long id) {
        // 校验存在
        validateTeamScheduleExists(id);
        // 删除
        teamScheduleMapper.deleteById(id);
    }

    private void validateTeamScheduleExists(Long id) {
        if (teamScheduleMapper.selectById(id) == null) {
            throw exception(TEAM_SCHEDULE_NOT_EXISTS);
        }
    }

    @Override
    public TeamScheduleDO getTeamSchedule(Long id) {
        return teamScheduleMapper.selectById(id);
    }

    @Override
    public List<TeamScheduleDO> getTeamScheduleList(Collection<Long> ids) {

        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }

        return teamScheduleMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TeamScheduleDO> getTeamSchedulePage(TeamSchedulePageReqVO pageReqVO) {
        return teamScheduleMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TeamScheduleDO> getTeamScheduleList(TeamScheduleExportReqVO exportReqVO) {
        return teamScheduleMapper.selectList(exportReqVO);
    }

}
