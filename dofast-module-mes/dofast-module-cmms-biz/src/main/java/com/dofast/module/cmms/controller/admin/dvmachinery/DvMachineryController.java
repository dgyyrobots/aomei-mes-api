package com.dofast.module.cmms.controller.admin.dvmachinery;

import com.dofast.framework.common.pojo.CommonResult;
import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.excel.core.util.ExcelUtils;
import com.dofast.framework.operatelog.core.annotations.OperateLog;
import com.dofast.module.cmms.controller.admin.dvmachinery.vo.*;
import com.dofast.module.cmms.convert.dvmachinery.DvMachineryConvert;
import com.dofast.module.cmms.dal.dataobject.dvmachinery.DvMachineryDO;
import com.dofast.module.cmms.service.dvmachinery.DvMachineryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static com.dofast.framework.common.pojo.CommonResult.success;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "设备管理 - 设备台账")
@RestController
@RequestMapping("/mes/cmms/dv-machinery")
@Validated
public class DvMachineryController {

    @Resource
    private DvMachineryService dvMachineryService;

//    @Autowired
//    private WmBarCodeUtil wmBarCodeUtil;

    @PostMapping("/create")
    @Operation(summary = "创建设备台账")
    @PreAuthorize("@ss.hasPermission('cmms:dv-machinery:create')")
    public CommonResult<Long> createDvMachinery(@Valid @RequestBody DvMachineryCreateReqVO createReqVO) {
//        wmBarCodeUtil.generateBarCode(UserConstants.BARCODE_TYPE_MACHINERY,dvMachinery.getMachineryId(),dvMachinery.getMachineryCode(),dvMachinery.getMachineryName());

        return success(dvMachineryService.createDvMachinery(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备台账")
    @PreAuthorize("@ss.hasPermission('cmms:dv-machinery:update')")
    public CommonResult<Boolean> updateDvMachinery(@Valid @RequestBody DvMachineryUpdateReqVO updateReqVO) {
        dvMachineryService.updateDvMachinery(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备台账")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('cmms:dv-machinery:delete')")
    public CommonResult<Boolean> deleteDvMachinery(@RequestParam("id") Long id) {
        dvMachineryService.deleteDvMachinery(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备台账")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('cmms:dv-machinery:query')")
    public CommonResult<DvMachineryRespVO> getDvMachinery(@RequestParam("id") Long id) {
        DvMachineryDO dvMachinery = dvMachineryService.getDvMachinery(id);
        return success(DvMachineryConvert.INSTANCE.convert(dvMachinery));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备台账列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('cmms:dv-machinery:query')")
    public CommonResult<List<DvMachineryRespVO>> getDvMachineryList(@RequestParam("ids") Collection<Long> ids) {
        List<DvMachineryDO> list = dvMachineryService.getDvMachineryList(ids);
        return success(DvMachineryConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备台账分页")
    @PreAuthorize("@ss.hasPermission('cmms:dv-machinery:query')")
    public CommonResult<PageResult<DvMachineryRespVO>> getDvMachineryPage(@Valid DvMachineryPageReqVO pageVO) {
        PageResult<DvMachineryDO> pageResult = dvMachineryService.getDvMachineryPage(pageVO);
        return success(DvMachineryConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备台账 Excel")
    @PreAuthorize("@ss.hasPermission('cmms:dv-machinery:export')")
    @OperateLog(type = EXPORT)
    public void exportDvMachineryExcel(@Valid DvMachineryExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DvMachineryDO> list = dvMachineryService.getDvMachineryList(exportReqVO);
        // 导出 Excel
        List<DvMachineryExcelVO> datas = DvMachineryConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备台账.xls", "数据", DvMachineryExcelVO.class, datas);
    }

}
