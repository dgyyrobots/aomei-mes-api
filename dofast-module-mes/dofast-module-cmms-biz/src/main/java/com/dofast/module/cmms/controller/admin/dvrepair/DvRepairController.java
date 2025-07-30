package com.dofast.module.cmms.controller.admin.dvrepair;

import com.dofast.module.cmms.controller.admin.dvchecksubject.vo.DvCheckSubjectExportReqVO;
import com.dofast.module.cmms.dal.dataobject.dvcheckmachinery.DvCheckMachineryDO;
import com.dofast.module.cmms.dal.dataobject.dvcheckplan.DvCheckPlanDO;
import com.dofast.module.cmms.dal.dataobject.dvchecksubject.DvCheckSubjectDO;
import com.dofast.module.cmms.dal.dataobject.dvrepairline.DvRepairLineDO;
import com.dofast.module.cmms.enums.ErrorCodeConstants;
import com.dofast.module.cmms.service.dvcheckmachinery.DvCheckMachineryService;
import com.dofast.module.cmms.service.dvcheckplan.DvCheckPlanService;
import com.dofast.module.cmms.service.dvchecksubject.DvCheckSubjectService;
import com.dofast.module.cmms.service.dvrepairline.DvRepairLineService;
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

import com.dofast.module.cmms.controller.admin.dvrepair.vo.*;
import com.dofast.module.cmms.dal.dataobject.dvrepair.DvRepairDO;
import com.dofast.module.cmms.convert.dvrepair.DvRepairConvert;
import com.dofast.module.cmms.service.dvrepair.DvRepairService;

@Tag(name = "设备管理 - 设备维修单")
@RestController
@RequestMapping("/mes/cmms/dv-repair")
@Validated
public class DvRepairController {

    @Resource
    private DvRepairService dvRepairService;

    @Resource
    private DvCheckMachineryService dvCheckMachineryService;

    @Resource
    private DvCheckSubjectService dvCheckSubjectService;

    @Resource
    private DvRepairLineService dvRepairLineService;


    @PostMapping("/create")
    @Operation(summary = "创建设备维修单")
    @PreAuthorize("@ss.hasPermission('cmms:dv-repair:create')")
    public CommonResult<Long> createDvRepair(@Valid @RequestBody DvRepairCreateReqVO createReqVO) {
        if(Constant.NOT_UNIQUE.equals(dvRepairService.checkCodeUnique(createReqVO))){
            return error(ErrorCodeConstants.DV_REPAIR_CODE_NOT_UNIQUE);
        }
        Long repairId = dvRepairService.createDvRepair(createReqVO);
        // 根据当前设备编码, 获取点检计划信息
        DvCheckMachineryDO machineryDO = Optional.ofNullable(dvCheckMachineryService.getDvCheckMachineryByMachineryCode(createReqVO.getMachineryCode())).orElse(null);
        if(machineryDO == null){
            return error(ErrorCodeConstants.DV_MACHINERY_NOT_CHECKPLAN);
        }
        // 基于当前点检计划, 获取检验项目
        List<DvCheckSubjectDO> subjectList = dvCheckSubjectService.getDvCheckSubjectList(new DvCheckSubjectExportReqVO().setPlanId(machineryDO.getPlanId()));

        List<DvRepairLineDO> addList = new ArrayList<>();
        // 开始添加维修单身行信息
        for(DvCheckSubjectDO subjectDO : subjectList){
            DvRepairLineDO line = new DvRepairLineDO();
            line.setSubjectId(subjectDO.getSubjectId());
            line.setRepairId(repairId);
            line.setSubjectCode(subjectDO.getSubjectCode());
            line.setSubjectName(subjectDO.getSubjectName());
            line.setSubjectType(subjectDO.getSubjectType());
            line.setSubjectContent(subjectDO.getSubjectContent());
            line.setSubjectStandard(subjectDO.getSubjectStandard());
            addList.add(line);
        }
        dvRepairLineService.insertBatch(addList);
        return success();
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备维修单")
    @PreAuthorize("@ss.hasPermission('cmms:dv-repair:update')")
    public CommonResult<Boolean> updateDvRepair(@Valid @RequestBody DvRepairUpdateReqVO updateReqVO) {
        if(Constant.NOT_UNIQUE.equals(dvRepairService.checkCodeUnique(updateReqVO))){
            return error(ErrorCodeConstants.DV_REPAIR_CODE_NOT_UNIQUE);
        }
        dvRepairService.updateDvRepair(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备维修单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmms:dv-repair:delete')")
    public CommonResult<Boolean> deleteDvRepair(@RequestParam("id") Long id) {
        dvRepairService.deleteDvRepair(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备维修单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmms:dv-repair:query')")
    public CommonResult<DvRepairRespVO> getDvRepair(@RequestParam("id") Long id) {
        DvRepairDO dvRepair = dvRepairService.getDvRepair(id);
        return success(DvRepairConvert.INSTANCE.convert(dvRepair));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备维修单列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cmms:dv-repair:query')")
    public CommonResult<List<DvRepairRespVO>> getDvRepairList(@RequestParam("ids") Collection<Long> ids) {
        List<DvRepairDO> list = dvRepairService.getDvRepairList(ids);
        return success(DvRepairConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备维修单分页")
    @PreAuthorize("@ss.hasPermission('cmms:dv-repair:query')")
    public CommonResult<PageResult<DvRepairRespVO>> getDvRepairPage(@Valid DvRepairPageReqVO pageVO) {
        PageResult<DvRepairDO> pageResult = dvRepairService.getDvRepairPage(pageVO);
        return success(DvRepairConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备维修单 Excel")
    @PreAuthorize("@ss.hasPermission('cmms:dv-repair:export')")
    @OperateLog(type = EXPORT)
    public void exportDvRepairExcel(@Valid DvRepairExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DvRepairDO> list = dvRepairService.getDvRepairList(exportReqVO);
        // 导出 Excel
        List<DvRepairExcelVO> datas = DvRepairConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备维修单.xls", "数据", DvRepairExcelVO.class, datas);
    }

}
