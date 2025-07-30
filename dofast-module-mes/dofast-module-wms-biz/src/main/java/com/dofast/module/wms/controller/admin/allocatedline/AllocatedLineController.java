package com.dofast.module.wms.controller.admin.allocatedline;

import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.wms.controller.admin.allocatedrecord.vo.AllocatedRecordExportReqVO;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedHeaderDO;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;
import com.dofast.module.wms.enums.ErrorCodeConstants;
import com.dofast.module.wms.service.allocatedheader.AllocatedHeaderService;
import com.dofast.module.wms.service.allocatedrecord.AllocatedRecordService;
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
import java.math.BigDecimal;
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.wms.controller.admin.allocatedline.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedline.AllocatedLineDO;
import com.dofast.module.wms.convert.allocatedline.AllocatedLineConvert;
import com.dofast.module.wms.service.allocatedline.AllocatedLineService;

@Tag(name = "管理后台 - 调拨单身")
@RestController
@RequestMapping("/wms/allocated-line")
@Validated
public class AllocatedLineController {

    @Resource
    private AllocatedLineService allocatedLineService;

    @Resource
    private AllocatedRecordService allocatedRecordService;

    @Resource
    private AllocatedHeaderService allocatedHeaderService;

    @Resource
    private TaskApi taskApi;

