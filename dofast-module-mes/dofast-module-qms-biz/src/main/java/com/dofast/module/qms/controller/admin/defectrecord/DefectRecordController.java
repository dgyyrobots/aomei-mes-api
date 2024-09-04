package com.dofast.module.qms.controller.admin.defectrecord;

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

import com.dofast.module.qms.controller.admin.defectrecord.vo.*;
import com.dofast.module.qms.dal.dataobject.defectrecord.DefectRecordDO;
import com.dofast.module.qms.convert.defectrecord.DefectRecordConvert;
import com.dofast.module.qms.service.defectrecord.DefectRecordService;

@Tag(name = "质量管理 - 检验单缺陷记录")
@RestController
@RequestMapping("/mes/qms/defect-record")
@Validated
public class DefectRecordController {

    @Resource
    private DefectRecordService defectRecordService;

    @PostMapping("/create")
    @Operation(summary = "创建检验单缺陷记录")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:create')")
    public CommonResult<Long> createDefectRecord(@Valid @RequestBody DefectRecordCreateReqVO createReqVO) {
        return success(defectRecordService.createDefectRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新检验单缺陷记录")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:update')")
    public CommonResult<Boolean> updateDefectRecord(@Valid @RequestBody DefectRecordUpdateReqVO updateReqVO) {
        defectRecordService.updateDefectRecord(updateReqVO);
        return success(true);
    }


    @PutMapping("/update-all")
    @Operation(summary = "批量更新检验单缺陷记录")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:update')")
    public CommonResult<Boolean> updateAllDefectRecord(@Valid @RequestBody List<DefectRecordUpdateReqVO> updateReqVO) {
        defectRecordService.updateAllDefectRecord(updateReqVO);
        return success(true);
    }


    @PutMapping("/update-or-create")
    @Operation(summary = "更新或创建检验单缺陷记录")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:update')")
    public CommonResult<Boolean> updateOrCreateDefectRecord(@Valid @RequestBody List<DefectRecordUpdateReqVO> updateReqVOS){
        defectRecordService.updateOrCreateDefectRecord(updateReqVOS);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除检验单缺陷记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('qms:defect-record:delete')")
    public CommonResult<Boolean> deleteDefectRecord(@RequestParam("id") Long id) {
        defectRecordService.deleteDefectRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得检验单缺陷记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:query')")
    public CommonResult<DefectRecordRespVO> getDefectRecord(@RequestParam("id") Long id) {
        DefectRecordDO defectRecord = defectRecordService.getDefectRecord(id);
        return success(DefectRecordConvert.INSTANCE.convert(defectRecord));
    }

    @GetMapping("/list")
    @Operation(summary = "获得检验单缺陷记录列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:query')")
    public CommonResult<List<DefectRecordRespVO>> getDefectRecordList(@RequestParam("ids") Collection<Long> ids) {
        List<DefectRecordDO> list = defectRecordService.getDefectRecordList(ids);
        return success(DefectRecordConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得检验单缺陷记录分页")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:query')")
    public CommonResult<PageResult<DefectRecordRespVO>> getDefectRecordPage(@Valid DefectRecordPageReqVO pageVO) {
        PageResult<DefectRecordDO> pageResult = defectRecordService.getDefectRecordPage(pageVO);
        return success(DefectRecordConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出检验单缺陷记录 Excel")
    @PreAuthorize("@ss.hasPermission('qms:defect-record:export')")
    @OperateLog(type = EXPORT)
    public void exportDefectRecordExcel(@Valid DefectRecordExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DefectRecordDO> list = defectRecordService.getDefectRecordList(exportReqVO);
        // 导出 Excel
        List<DefectRecordExcelVO> datas = DefectRecordConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "检验单缺陷记录.xls", "数据", DefectRecordExcelVO.class, datas);
    }

}
