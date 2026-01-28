package com.dofast.module.cal.controller.admin.teamshift;

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

import com.dofast.module.cal.controller.admin.teamshift.vo.*;
import com.dofast.module.cal.dal.dataobject.teamshift.TeamshiftDO;
import com.dofast.module.cal.convert.teamshift.TeamshiftConvert;
import com.dofast.module.cal.service.teamshift.TeamshiftService;

@Tag(name = "管理后台 - 班组排班")
@RestController
@RequestMapping("/cal/teamshift")
@Validated
public class TeamshiftController {

    @Resource
    private TeamshiftService teamshiftService;

    @PostMapping("/create")
    @Operation(summary = "创建班组排班")
    //@PreAuthorize("@ss.hasPermission('cal:teamshift:create')")
    public CommonResult<Long> createTeamshift(@Valid @RequestBody TeamshiftCreateReqVO createReqVO) {
        return success(teamshiftService.createTeamshift(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新班组排班")
    //@PreAuthorize("@ss.hasPermission('cal:teamshift:update')")
    public CommonResult<Boolean> updateTeamshift(@Valid @RequestBody TeamshiftUpdateReqVO updateReqVO) {
        teamshiftService.updateTeamshift(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除班组排班")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('cal:teamshift:delete')")
    public CommonResult<Boolean> deleteTeamshift(@RequestParam("id") Long id) {
        teamshiftService.deleteTeamshift(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得班组排班")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('cal:teamshift:query')")
    public CommonResult<TeamshiftRespVO> getTeamshift(@RequestParam("id") Long id) {
        TeamshiftDO teamshift = teamshiftService.getTeamshift(id);
        return success(TeamshiftConvert.INSTANCE.convert(teamshift));
    }

    @GetMapping("/list")
    @Operation(summary = "获得班组排班列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    //@PreAuthorize("@ss.hasPermission('cal:teamshift:query')")
    public CommonResult<List<TeamshiftRespVO>> getTeamshiftList(@RequestParam("ids") Collection<Long> ids) {
        List<TeamshiftDO> list = teamshiftService.getTeamshiftList(ids);
        return success(TeamshiftConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得班组排班分页")
    //@PreAuthorize("@ss.hasPermission('cal:teamshift:query')")
    public CommonResult<PageResult<TeamshiftRespVO>> getTeamshiftPage(@Valid TeamshiftPageReqVO pageVO) {
        PageResult<TeamshiftDO> pageResult = teamshiftService.getTeamshiftPage(pageVO);
        return success(TeamshiftConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出班组排班 Excel")
    //@PreAuthorize("@ss.hasPermission('cal:teamshift:export')")
    @OperateLog(type = EXPORT)
    public void exportTeamshiftExcel(@Valid TeamshiftExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TeamshiftDO> list = teamshiftService.getTeamshiftList(exportReqVO);
        // 导出 Excel
        List<TeamshiftExcelVO> datas = TeamshiftConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "班组排班.xls", "数据", TeamshiftExcelVO.class, datas);
    }

}
