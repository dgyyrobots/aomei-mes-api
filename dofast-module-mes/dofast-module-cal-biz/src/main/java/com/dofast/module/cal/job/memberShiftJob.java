package com.dofast.module.cal.job;
import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.cal.controller.admin.team.vo.TeamExportReqVO;
import com.dofast.module.cal.controller.admin.teammember.vo.TeamMemberExportReqVO;
import com.dofast.module.cal.controller.admin.teamschedule.vo.TeamScheduleExportReqVO;
import com.dofast.module.cal.dal.dataobject.team.TeamDO;
import com.dofast.module.cal.dal.dataobject.teammember.TeamMemberDO;
import com.dofast.module.cal.dal.dataobject.teamschedule.TeamScheduleDO;
import com.dofast.module.cal.service.team.TeamService;
import com.dofast.module.cal.service.teammember.TeamMemberService;
import com.dofast.module.cal.service.teamschedule.TeamScheduleService;
import com.dofast.module.system.dal.dataobject.dict.DictDataDO;
import com.dofast.module.system.service.dict.DictDataService;
import com.dofast.module.system.service.dict.DictTypeService;
import java.time.LocalDate;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class memberShiftJob implements JobHandler{

    @Resource
    private TeamService teamService;

    @Resource
    private TeamMemberService teamMemberService;

    @Resource
    private TeamScheduleService teamScheduleService;

    @Resource
    private DictTypeService dictTypeService;

    @Resource
    private DictDataService dictDataService;

    @Override
    public String execute(String param) throws Exception {
        List<TeamScheduleDO> teamScheduleList = teamScheduleService.getTeamScheduleList(new TeamScheduleExportReqVO().setWorkDate(LocalDate.now()));
        if(teamScheduleList.isEmpty()){
            return "未找到要变更的班组信息";
        }
        List<TeamMemberDO> updateTeamMemberList = new ArrayList<>();

        // teamScheduleList根据teamId字段进行分类
        Map<Long, List<TeamScheduleDO>> teamScheduleMap = teamScheduleList.stream()
                .collect(Collectors.groupingBy(TeamScheduleDO::getTeamId));
        // 循环teamScheduleMap, 根据不同的teamId获取不同的teamMember列表
        for (Map.Entry<Long, List<TeamScheduleDO>> entry : teamScheduleMap.entrySet()) {
            Long teamId = entry.getKey();
            List<TeamScheduleDO> teamScheduleDOList = entry.getValue();
            // 基于当前teamId获取班组成员班次信息. 进行比对
            List<TeamMemberDO> teamMemberList = teamMemberService.getTeamMemberList(new TeamMemberExportReqVO().setTeamId(teamId));
            for (TeamMemberDO teamMemberDO : teamMemberList) {
                // 2025-11-26追加判定: 若当前的班组成员不再排班表中, 则默认修改为未排班
                if (!teamScheduleDOList.stream().anyMatch(teamScheduleDO -> teamScheduleDO.getUserId().equals(teamMemberDO.getUserId()))) {
                    teamMemberDO.setShiftInfo("2");
                    updateTeamMemberList.add(teamMemberDO);
                }
                // 剩余数据基于排班表进行变更
                for (TeamScheduleDO teamScheduleDO : teamScheduleDOList) {
                    if (teamMemberDO.getUserId().equals(teamScheduleDO.getUserId())) {
                        // 开始变更班组信息
                        teamMemberDO.setShiftInfo(teamScheduleDO.getShiftType());
                        updateTeamMemberList.add(teamMemberDO);
                    }
                }

            }
        }
        teamMemberService.updateBatchTeamMember(updateTeamMemberList);
        return "success";
    }
}
