package com.dofast.module.mes.controller.admin.exception;

import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
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
import java.time.LocalDateTime;
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.mes.controller.admin.exception.vo.*;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;
import com.dofast.module.mes.convert.exception.ExceptionConvert;
import com.dofast.module.mes.service.exception.ExceptionService;

@Tag(name = "管理后台 - 异常登记")
@RestController
@RequestMapping("/mes/exception")
@Validated
public class ExceptionController {

    @Resource
    private ExceptionService exceptionService;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private AutoCodeApi autoCodeApi;

    @PostMapping("/create")
    @Operation(summary = "创建异常登记")
    @PreAuthorize("@ss.hasPermission('mes:exception:create')")
    public CommonResult<Long> createException(@Valid @RequestBody ExceptionCreateReqVO createReqVO) {
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        createReqVO.setRegisterUser(adminUserRespDTO.getUsername());
        createReqVO.setRegisterTime(LocalDateTime.now());
        String code = autoCodeApi.genSerialCode("EXCEPTION_CODE",null);
        createReqVO.setExceptionCode(code);
        createReqVO.setTitle(code);
        return success(exceptionService.createException(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新异常登记")
    @PreAuthorize("@ss.hasPermission('mes:exception:update')")
    public CommonResult<Boolean> updateException(@Valid @RequestBody ExceptionUpdateReqVO updateReqVO) {
        exceptionService.updateException(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除异常登记")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:exception:delete')")
    public CommonResult<Boolean> deleteException(@RequestParam("id") Long id) {
        exceptionService.deleteException(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得异常登记")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:exception:query')")
    public CommonResult<ExceptionRespVO> getException(@RequestParam("id") Long id) {
        ExceptionDO exception = exceptionService.getException(id);
        return success(ExceptionConvert.INSTANCE.convert(exception));
    }

    @GetMapping("/list")
    @Operation(summary = "获得异常登记列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('mes:exception:query')")
    public CommonResult<List<ExceptionRespVO>> getExceptionList(@RequestParam("ids") Collection<Long> ids) {
        List<ExceptionDO> list = exceptionService.getExceptionList(ids);
        return success(ExceptionConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得异常登记分页")
    @PreAuthorize("@ss.hasPermission('mes:exception:query')")
    public CommonResult<PageResult<ExceptionRespVO>> getExceptionPage(@Valid ExceptionPageReqVO pageVO) {
        PageResult<ExceptionDO> pageResult = exceptionService.getExceptionPage(pageVO);
        return success(ExceptionConvert.INSTANCE.convertPage(pageResult));
    }



    @GetMapping("/export-excel")
    @Operation(summary = "导出异常登记 Excel")
    @PreAuthorize("@ss.hasPermission('mes:exception:export')")
    @OperateLog(type = EXPORT)
    public void exportExceptionExcel(@Valid ExceptionExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<ExceptionDO> list = exceptionService.getExceptionList(exportReqVO);
        // 导出 Excel
        List<ExceptionExcelVO> datas = ExceptionConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "异常登记.xls", "数据", ExceptionExcelVO.class, datas);
    }

}
