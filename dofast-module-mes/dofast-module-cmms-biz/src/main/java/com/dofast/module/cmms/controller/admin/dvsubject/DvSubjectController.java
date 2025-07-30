package com.dofast.module.cmms.controller.admin.dvsubject;

import com.dofast.module.cmms.enums.ErrorCodeConstants;
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

import com.dofast.module.cmms.controller.admin.dvsubject.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvsubject.DvSubjectDO;
import com.dofast.module.cmms.convert.dvsubject.DvSubjectConvert;
import com.dofast.module.cmms.service.dvsubject.DvSubjectService;

@Tag(name = "设备管理 - 设备点检保养项目")
@RestController
@RequestMapping("/mes/cmms/dv-subject")
@Validated
public class DvSubjectController {

    @Resource
    private DvSubjectService dvSubjectService;

    @PostMapping("/create")
    @Operation(summary = "创建设备点检保养项目")
    @PreAuthorize("@ss.hasPermission('cmms:dv-subject:create')")
    public CommonResult<Long> createDvSubject(@Valid @RequestBody DvSubjectCreateReqVO createReqVO) {
        /*if(Constant.NOT_UNIQUE.equals(dvSubjectService.checkSubjectCodeUnique(createReqVO))){
            return error(ErrorCodeConstants.DV_SUBJECT_NOT_EXISTS);
        }*/
        return success(dvSubjectService.createDvSubject(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备点检保养项目")
    @PreAuthorize("@ss.hasPermission('cmms:dv-subject:update')")
    public CommonResult<Boolean> updateDvSubject(@Valid @RequestBody DvSubjectUpdateReqVO updateReqVO) {
        /*if(Constant.NOT_UNIQUE.equals(dvSubjectService.checkSubjectCodeUnique(updateReqVO))){
            return error(ErrorCodeConstants.DV_SUBJECT_NOT_EXISTS);
        }*/
        dvSubjectService.updateDvSubject(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备点检保养项目")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmms:dv-subject:delete')")
    public CommonResult<Boolean> deleteDvSubject(@RequestParam("id") Long id) {
        dvSubjectService.deleteDvSubject(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备点检保养项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmms:dv-subject:query')")
    public CommonResult<DvSubjectRespVO> getDvSubject(@RequestParam("id") Long id) {
        DvSubjectDO dvSubject = dvSubjectService.getDvSubject(id);
        return success(DvSubjectConvert.INSTANCE.convert(dvSubject));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备点检保养项目列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cmms:dv-subject:query')")
    public CommonResult<List<DvSubjectRespVO>> getDvSubjectList(@RequestParam("ids") Collection<Long> ids) {
        List<DvSubjectDO> list = dvSubjectService.getDvSubjectList(ids);
        return success(DvSubjectConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备点检保养项目分页")
    @PreAuthorize("@ss.hasPermission('cmms:dv-subject:query')")
    public CommonResult<PageResult<DvSubjectRespVO>> getDvSubjectPage(@Valid DvSubjectPageReqVO pageVO) {
        PageResult<DvSubjectDO> pageResult = dvSubjectService.getDvSubjectPage(pageVO);
        return success(DvSubjectConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备点检保养项目 Excel")
    @PreAuthorize("@ss.hasPermission('cmms:dv-subject:export')")
    @OperateLog(type = EXPORT)
    public void exportDvSubjectExcel(@Valid DvSubjectExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DvSubjectDO> list = dvSubjectService.getDvSubjectList(exportReqVO);
        // 导出 Excel
        List<DvSubjectExcelVO> datas = DvSubjectConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备点检保养项目.xls", "数据", DvSubjectExcelVO.class, datas);
    }

}
