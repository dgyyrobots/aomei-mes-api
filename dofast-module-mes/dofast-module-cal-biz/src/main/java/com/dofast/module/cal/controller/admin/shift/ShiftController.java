package com.dofast.module.cal.controller.admin.shift;

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

import com.dofast.module.cal.controller.admin.shift.vo.*;
import com.dofast.module.cal.dal.dataobject.shift.ShiftDO;
import com.dofast.module.cal.convert.shift.ShiftConvert;
import com.dofast.module.cal.service.shift.ShiftService;

@Tag(name = "管理后台 - 计划班次")
@RestController
@RequestMapping("/cal/shift")
@Validated
public class ShiftController {

    @Resource
    private ShiftService shiftService;

    @PostMapping("/create")
    @Operation(summary = "创建计划班次")
    @PreAuthorize("@ss.hasPermission('cal:shift:create')")
    public CommonResult<Long> createShift(@Valid @RequestBody ShiftCreateReqVO createReqVO) {
        System.out.println(createReqVO.toString());
        return success(shiftService.createShift(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新计划班次")
    @PreAuthorize("@ss.hasPermission('cal:shift:update')")
    public CommonResult<Boolean> updateShift(@Valid @RequestBody ShiftUpdateReqVO updateReqVO) {
        System.out.println(updateReqVO.toString());
        shiftService.updateShift(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计划班次")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cal:shift:delete')")
    public CommonResult<Boolean> deleteShift(@RequestParam("id") Long id) {
        shiftService.deleteShift(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计划班次")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cal:shift:query')")
    public CommonResult<ShiftRespVO> getShift(@RequestParam("id") Long id) {
        ShiftDO shift = shiftService.getShift(id);
        return success(ShiftConvert.INSTANCE.convert(shift));
    }

    @GetMapping("/list")
    @Operation(summary = "获得计划班次列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cal:shift:query')")
    public CommonResult<List<ShiftRespVO>> getShiftList(@RequestParam("ids") Collection<Long> ids) {
        List<ShiftDO> list = shiftService.getShiftList(ids);
        return success(ShiftConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得计划班次分页")
    @PreAuthorize("@ss.hasPermission('cal:shift:query')")
    public CommonResult<PageResult<ShiftRespVO>> getShiftPage(@Valid ShiftPageReqVO pageVO) {
        PageResult<ShiftDO> pageResult = shiftService.getShiftPage(pageVO);
        return success(ShiftConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计划班次 Excel")
    @PreAuthorize("@ss.hasPermission('cal:shift:export')")
    @OperateLog(type = EXPORT)
    public void exportShiftExcel(@Valid ShiftExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ShiftDO> list = shiftService.getShiftList(exportReqVO);
        // 导出 Excel
        List<ShiftExcelVO> datas = ShiftConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "计划班次.xls", "数据", ShiftExcelVO.class, datas);
    }

}
