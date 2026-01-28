package com.dofast.module.cal.service.teammember;

import com.dofast.module.cal.dal.dataobject.team.TeamDO;
import com.dofast.module.cal.dal.mysql.team.TeamMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.util.*;
import com.dofast.module.cal.controller.admin.teammember.vo.*;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;
import com.dofast.framework.common.pojo.PageResult;

import com.dofast.module.cal.convert.teammember.TeamMemberConvert;
import com.dofast.module.cal.dal.mysql.teammember.TeamMemberMapper;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.module.cal.enums.ErrorCodeConstants.*;

/**
 * 班组成员 Service 实现类
 *
 * @author 惠智造
 */
@Service
@Validated
public class TeamMemberServiceImpl implements TeamMemberService {

    @Resource
    private TeamMemberMapper teamMemberMapper;

    @Resource
    private TeamMapper teamMapper;

    @Override
    public Long createTeamMember(TeamMemberCreateReqVO createReqVO) {
        // 插入
        TeamMemberDO teamMember = TeamMemberConvert.INSTANCE.convert(createReqVO);
        teamMemberMapper.insert(teamMember);
        // 追加成员人数到班组表
        Long count = teamMemberMapper.selectCount(TeamMemberDO::getTeamId, teamMember.getTeamId());
        TeamDO teamDO = teamMapper.selectOne(TeamDO::getId, teamMember.getTeamId());
        teamDO.setPersonCount(count);;
        teamMapper.updateById(teamDO);
        // 返回
        return teamMember.getId();
    }

    @Override
    public void updateTeamMember(TeamMemberUpdateReqVO updateReqVO) {
        // 校验存在
        validateTeamMemberExists(updateReqVO.getId());
        // 更新
        TeamMemberDO updateObj = TeamMemberConvert.INSTANCE.convert(updateReqVO);
        teamMemberMapper.updateById(updateObj);
    }

    @Override
    public void deleteTeamMember(Long id) {
        // 校验存在
        validateTeamMemberExists(id);
        // 删除
        teamMemberMapper.deleteById(id);
    }

    private void validateTeamMemberExists(Long id) {
        if (teamMemberMapper.selectById(id) == null) {
            throw exception(TEAM_MEMBER_NOT_EXISTS);
        }
    }

    @Override
    public TeamMemberDO getTeamMember(Long id) {
        return teamMemberMapper.selectById(id);
    }

    @Override
    public List<TeamMemberDO> getTeamMemberByTeamId(Long id){
        return teamMemberMapper.selectList(TeamMemberDO::getTeamId, id);
    }

    @Override
    public List<TeamMemberDO> getTeamMemberList(Collection<Long> ids) {
        return teamMemberMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<TeamMemberDO> getTeamMemberPage(TeamMemberPageReqVO pageReqVO) {
        return teamMemberMapper.selectPage(pageReqVO);
    }

    @Override
    public List<TeamMemberDO> getTeamMemberList(TeamMemberExportReqVO exportReqVO) {
        return teamMemberMapper.selectList(exportReqVO);
    }

    @Override
    public void updateBatchTeamMember(List<TeamMemberDO> updateList){
        teamMemberMapper.updateBatch(updateList);
    }


}
