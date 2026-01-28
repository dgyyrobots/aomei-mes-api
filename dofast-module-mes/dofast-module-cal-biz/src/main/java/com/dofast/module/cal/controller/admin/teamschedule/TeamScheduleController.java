package com.dofast.module.cal.controller.admin.teamschedule;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.cal.controller.admin.teamschedule.vo.*;
import com.dofast.module.cal.dal.dataobject.teamschedule.TeamScheduleDO;
import com.dofast.module.cal.convert.teamschedule.TeamScheduleConvert;
import com.dofast.module.cal.service.teamschedule.TeamScheduleService;

@Tag(name = "管理后台 - 班组排班")
@RestController
@RequestMapping("/cal/team-schedule")
@Validated
public class TeamScheduleController {

    @Resource
    private TeamScheduleService teamScheduleService;

    @PostMapping("/create")
    @Operation(summary = "创建班组排班")
    @PreAuthorize("@ss.hasPermission('cal:team-schedule:create')")
    public CommonResult<Long> createTeamSchedule(@Valid @RequestBody TeamScheduleCreateReqVO createReqVO) {
        return success(teamScheduleService.createTeamSchedule(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新班组排班")
    @PreAuthorize("@ss.hasPermission('cal:team-schedule:update')")
    public CommonResult<Boolean> updateTeamSchedule(@Valid @RequestBody TeamScheduleUpdateReqVO updateReqVO) {
        teamScheduleService.updateTeamSchedule(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除班组排班")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cal:team-schedule:delete')")
    public CommonResult<Boolean> deleteTeamSchedule(@RequestParam("id") Long id) {
        teamScheduleService.deleteTeamSchedule(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得班组排班")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cal:team-schedule:query')")
    public CommonResult<TeamScheduleRespVO> getTeamSchedule(@RequestParam("id") Long id) {
        TeamScheduleDO teamSchedule = teamScheduleService.getTeamSchedule(id);
        return success(TeamScheduleConvert.INSTANCE.convert(teamSchedule));
    }

    @GetMapping("/list")
    @Operation(summary = "获得班组排班列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cal:team-schedule:query')")
    public CommonResult<List<TeamScheduleRespVO>> getTeamScheduleList(@RequestParam("ids") Collection<Long> ids) {
        List<TeamScheduleDO> list = teamScheduleService.getTeamScheduleList(ids);
        return success(TeamScheduleConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得班组排班分页")
    @PreAuthorize("@ss.hasPermission('cal:team-schedule:query')")
    public CommonResult<PageResult<TeamScheduleRespVO>> getTeamSchedulePage(@Valid TeamSchedulePageReqVO pageVO) {
        PageResult<TeamScheduleDO> pageResult = teamScheduleService.getTeamSchedulePage(pageVO);
        return success(TeamScheduleConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出班组排班 Excel")
    @PreAuthorize("@ss.hasPermission('cal:team-schedule:export')")
    @OperateLog(type = EXPORT)
    public void exportTeamScheduleExcel(@Valid TeamScheduleExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TeamScheduleDO> list = teamScheduleService.getTeamScheduleList(exportReqVO);
        // 导出 Excel
        List<TeamScheduleExcelVO> datas = TeamScheduleConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "班组排班.xls", "数据", TeamScheduleExcelVO.class, datas);
    }

}
