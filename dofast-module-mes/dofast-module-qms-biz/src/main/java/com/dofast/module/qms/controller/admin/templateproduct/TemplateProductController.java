package com.dofast.module.qms.controller.admin.templateproduct;

import com.dofast.module.mes.constant.Constant;
import com.dofast.module.qms.dal.dataobject.template.TemplateDO;
import com.dofast.module.qms.enums.ErrorCodeConstants;
import com.dofast.module.qms.service.template.TemplateService;
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

import com.dofast.module.qms.controller.admin.templateproduct.vo.*;
import com.dofast.module.qms.dal.dataobject.templateproduct.TemplateProductDO;
import com.dofast.module.qms.convert.templateproduct.TemplateProductConvert;
import com.dofast.module.qms.service.templateproduct.TemplateProductService;

@Tag(name = "质量管理 - 检测模板-产品")
@RestController
@RequestMapping("/mes/qms/template-product")
@Validated
public class TemplateProductController {

    @Resource
    private TemplateService templateService;

    @Resource
    private TemplateProductService templateProductService;

    @PostMapping("/create")
    @Operation(summary = "创建检测模板-产品")
    @PreAuthorize("@ss.hasPermission('qms:template-product:create')")
    public CommonResult<Long> createTemplateProduct(@Valid @RequestBody TemplateProductCreateReqVO createReqVO) {
        TemplateDO templateDO = templateService.getTemplate(createReqVO.getTemplateId());
        createReqVO.setProcessCode(templateDO.getProcessCode());
        if(Constant.NOT_UNIQUE.equals(templateProductService.checkProductUnique(createReqVO))){
            return error(ErrorCodeConstants.PRODUCT_HAS_TEMPLATE);
        }

        return success(templateProductService.createTemplateProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检测模板-产品")
    @PreAuthorize("@ss.hasPermission('qms:template-product:update')")
    public CommonResult<Boolean> updateTemplateProduct(@Valid @RequestBody TemplateProductUpdateReqVO updateReqVO) {
        if(Constant.NOT_UNIQUE.equals(templateProductService.checkProductUnique(updateReqVO))){
            return error(ErrorCodeConstants.PRODUCT_HAS_TEMPLATE);
        }
        templateProductService.updateTemplateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检测模板-产品")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('qms:template-product:delete')")
    public CommonResult<Boolean> deleteTemplateProduct(@RequestParam("id") Long id) {
        templateProductService.deleteTemplateProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检测模板-产品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('qms:template-product:query')")
    public CommonResult<TemplateProductRespVO> getTemplateProduct(@RequestParam("id") Long id) {
        TemplateProductDO templateProduct = templateProductService.getTemplateProduct(id);
        return success(TemplateProductConvert.INSTANCE.convert(templateProduct));
    }

    @GetMapping("/list")
    @Operation(summary = "获得检测模板-产品列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('qms:template-product:query')")
    public CommonResult<List<TemplateProductRespVO>> getTemplateProductList(@RequestParam("ids") Collection<Long> ids) {
        List<TemplateProductDO> list = templateProductService.getTemplateProductList(ids);
        return success(TemplateProductConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检测模板-产品分页")
    @PreAuthorize("@ss.hasPermission('qms:template-product:query')")
    public CommonResult<PageResult<TemplateProductRespVO>> getTemplateProductPage(@Valid TemplateProductPageReqVO pageVO) {
        PageResult<TemplateProductDO> pageResult = templateProductService.getTemplateProductPage(pageVO);
        return success(TemplateProductConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检测模板-产品 Excel")
    @PreAuthorize("@ss.hasPermission('qms:template-product:export')")
    @OperateLog(type = EXPORT)
    public void exportTemplateProductExcel(@Valid TemplateProductExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TemplateProductDO> list = templateProductService.getTemplateProductList(exportReqVO);
        // 导出 Excel
        List<TemplateProductExcelVO> datas = TemplateProductConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "检测模板-产品.xls", "数据", TemplateProductExcelVO.class, datas);
    }

}
