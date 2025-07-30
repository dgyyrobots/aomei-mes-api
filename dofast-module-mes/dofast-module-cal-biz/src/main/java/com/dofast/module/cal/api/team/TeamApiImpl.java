package com.dofast.module.cal.api.team;

import com.dofast.module.cal.api.team.dto.TeamDTO;
import com.dofast.module.cal.api.team.dto.TeamMemberDTO;
import com.dofast.module.cal.controller.admin.teammember.vo.TeamMemberExportReqVO;
import com.dofast.module.cal.convert.team.TeamConvert;
import com.dofast.module.cal.convert.teammember.TeamMemberConvert;
import com.dofast.module.cal.dal.dataobject.team.TeamDO;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;
import com.dofast.module.cal.service.team.TeamService;
import com.dofast.module.cal.service.teammember.TeamMemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeamApiImpl implements TeamApi {

    @Resource
    private TeamService teamService;

    @Resource
    private TeamMemberService teamMemberService;

    @Override
    public TeamDTO getTeamById(Long teamId) {
       TeamDO teamDO = teamService.getTeam(teamId);
        return TeamConvert.INSTANCE.convert01(teamDO);
    }

    @Override
    public TeamDTO getTeamByCode(String teamCode) {
        TeamDO teamDO = teamService.getTeam(teamCode);
        return TeamConvert.INSTANCE.convert01(teamDO);
    }

    @Override
    public List<TeamMemberDTO> getTeamMemberByCode(String teamCode , String shiftInfo){
        TeamDO teamDO = teamService.getTeam(teamCode);
        List<TeamMemberDO> teamMemberList = teamMemberService.getTeamMemberList(new TeamMemberExportReqVO().setTeamId(teamDO.getId()).setShiftInfo(shiftInfo));
        return TeamMemberConvert.INSTANCE.convertList03(teamMemberList);
    }

    @Override
    public List<TeamMemberDTO> getTeamMemberById(Long teamId , String shiftInfo){
        List<TeamMemberDO> teamMemberList = teamMemberService.getTeamMemberList(new TeamMemberExportReqVO().setTeamId(teamId).setShiftInfo(shiftInfo));
        return TeamMemberConvert.INSTANCE.convertList03(teamMemberList);
    }

}
