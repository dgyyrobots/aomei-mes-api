package com.dofast.module.mes.controller.admin.exceptionlevelconfig;

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

import com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo.*;
import com.dofast.module.mes.dal.dataobject.exceptionlevelconfig.ExceptionLevelConfigDO;
import com.dofast.module.mes.convert.exceptionlevelconfig.ExceptionLevelConfigConvert;
import com.dofast.module.mes.service.exceptionlevelconfig.ExceptionLevelConfigService;

@Tag(name = "管理后台 - 异常等级配置")
@RestController
@RequestMapping("/mes/exception-level-config")
@Validated
public class ExceptionLevelConfigController {

    @Resource
    private ExceptionLevelConfigService exceptionLevelConfigService;

    @PostMapping("/create")
    @Operation(summary = "创建异常等级配置")
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:create')")
    public CommonResult<Long> createExceptionLevelConfig(@Valid @RequestBody ExceptionLevelConfigCreateReqVO createReqVO) {
        return success(exceptionLevelConfigService.createExceptionLevelConfig(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新异常等级配置")
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:update')")
    public CommonResult<Boolean> updateExceptionLevelConfig(@Valid @RequestBody ExceptionLevelConfigUpdateReqVO updateReqVO) {
        exceptionLevelConfigService.updateExceptionLevelConfig(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除异常等级配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:delete')")
    public CommonResult<Boolean> deleteExceptionLevelConfig(@RequestParam("id") Long id) {
        exceptionLevelConfigService.deleteExceptionLevelConfig(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得异常等级配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:query')")
    public CommonResult<ExceptionLevelConfigRespVO> getExceptionLevelConfig(@RequestParam("id") Long id) {
        ExceptionLevelConfigDO exceptionLevelConfig = exceptionLevelConfigService.getExceptionLevelConfig(id);
        return success(ExceptionLevelConfigConvert.INSTANCE.convert(exceptionLevelConfig));
    }

    @GetMapping("/list")
    @Operation(summary = "获得异常等级配置列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:query')")
    public CommonResult<List<ExceptionLevelConfigRespVO>> getExceptionLevelConfigList(@RequestParam("ids") Collection<Long> ids) {
        List<ExceptionLevelConfigDO> list = exceptionLevelConfigService.getExceptionLevelConfigList(ids);
        return success(ExceptionLevelConfigConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得异常等级配置分页")
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:query')")
    public CommonResult<PageResult<ExceptionLevelConfigRespVO>> getExceptionLevelConfigPage(@Valid ExceptionLevelConfigPageReqVO pageVO) {
        PageResult<ExceptionLevelConfigDO> pageResult = exceptionLevelConfigService.getExceptionLevelConfigPage(pageVO);
        return success(ExceptionLevelConfigConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/all-list")
    @Operation(summary = "获得异常等级配置分页")
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:query')")
    public CommonResult<List<ExceptionLevelConfigDO>> getExceptionLevelConfigAllList(@Valid ExceptionLevelConfigExportReqVO pageVO) {
        List<ExceptionLevelConfigDO> result = exceptionLevelConfigService.getExceptionLevelConfigList(pageVO);
        return success(result);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出异常等级配置 Excel")
    @PreAuthorize("@ss.hasPermission('mes:exception-level-config:export')")
    @OperateLog(type = EXPORT)
    public void exportExceptionLevelConfigExcel(@Valid ExceptionLevelConfigExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ExceptionLevelConfigDO> list = exceptionLevelConfigService.getExceptionLevelConfigList(exportReqVO);
        // 导出 Excel
        List<ExceptionLevelConfigExcelVO> datas = ExceptionLevelConfigConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "异常等级配置.xls", "数据", ExceptionLevelConfigExcelVO.class, datas);
    }

}
