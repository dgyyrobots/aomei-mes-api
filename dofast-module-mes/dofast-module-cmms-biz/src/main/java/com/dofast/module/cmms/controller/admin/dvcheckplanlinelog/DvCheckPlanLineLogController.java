package com.dofast.module.cmms.controller.admin.dvcheckplanlinelog;

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

import com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanlinelog.DvCheckPlanLineLogDO;
import com.dofast.module.cmms.convert.dvcheckplanlinelog.DvCheckPlanLineLogConvert;
import com.dofast.module.cmms.service.dvcheckplanlinelog.DvCheckPlanLineLogService;

@Tag(name = "管理后台 - 点检计划记录单身")
@RestController
@RequestMapping("/cmms/dv-check-plan-line-log")
@Validated
public class DvCheckPlanLineLogController {

    @Resource
    private DvCheckPlanLineLogService dvCheckPlanLineLogService;

    @PostMapping("/create")
    @Operation(summary = "创建点检计划记录单身")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-line-log:create')")
    public CommonResult<Long> createDvCheckPlanLineLog(@Valid @RequestBody DvCheckPlanLineLogCreateReqVO createReqVO) {
        return success(dvCheckPlanLineLogService.createDvCheckPlanLineLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新点检计划记录单身")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-line-log:update')")
    public CommonResult<Boolean> updateDvCheckPlanLineLog(@Valid @RequestBody DvCheckPlanLineLogUpdateReqVO updateReqVO) {
        dvCheckPlanLineLogService.updateDvCheckPlanLineLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除点检计划记录单身")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-line-log:delete')")
    public CommonResult<Boolean> deleteDvCheckPlanLineLog(@RequestParam("id") Long id) {
        dvCheckPlanLineLogService.deleteDvCheckPlanLineLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得点检计划记录单身")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-line-log:query')")
    public CommonResult<DvCheckPlanLineLogRespVO> getDvCheckPlanLineLog(@RequestParam("id") Long id) {
        DvCheckPlanLineLogDO dvCheckPlanLineLog = dvCheckPlanLineLogService.getDvCheckPlanLineLog(id);
        return success(DvCheckPlanLineLogConvert.INSTANCE.convert(dvCheckPlanLineLog));
    }

    @GetMapping("/list")
    @Operation(summary = "获得点检计划记录单身列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-line-log:query')")
    public CommonResult<List<DvCheckPlanLineLogRespVO>> getDvCheckPlanLineLogList(@RequestParam("ids") Collection<Long> ids) {
        List<DvCheckPlanLineLogDO> list = dvCheckPlanLineLogService.getDvCheckPlanLineLogList(ids);
        return success(DvCheckPlanLineLogConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得点检计划记录单身分页")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-line-log:query')")
    public CommonResult<PageResult<DvCheckPlanLineLogRespVO>> getDvCheckPlanLineLogPage(@Valid DvCheckPlanLineLogPageReqVO pageVO) {
        PageResult<DvCheckPlanLineLogDO> pageResult = dvCheckPlanLineLogService.getDvCheckPlanLineLogPage(pageVO);
        return success(DvCheckPlanLineLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出点检计划记录单身 Excel")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-line-log:export')")
    @OperateLog(type = EXPORT)
    public void exportDvCheckPlanLineLogExcel(@Valid DvCheckPlanLineLogExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DvCheckPlanLineLogDO> list = dvCheckPlanLineLogService.getDvCheckPlanLineLogList(exportReqVO);
        // 导出 Excel
        List<DvCheckPlanLineLogExcelVO> datas = DvCheckPlanLineLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "点检计划记录单身.xls", "数据", DvCheckPlanLineLogExcelVO.class, datas);
    }

}
