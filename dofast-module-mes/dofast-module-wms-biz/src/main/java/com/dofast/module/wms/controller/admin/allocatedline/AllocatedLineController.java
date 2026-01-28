package com.dofast.module.wms.controller.admin.allocatedline;

import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.wms.controller.admin.allocatedrecord.vo.AllocatedRecordExportReqVO;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedHeaderDO;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;
import com.dofast.module.wms.enums.ErrorCodeConstants;
import com.dofast.module.wms.service.allocatedheader.AllocatedHeaderService;
import com.dofast.module.wms.service.allocatedrecord.AllocatedRecordService;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional(rollbackFor = Exception.class) // 添加事务管理
    public CommonResult<Long> updateLine(@Valid @RequestBody Map<String, Object> requestmap) {
        Integer headerId = (Integer) requestmap.get("headerId");

        AllocatedHeaderDO allocatedHeaderDO = allocatedHeaderService.getAllocatedHeader(Long.valueOf(headerId));
        boolean bindWorkorder = Boolean.parseBoolean(allocatedHeaderDO.getBindWorkorder());
        if(bindWorkorder){
            TaskDTO taskDTO = taskApi.getTask(allocatedHeaderDO.getTaskId());
            if (taskDTO == null) {
                return error(ErrorCodeConstants.ALLOCATED_TASK_NOT_EXISTS);
            }
            if (taskDTO.getAttr1() == null) {
                return error(ErrorCodeConstants.ALLOCATED_HEADER_NEED_TASK_TEAM);
            }
        }

        List<Map<String, Object>> detailList = (List<Map<String, Object>>) requestmap.get("bomList");
        List<AllocatedRecordDO> existingRecords = allocatedRecordService.getAllocatedRecordList(new AllocatedRecordExportReqVO().setAllocatedId(headerId.longValue()));

        Map<String, AllocatedRecordDO> existingRecordMap = new HashMap<>();
        for (AllocatedRecordDO record : existingRecords) {
            String key = record.getItemCode() + "|" + record.getBatchCode();
            existingRecordMap.put(key, record);
        }

        Set<String> frontendKeys = new HashSet<>();
        List<AllocatedRecordDO> addList = new ArrayList<>();
        List<AllocatedRecordDO> updateList = new ArrayList<>();
        List<Long> deleteIdList = new ArrayList<>();

        for (Map<String, Object> detail : detailList) {
            String itemCode = (String) detail.get("itemCode");
            String batchCode = (String) detail.get("batchCode");
            String key = itemCode + "|" + batchCode;
            frontendKeys.add(key);

            if (!existingRecordMap.containsKey(key)) {
                AllocatedRecordDO newRecord = buildAllocatedRecordDO(detail, headerId);
                addList.add(newRecord);
            } else {
                AllocatedRecordDO existingRecord = existingRecordMap.get(key);
                updateRecordFromDetail(existingRecord, detail);
                updateList.add(existingRecord);
            }
        }

        for (AllocatedRecordDO existingRecord : existingRecords) {
            String key = existingRecord.getItemCode() + "|" + existingRecord.getBatchCode();
            if (!frontendKeys.contains(key)) {
                deleteIdList.add(existingRecord.getId());
            }
        }

        if (!addList.isEmpty()) {
            allocatedRecordService.createBatchAllocatedRecord(addList);
        }
        if (!updateList.isEmpty()) {
            allocatedRecordService.updateAllocatedRecordBatch(updateList);
        }
        if (!deleteIdList.isEmpty()) {
            allocatedRecordService.deleteAllocatedRecordBatch(deleteIdList);
        }

        return success();
    }

    // 构建新增记录的实体
    private AllocatedRecordDO buildAllocatedRecordDO(Map<String, Object> detail, Integer headerId) {
        AllocatedRecordDO record = new AllocatedRecordDO();
        record.setAllocatedId(headerId.longValue());
        record.setItemCode((String) detail.get("itemCode"));
        record.setItemName((String) detail.get("itemName"));
        record.setSpecification((String) detail.get("specification"));
        record.setItemId(Long.valueOf(Optional.ofNullable((Integer) detail.get("itemId")).orElse(0)));
        record.setBatchCode((String) detail.get("batchCode"));
        record.setParentBatchCode((String) detail.get("parentBatchCode"));
        record.setWarehouseCode((String) detail.get("warehouseCode"));
        record.setWarehouseName((String) detail.get("warehouseName"));
        record.setWarehouseId(((Integer) detail.get("warehouseId")).longValue());
        record.setLocationCode((String) detail.get("locationCode"));
        record.setLocationName((String) detail.get("locationName"));
        record.setLocationId(((Integer) detail.get("locationId")).longValue());
        record.setAreaCode((String) detail.get("areaCode"));
        record.setAreaName((String) detail.get("areaName"));
        record.setAreaId(((Integer) detail.get("areaId")).longValue());
        BigDecimal quantityAllocated = new BigDecimal(String.valueOf(detail.get("quantityAllocated")));
        record.setQuantityAllocated(quantityAllocated.doubleValue());
        record.setUnitOfMeasure((String) detail.get("unitOfMeasure"));
        record.setMaterialStockId((Long) detail.get("materialStockId"));
        record.setAllocatedFlag("N");
        record.setVendorCode((String) detail.get("vendorCode"));
        return record;
    }

    // 更新现有记录的字段
    private void updateRecordFromDetail(AllocatedRecordDO record, Map<String, Object> detail) {
        record.setItemName((String) detail.get("itemName"));
        record.setSpecification((String) detail.get("specification"));
        record.setItemId(Long.valueOf(Optional.ofNullable((Integer) detail.get("itemId")).orElse(0)));
        record.setParentBatchCode((String) detail.get("parentBatchCode"));
        record.setWarehouseCode((String) detail.get("warehouseCode"));
        record.setWarehouseName((String) detail.get("warehouseName"));
        record.setWarehouseId(((Integer) detail.get("warehouseId")).longValue());
        record.setLocationCode((String) detail.get("locationCode"));
        record.setLocationName((String) detail.get("locationName"));
        record.setLocationId(((Integer) detail.get("locationId")).longValue());
        record.setAreaCode((String) detail.get("areaCode"));
        record.setAreaName((String) detail.get("areaName"));
        record.setAreaId(((Integer) detail.get("areaId")).longValue());
        BigDecimal quantityAllocated = new BigDecimal(String.valueOf(detail.get("quantityAllocated")));
        record.setQuantityAllocated(quantityAllocated.doubleValue());
        record.setUnitOfMeasure((String) detail.get("unitOfMeasure"));
        record.setMaterialStockId((Long) detail.get("materialStockId"));
        record.setVendorCode((String) detail.get("vendorCode"));
    }


}