    @PostMapping("/create")
    @Operation(summary = "创建调拨单身")
    @PreAuthorize("@ss.hasPermission('wms:allocated-line:create')")
    public CommonResult<Long> createAllocatedLine(@Valid @RequestBody AllocatedLineCreateReqVO createReqVO) {
        return success(allocatedLineService.createAllocatedLine(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新调拨单身")
    @PreAuthorize("@ss.hasPermission('wms:allocated-line:update')")
    public CommonResult<Boolean> updateAllocatedLine(@Valid @RequestBody AllocatedLineUpdateReqVO updateReqVO) {
        AllocatedHeaderDO allocatedHeaderDO = allocatedHeaderService.getAllocatedHeader(updateReqVO.getAllocatedId());

        boolean bindWorkorder = Boolean.parseBoolean(allocatedHeaderDO.getBindWorkorder());
        if(bindWorkorder){
            TaskDTO taskDTO = taskApi.getTask(allocatedHeaderDO.getTaskId());
            if (taskDTO == null) {
                return error(ErrorCodeConstants.ALLOCATED_TASK_NOT_EXISTS);
            }
            // 班组编码
            String attr1 = taskDTO.getAttr1();
            if(attr1 == null){
                return error(ErrorCodeConstants.ALLOCATED_HEADER_NEED_TASK_TEAM);
            }
        }
        allocatedLineService.updateAllocatedLine(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除调拨单身")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wms:allocated-line:delete')")
    public CommonResult<Boolean> deleteAllocatedLine(@RequestParam("id") Long id) {
        allocatedLineService.deleteAllocatedLine(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得调拨单身")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:allocated-line:query')")
    public CommonResult<AllocatedLineRespVO> getAllocatedLine(@RequestParam("id") Long id) {
        AllocatedLineDO allocatedLine = allocatedLineService.getAllocatedLine(id);
        return success(AllocatedLineConvert.INSTANCE.convert(allocatedLine));
    }

    @GetMapping("/list")
    @Operation(summary = "获得调拨单身列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('wms:allocated-line:query')")
    public CommonResult<List<AllocatedLineRespVO>> getAllocatedLineList(@RequestParam("ids") Collection<Long> ids) {
        List<AllocatedLineDO> list = allocatedLineService.getAllocatedLineList(ids);
        return success(AllocatedLineConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得调拨单身分页")
    @PreAuthorize("@ss.hasPermission('wms:allocated-line:query')")
    public CommonResult<PageResult<AllocatedLineRespVO>> getAllocatedLinePage(@Valid AllocatedLinePageReqVO pageVO) {
        PageResult<AllocatedLineDO> pageResult = allocatedLineService.getAllocatedLinePage(pageVO);
        return success(AllocatedLineConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出调拨单身 Excel")
    @PreAuthorize("@ss.hasPermission('wms:allocated-line:export')")
    @OperateLog(type = EXPORT)
    public void exportAllocatedLineExcel(@Valid AllocatedLineExportReqVO exportReqVO,
                                         HttpServletResponse response) throws IOException {
        List<AllocatedLineDO> list = allocatedLineService.getAllocatedLineList(exportReqVO);
        // 导出 Excel
        List<AllocatedLineExcelVO> datas = AllocatedLineConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "调拨单身.xls", "数据", AllocatedLineExcelVO.class, datas);
    }

    @PostMapping("/updateLine")
    @Operation(summary = "修改调拨单身")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-line:create')")
    public CommonResult<Long> updateLine(@Valid @RequestBody Map<String, Object> requestmap) {
        Integer headerId = (Integer) requestmap.get("headerId");

        AllocatedHeaderDO allocatedHeaderDO = allocatedHeaderService.getAllocatedHeader(Long.valueOf(headerId));


        boolean bindWorkorder = Boolean.parseBoolean(allocatedHeaderDO.getBindWorkorder());
        if(bindWorkorder){
            TaskDTO taskDTO = taskApi.getTask(allocatedHeaderDO.getTaskId());
            if (taskDTO == null) {
                return error(ErrorCodeConstants.ALLOCATED_TASK_NOT_EXISTS);
            }
            // 班组编码
            String attr1 = taskDTO.getAttr1();
            if(attr1 == null){
                return error(ErrorCodeConstants.ALLOCATED_HEADER_NEED_TASK_TEAM);
            }
        }

        List<Map<String, Object>> detailList = (List<Map<String, Object>>) requestmap.get("bomList");

        // 比对recordList与detailList, 若存在相同的则进行修改, 不存在则新增
        // 基于物料号与批次号视为一个唯一标识. 比对recordList中是否存在

        List<AllocatedRecordDO> addList = new ArrayList<>();
        List<AllocatedRecordDO> editList = new ArrayList<>();

        for (Map<String, Object> detail : detailList) {
            String itemCode = (String) detail.get("itemCode");
            String batchCode = (String) detail.get("batchCode");
            String parentBatchCode = (String) detail.get("parentBatchCode");

            // 根据单头Id获取记录
            AllocatedRecordExportReqVO exportReqVO = new AllocatedRecordExportReqVO();
            exportReqVO.setAllocatedId(headerId.longValue());
            exportReqVO.setItemCode(itemCode);
            exportReqVO.setBatchCode(batchCode);
            List<AllocatedRecordDO> recordList = allocatedRecordService.getAllocatedRecordList(exportReqVO);
            if (recordList.isEmpty()) {
                // 不存在相同的, 新增记录
                AllocatedRecordDO allocatedRecordDO = new AllocatedRecordDO();
                allocatedRecordDO.setAllocatedId(headerId.longValue());
                allocatedRecordDO.setItemCode((String) detail.get("itemCode"));
                allocatedRecordDO.setItemName((String) detail.get("itemName"));
                allocatedRecordDO.setSpecification((String) detail.get("specification"));
                // String itemIdStr = Optional.ofNullable((String) detail.get("itemId")).orElse("0");
                Integer itemId = Optional.ofNullable((Integer)detail.get("itemId")).orElse(0);
                allocatedRecordDO.setItemId(Long.valueOf(itemId));
                allocatedRecordDO.setBatchCode((String) detail.get("batchCode"));
                // 2025-6-8 追加母批次号
                allocatedRecordDO.setParentBatchCode((String) detail.get("parentBatchCode"));
                allocatedRecordDO.setWarehouseCode((String) detail.get("warehouseCode"));
                allocatedRecordDO.setWarehouseName((String) detail.get("warehouseName"));
                Integer warehouseId = (Integer) detail.get("warehouseId");
                allocatedRecordDO.setWarehouseId(warehouseId.longValue());
                allocatedRecordDO.setLocationCode((String) detail.get("locationCode"));
                allocatedRecordDO.setLocationName((String) detail.get("locationName"));
                Integer locationId = (Integer) detail.get("locationId");
                allocatedRecordDO.setLocationId(locationId.longValue());
                allocatedRecordDO.setAreaCode((String) detail.get("areaCode"));
                allocatedRecordDO.setAreaName((String) detail.get("areaName"));
                Integer areaId = (Integer) detail.get("areaId");
                allocatedRecordDO.setAreaId(areaId.longValue());
                BigDecimal quantityAllocated = new BigDecimal(String.valueOf(detail.get("quantityAllocated")));
                allocatedRecordDO.setQuantityAllocated(quantityAllocated.doubleValue());
                allocatedRecordDO.setUnitOfMeasure((String) detail.get("unitOfMeasure"));
                allocatedRecordDO.setMaterialStockId((Long) detail.get("materialStockId"));
                allocatedRecordDO.setAllocatedFlag("N");
                allocatedRecordDO.setVendorCode((String) detail.get("vendorCode"));
                addList.add(allocatedRecordDO);
            }
        }
        allocatedRecordService.createBatchAllocatedRecord(addList);
        return success();
    }


}
