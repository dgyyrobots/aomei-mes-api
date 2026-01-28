package com.dofast.module.mes.controller.admin.mditemcategory;

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

import com.dofast.module.mes.controller.admin.mditemcategory.vo.*;
import com.dofast.module.mes.dal.dataobject.mditemcategory.MdItemCategoryDO;
import com.dofast.module.mes.convert.mditemcategory.MdItemCategoryConvert;
import com.dofast.module.mes.service.mditemcategory.MdItemCategoryService;

@Tag(name = "管理后台 - 物料类别")
@RestController
@RequestMapping("/mes/md-item-category")
@Validated
public class MdItemCategoryController {

    @Resource
    private MdItemCategoryService mdItemCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建物料类别")
    @PreAuthorize("@ss.hasPermission('mes:md-item-category:create')")
    public CommonResult<Integer> createMdItemCategory(@Valid @RequestBody MdItemCategoryCreateReqVO createReqVO) {
        return success(mdItemCategoryService.createMdItemCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物料类别")
    @PreAuthorize("@ss.hasPermission('mes:md-item-category:update')")
    public CommonResult<Boolean> updateMdItemCategory(@Valid @RequestBody MdItemCategoryUpdateReqVO updateReqVO) {
        mdItemCategoryService.updateMdItemCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除物料类别")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('mes:md-item-category:delete')")
    public CommonResult<Boolean> deleteMdItemCategory(@RequestParam("id") Integer id) {
        mdItemCategoryService.deleteMdItemCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得物料类别")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('mes:md-item-category:query')")
    public CommonResult<MdItemCategoryRespVO> getMdItemCategory(@RequestParam("id") Integer id) {
        MdItemCategoryDO mdItemCategory = mdItemCategoryService.getMdItemCategory(id);
        return success(MdItemCategoryConvert.INSTANCE.convert(mdItemCategory));
    }

    @GetMapping("/list")
    @Operation(summary = "获得物料类别列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('mes:md-item-category:query')")
    public CommonResult<List<MdItemCategoryRespVO>> getMdItemCategoryList(@RequestParam("ids") Collection<Integer> ids) {
        List<MdItemCategoryDO> list = mdItemCategoryService.getMdItemCategoryList(ids);
        return success(MdItemCategoryConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得物料类别分页")
    @PreAuthorize("@ss.hasPermission('mes:md-item-category:query')")
    public CommonResult<PageResult<MdItemCategoryRespVO>> getMdItemCategoryPage(@Valid MdItemCategoryPageReqVO pageVO) {
        PageResult<MdItemCategoryDO> pageResult = mdItemCategoryService.getMdItemCategoryPage(pageVO);
        return success(MdItemCategoryConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出物料类别 Excel")
    @PreAuthorize("@ss.hasPermission('mes:md-item-category:export')")
    @OperateLog(type = EXPORT)
    public void exportMdItemCategoryExcel(@Valid MdItemCategoryExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<MdItemCategoryDO> list = mdItemCategoryService.getMdItemCategoryList(exportReqVO);
        // 导出 Excel
        List<MdItemCategoryExcelVO> datas = MdItemCategoryConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "物料类别.xls", "数据", MdItemCategoryExcelVO.class, datas);
    }




}
