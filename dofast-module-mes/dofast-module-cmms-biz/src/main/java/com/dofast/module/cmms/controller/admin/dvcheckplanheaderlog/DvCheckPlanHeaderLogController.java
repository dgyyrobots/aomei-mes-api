package com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog;

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

import com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog.DvCheckPlanHeaderLogDO;
import com.dofast.module.cmms.convert.dvcheckplanheaderlog.DvCheckPlanHeaderLogConvert;
import com.dofast.module.cmms.service.dvcheckplanheaderlog.DvCheckPlanHeaderLogService;

@Tag(name = "管理后台 - 点检计划记录单头")
@RestController
@RequestMapping("/cmms/dv-check-plan-header-log")
@Validated
public class DvCheckPlanHeaderLogController {

    @Resource
    private DvCheckPlanHeaderLogService dvCheckPlanHeaderLogService;

    @PostMapping("/create")
    @Operation(summary = "创建点检计划记录单头")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-header-log:create')")
    public CommonResult<Long> createDvCheckPlanHeaderLog(@Valid @RequestBody DvCheckPlanHeaderLogCreateReqVO createReqVO) {
        return success(dvCheckPlanHeaderLogService.createDvCheckPlanHeaderLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新点检计划记录单头")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-header-log:update')")
    public CommonResult<Boolean> updateDvCheckPlanHeaderLog(@Valid @RequestBody DvCheckPlanHeaderLogUpdateReqVO updateReqVO) {
        dvCheckPlanHeaderLogService.updateDvCheckPlanHeaderLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除点检计划记录单头")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-header-log:delete')")
    public CommonResult<Boolean> deleteDvCheckPlanHeaderLog(@RequestParam("id") Long id) {
        dvCheckPlanHeaderLogService.deleteDvCheckPlanHeaderLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得点检计划记录单头")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-header-log:query')")
    public CommonResult<DvCheckPlanHeaderLogRespVO> getDvCheckPlanHeaderLog(@RequestParam("id") Long id) {
        DvCheckPlanHeaderLogDO dvCheckPlanHeaderLog = dvCheckPlanHeaderLogService.getDvCheckPlanHeaderLog(id);
        return success(DvCheckPlanHeaderLogConvert.INSTANCE.convert(dvCheckPlanHeaderLog));
    }

    @GetMapping("/list")
    @Operation(summary = "获得点检计划记录单头列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-header-log:query')")
    public CommonResult<List<DvCheckPlanHeaderLogRespVO>> getDvCheckPlanHeaderLogList(@RequestParam("ids") Collection<Long> ids) {
        List<DvCheckPlanHeaderLogDO> list = dvCheckPlanHeaderLogService.getDvCheckPlanHeaderLogList(ids);
        return success(DvCheckPlanHeaderLogConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得点检计划记录单头分页")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-header-log:query')")
    public CommonResult<PageResult<DvCheckPlanHeaderLogRespVO>> getDvCheckPlanHeaderLogPage(@Valid DvCheckPlanHeaderLogPageReqVO pageVO) {
        PageResult<DvCheckPlanHeaderLogDO> pageResult = dvCheckPlanHeaderLogService.getDvCheckPlanHeaderLogPage(pageVO);
        return success(DvCheckPlanHeaderLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出点检计划记录单头 Excel")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan-header-log:export')")
    @OperateLog(type = EXPORT)
    public void exportDvCheckPlanHeaderLogExcel(@Valid DvCheckPlanHeaderLogExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DvCheckPlanHeaderLogDO> list = dvCheckPlanHeaderLogService.getDvCheckPlanHeaderLogList(exportReqVO);
        // 导出 Excel
        List<DvCheckPlanHeaderLogExcelVO> datas = DvCheckPlanHeaderLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "点检计划记录单头.xls", "数据", DvCheckPlanHeaderLogExcelVO.class, datas);
    }

}
