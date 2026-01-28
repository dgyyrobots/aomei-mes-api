package com.dofast.module.qms.controller.admin.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.qms.enums.ErrorCodeConstants;
import com.dofast.module.qms.service.index.IndexService;
import com.dofast.module.qms.service.templateindex.TemplateIndexService;
import com.dofast.module.qms.service.templateproduct.TemplateProductService;
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

import com.dofast.module.qms.controller.admin.template.vo.*;
import com.dofast.module.qms.dal.dataobject.template.TemplateDO;
import com.dofast.module.qms.convert.template.TemplateConvert;
import com.dofast.module.qms.service.template.TemplateService;

@Tag(name = "质量管理 - 检测模板")
@RestController
@RequestMapping("/mes/qms/template")
@Validated
public class TemplateController {

    @Resource
    private TemplateService templateService;
    @Resource
    private TemplateIndexService templateIndexService;
    @Resource
    private TemplateProductService templateProductService;

    @PostMapping("/create")
    @Operation(summary = "创建检测模板")
    @PreAuthorize("@ss.hasPermission('qms:template:create')")
    public CommonResult<Long> createTemplate(@Valid @RequestBody TemplateCreateReqVO createReqVO) {
        if(Constant.NOT_UNIQUE.equals(templateService.checkTemplateCodeUnique(createReqVO))){
            return error(ErrorCodeConstants.TEMPLATE_CODE_EXISTS);
        }
        if(ArrayUtil.isEmpty(createReqVO.getQcTypesParam())){
            return error(ErrorCodeConstants.TEMPLATE_TYPE_EXISTS);
        }

        if(ArrayUtil.isNotEmpty(createReqVO.getQcTypesParam())){
            createReqVO.setQcTypes(null); //先置空
            String[] types = createReqVO.getQcTypesParam();
            //根据输入参数重新生成
            for (String type:types
            ) {
                if(StrUtils.isNotNull(createReqVO.getQcTypes())){
                    createReqVO.setQcTypes(createReqVO.getQcTypes()+','+type);
                }else{
                    createReqVO.setQcTypes(type);
                }
            }
        }
        return success(templateService.createTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检测模板")
    @PreAuthorize("@ss.hasPermission('qms:template:update')")
    public CommonResult<Boolean> updateTemplate(@Valid @RequestBody TemplateUpdateReqVO updateReqVO) {
        if(Constant.NOT_UNIQUE.equals(templateService.checkTemplateCodeUnique(updateReqVO))){
            return error(ErrorCodeConstants.TEMPLATE_CODE_EXISTS);
        }
        if(ArrayUtil.isNotEmpty(updateReqVO.getQcTypesParam())){
            updateReqVO.setQcTypes(null); //先置空
            String[] types = updateReqVO.getQcTypesParam();
            //根据输入参数重新生成
            for (String type:types
            ) {
                if(StrUtils.isNotNull(updateReqVO.getQcTypes())){
                    updateReqVO.setQcTypes(updateReqVO.getQcTypes()+','+type);
                }else{
                    updateReqVO.setQcTypes(type);
                }
            }
        }
        templateService.updateTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检测模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('qms:template:delete')")
    public CommonResult<Boolean> deleteTemplate(@RequestParam("id") Long id) {
        //删除当前模板下所有检测项数据
        templateIndexService.deleteByTemplateId(id);
        //删除当前模板下所有检测产品
        templateProductService.deleteByTemplateId(id);
        templateService.deleteTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检测模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('qms:template:query')")
    public CommonResult<TemplateRespVO> getTemplate(@RequestParam("id") Long id) {
        TemplateDO template = templateService.getTemplate(id);
        TemplateRespVO templateRespVO = BeanUtil.toBean(template, TemplateRespVO.class);
        if(StrUtils.isNotNull(template)){
            templateRespVO.setQcTypesParam(template.getQcTypes().split(","));
        }
        return success(templateRespVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得检测模板列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('qms:template:query')")
    public CommonResult<List<TemplateRespVO>> getTemplateList(@RequestParam("ids") Collection<Long> ids) {
        List<TemplateDO> list = templateService.getTemplateList(ids);
        if(CollUtil.isNotEmpty(list)){
            for (int i=0;i<list.size();i++)
            {
                TemplateDO template = list.get(i);
                list.set(i,template);
            }
        }
        return success(TemplateConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检测模板分页")
    @PreAuthorize("@ss.hasPermission('qms:template:query')")
    public CommonResult<PageResult<TemplateRespVO>> getTemplatePage(@Valid TemplatePageReqVO pageVO) {
        PageResult<TemplateDO> pageResult = templateService.getTemplatePage(pageVO);
        if(CollUtil.isNotEmpty(pageResult.getList())){
            for (int i=0;i<pageResult.getList().size();i++)
            {
                TemplateDO template = pageResult.getList().get(i);
                pageResult.getList().set(i,template);
            }
        }
        return success(TemplateConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检测模板 Excel")
    @PreAuthorize("@ss.hasPermission('qms:template:export')")
    @OperateLog(type = EXPORT)
    public void exportTemplateExcel(@Valid TemplateExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<TemplateDO> list = templateService.getTemplateList(exportReqVO);
        // 导出 Excel
        List<TemplateExcelVO> datas = TemplateConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "检测模板.xls", "数据", TemplateExcelVO.class, datas);
    }

}
