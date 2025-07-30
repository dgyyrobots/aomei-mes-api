package com.dofast.module.mes.controller.admin.registrationline;

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

import com.dofast.module.mes.controller.admin.registrationline.vo.*;
import com.dofast.module.mes.dal.dataobject.registrationline.RegistrationLineDO;
import com.dofast.module.mes.convert.registrationline.RegistrationLineConvert;
import com.dofast.module.mes.service.registrationline.RegistrationLineService;

@Tag(name = "管理后台 - 计时登记记录")
@RestController
@RequestMapping("/mes/registration-line")
@Validated
public class RegistrationLineController {

    @Resource
    private RegistrationLineService registrationLineService;

    @PostMapping("/create")
    @Operation(summary = "创建计时登记记录")
    @PreAuthorize("@ss.hasPermission('mes:registration-line:create')")
    public CommonResult<Long> createRegistrationLine(@Valid @RequestBody RegistrationLineCreateReqVO createReqVO) {
        return success(registrationLineService.createRegistrationLine(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新计时登记记录")
    @PreAuthorize("@ss.hasPermission('mes:registration-line:update')")
    public CommonResult<Boolean> updateRegistrationLine(@Valid @RequestBody RegistrationLineUpdateReqVO updateReqVO) {
        registrationLineService.updateRegistrationLine(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除计时登记记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:registration-line:delete')")
    public CommonResult<Boolean> deleteRegistrationLine(@RequestParam("id") Long id) {
        registrationLineService.deleteRegistrationLine(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得计时登记记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:registration-line:query')")
    public CommonResult<RegistrationLineRespVO> getRegistrationLine(@RequestParam("id") Long id) {
        RegistrationLineDO registrationLine = registrationLineService.getRegistrationLine(id);
        return success(RegistrationLineConvert.INSTANCE.convert(registrationLine));
    }

    @GetMapping("/list")
    @Operation(summary = "获得计时登记记录列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('mes:registration-line:query')")
    public CommonResult<List<RegistrationLineRespVO>> getRegistrationLineList(@RequestParam("ids") Collection<Long> ids) {
        List<RegistrationLineDO> list = registrationLineService.getRegistrationLineList(ids);
        return success(RegistrationLineConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得计时登记记录分页")
    @PreAuthorize("@ss.hasPermission('mes:registration-line:query')")
    public CommonResult<PageResult<RegistrationLineRespVO>> getRegistrationLinePage(@Valid RegistrationLinePageReqVO pageVO) {
        PageResult<RegistrationLineDO> pageResult = registrationLineService.getRegistrationLinePage(pageVO);
        return success(RegistrationLineConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出计时登记记录 Excel")
    @PreAuthorize("@ss.hasPermission('mes:registration-line:export')")
    @OperateLog(type = EXPORT)
    public void exportRegistrationLineExcel(@Valid RegistrationLineExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RegistrationLineDO> list = registrationLineService.getRegistrationLineList(exportReqVO);
        // 导出 Excel
        List<RegistrationLineExcelVO> datas = RegistrationLineConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "计时登记记录.xls", "数据", RegistrationLineExcelVO.class, datas);
    }

}
