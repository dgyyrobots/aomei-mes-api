package com.dofast.module.cal.controller.admin.planteam;

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

import com.dofast.module.cal.controller.admin.planteam.vo.*;
import com.dofast.module.cal.dal.dataobject.planteam.PlanTeamDO;
import com.dofast.module.cal.convert.planteam.PlanTeamConvert;
import com.dofast.module.cal.service.planteam.PlanTeamService;

@Tag(name = "管理后台 - 计划班组")
@RestController
@RequestMapping("/mes/cal/planteam")
@Validated
public class PlanTeamController {

    @Resource
    private PlanTeamService planTeamService;

    @PostMapping("/create")
    @Operation(summary = "创建计划班组")
    @PreAuthorize("@ss.hasPermission('cal:plan:create')")
    public CommonResult<Long> createPlanTeam(@Valid @RequestBody PlanTeamCreateReqVO createReqVO) {
        return success(planTeamService.createPlanTeam(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新计划班组")
    @PreAuthorize("@ss.hasPermission('cal:plan:update')")
    public CommonResult<Boolean> updatePlanTeam(@Valid @RequestBody PlanTeamUpdateReqVO updateReqVO) {
        planTeamService.updatePlanTeam(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计划班组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cal:plan:delete')")
    public CommonResult<Boolean> deletePlanTeam(@RequestParam("id") Long id) {
        planTeamService.deletePlanTeam(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计划班组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cal:plan:query')")
    public CommonResult<PlanTeamRespVO> getPlanTeam(@RequestParam("id") Long id) {
        PlanTeamDO planTeam = planTeamService.getPlanTeam(id);
        return success(PlanTeamConvert.INSTANCE.convert(planTeam));
    }

    @GetMapping("/list")
    @Operation(summary = "获得计划班组列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cal:plan:query')")
    public CommonResult<List<PlanTeamRespVO>> getPlanTeamList(@RequestParam("ids") Collection<Long> ids) {
        List<PlanTeamDO> list = planTeamService.getPlanTeamList(ids);
        return success(PlanTeamConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得计划班组分页")
    @PreAuthorize("@ss.hasPermission('cal:plan:query')")
    public CommonResult<PageResult<PlanTeamRespVO>> getPlanTeamPage(@Valid PlanTeamPageReqVO pageVO) {
        PageResult<PlanTeamDO> pageResult = planTeamService.getPlanTeamPage(pageVO);
        return success(PlanTeamConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计划班组 Excel")
    @PreAuthorize("@ss.hasPermission('cal:plan:export')")
    @OperateLog(type = EXPORT)
    public void exportPlanTeamExcel(@Valid PlanTeamExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<PlanTeamDO> list = planTeamService.getPlanTeamList(exportReqVO);
        // 导出 Excel
        List<PlanTeamExcelVO> datas = PlanTeamConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "计划班组.xls", "数据", PlanTeamExcelVO.class, datas);
    }

}
