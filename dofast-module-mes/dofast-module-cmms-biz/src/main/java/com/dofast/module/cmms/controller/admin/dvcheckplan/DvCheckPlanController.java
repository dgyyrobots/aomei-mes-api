package com.dofast.module.cmms.controller.admin.dvcheckplan;

import com.dofast.module.cmms.enums.ErrorCodeConstants;
import com.dofast.module.cmms.service.dvcheckmachinery.DvCheckMachineryService;
import com.dofast.module.cmms.service.dvsubject.DvSubjectService;
import com.dofast.module.mes.constant.Constant;
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

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.cmms.controller.admin.dvcheckplan.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvcheckplan.DvCheckPlanDO;
import com.dofast.module.cmms.convert.dvcheckplan.DvCheckPlanConvert;
import com.dofast.module.cmms.service.dvcheckplan.DvCheckPlanService;

@Tag(name = "设备管理 - 设备点检保养计划头")
@RestController
@RequestMapping("/mes/cmms/dv-check-plan")
@Validated
public class DvCheckPlanController {

    @Resource
    private DvCheckPlanService dvCheckPlanService;




    @PostMapping("/create")
    @Operation(summary = "创建设备点检保养计划头")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan:create')")
    public CommonResult<Long> createDvCheckPlan(@Valid @RequestBody DvCheckPlanCreateReqVO createReqVO) {
        /*if(Constant.NOT_UNIQUE.equals(dvCheckPlanService.checkPlanCodeUnique(createReqVO))){
            return error(ErrorCodeConstants.DV_CHECK_PLAN_NOT_EXISTS);
        }*/
        return success(dvCheckPlanService.createDvCheckPlan(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备点检保养计划头")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan:update')")
    public CommonResult<Boolean> updateDvCheckPlan(@Valid @RequestBody DvCheckPlanUpdateReqVO updateReqVO) {
        if(Constant.NOT_UNIQUE.equals(dvCheckPlanService.checkPlanCodeUnique(updateReqVO))){
            return error(ErrorCodeConstants.DV_CHECK_PLAN_NOT_EXISTS);
        }
        dvCheckPlanService.updateDvCheckPlan(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备点检保养计划头")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan:delete')")
    public CommonResult<Boolean> deleteDvCheckPlan(@RequestParam("id") Long id) {
        dvCheckPlanService.deleteDvCheckPlan(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备点检保养计划头")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan:query')")
    public CommonResult<DvCheckPlanRespVO> getDvCheckPlan(@RequestParam("id") Long id) {
        DvCheckPlanDO dvCheckPlan = dvCheckPlanService.getDvCheckPlan(id);
        return success(DvCheckPlanConvert.INSTANCE.convert(dvCheckPlan));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备点检保养计划头列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan:query')")
    public CommonResult<List<DvCheckPlanRespVO>> getDvCheckPlanList(@RequestParam("ids") Collection<Long> ids) {
        List<DvCheckPlanDO> list = dvCheckPlanService.getDvCheckPlanList(ids);
        return success(DvCheckPlanConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备点检保养计划头分页")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan:query')")
    public CommonResult<PageResult<DvCheckPlanRespVO>> getDvCheckPlanPage(@Valid DvCheckPlanPageReqVO pageVO) {
        PageResult<DvCheckPlanDO> pageResult = dvCheckPlanService.getDvCheckPlanPage(pageVO);
        return success(DvCheckPlanConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备点检保养计划头 Excel")
    @PreAuthorize("@ss.hasPermission('cmms:dv-check-plan:export')")
    @OperateLog(type = EXPORT)
    public void exportDvCheckPlanExcel(@Valid DvCheckPlanExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DvCheckPlanDO> list = dvCheckPlanService.getDvCheckPlanList(exportReqVO);
        // 导出 Excel
        List<DvCheckPlanExcelVO> datas = DvCheckPlanConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备点检保养计划头.xls", "数据", DvCheckPlanExcelVO.class, datas);
    }

}
