package com.dofast.module.cal.api.team;

import com.dofast.module.cal.api.team.dto.TeamDTO;
import com.dofast.module.cal.api.team.dto.TeamMemberDTO;

import java.util.List;

public interface TeamApi {

    public TeamDTO getTeamById(Long teamId);

    public TeamDTO getTeamByCode(String teamCode);

    public List<TeamMemberDTO> getTeamMemberByCode(String teamCode , String shiftInfo);

    public List<TeamMemberDTO> getTeamMemberById(Long teamId , String shiftInfo);


}
