package com.dofast.module.mes.controller.admin.subclassexception;

import com.dofast.module.mes.controller.admin.exception.vo.ExceptionExportReqVO;
import com.dofast.module.mes.dal.dataobject.exception.ExceptionDO;
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

import com.dofast.module.mes.controller.admin.subclassexception.vo.*;
import com.dofast.module.mes.dal.dataobject.subclassexception.SubclassExceptionDO;
import com.dofast.module.mes.convert.subclassexception.SubclassExceptionConvert;
import com.dofast.module.mes.service.subclassexception.SubclassExceptionService;

@Tag(name = "管理后台 - 子类异常项配置")
@RestController
@RequestMapping("/mes/subclass-exception")
@Validated
public class SubclassExceptionController {

    @Resource
    private SubclassExceptionService subclassExceptionService;

    @PostMapping("/create")
    @Operation(summary = "创建子类异常项配置")
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:create')")
    public CommonResult<Long> createSubclassException(@Valid @RequestBody SubclassExceptionCreateReqVO createReqVO) {
        return success(subclassExceptionService.createSubclassException(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新子类异常项配置")
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:update')")
    public CommonResult<Boolean> updateSubclassException(@Valid @RequestBody SubclassExceptionUpdateReqVO updateReqVO) {
        subclassExceptionService.updateSubclassException(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除子类异常项配置")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:delete')")
    public CommonResult<Boolean> deleteSubclassException(@RequestParam("id") Long id) {
        subclassExceptionService.deleteSubclassException(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得子类异常项配置")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:query')")
    public CommonResult<SubclassExceptionRespVO> getSubclassException(@RequestParam("id") Long id) {
        SubclassExceptionDO subclassException = subclassExceptionService.getSubclassException(id);
        return success(SubclassExceptionConvert.INSTANCE.convert(subclassException));
    }

    @GetMapping("/list")
    @Operation(summary = "获得子类异常项配置列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:query')")
    public CommonResult<List<SubclassExceptionRespVO>> getSubclassExceptionList(@RequestParam("ids") Collection<Long> ids) {
        List<SubclassExceptionDO> list = subclassExceptionService.getSubclassExceptionList(ids);
        return success(SubclassExceptionConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得子类异常项配置分页")
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:query')")
    public CommonResult<PageResult<SubclassExceptionRespVO>> getSubclassExceptionPage(@Valid SubclassExceptionPageReqVO pageVO) {
        PageResult<SubclassExceptionDO> pageResult = subclassExceptionService.getSubclassExceptionPage(pageVO);
        return success(SubclassExceptionConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/all-list")
    @Operation(summary = "获得子类异常项配置分页")
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:query')")
    public CommonResult<List<SubclassExceptionDO>> getSubclassExceptionAllList(@Valid SubclassExceptionExportReqVO pageVO) {
        List<SubclassExceptionDO> result = subclassExceptionService.getSubclassExceptionList(pageVO);
        return success(result);
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出子类异常项配置 Excel")
    @PreAuthorize("@ss.hasPermission('mes:subclass-exception:export')")
    @OperateLog(type = EXPORT)
    public void exportSubclassExceptionExcel(@Valid SubclassExceptionExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<SubclassExceptionDO> list = subclassExceptionService.getSubclassExceptionList(exportReqVO);
        // 导出 Excel
        List<SubclassExceptionExcelVO> datas = SubclassExceptionConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "子类异常项配置.xls", "数据", SubclassExceptionExcelVO.class, datas);
    }

}
