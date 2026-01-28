package com.dofast.module.wms.controller.admin.allocatedrecord;

import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.api.ProcessApi.ProcessApi;
import com.dofast.module.pro.api.ProcessApi.dto.ProcessDTO;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderCreateReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockExportReqVO;
import com.dofast.module.wms.convert.allocatedheader.AllocatedHeaderConvert;
import com.dofast.module.wms.convert.issueheader.IssueHeaderConvert;
import com.dofast.module.wms.convert.materialstock.MaterialStockConvert;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.enums.ErrorCodeConstants;
import com.dofast.module.wms.service.allocatedheader.AllocatedHeaderService;
import com.dofast.module.wms.service.allocatedheader.AllocatedHeaderServiceImpl;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.materialstock.MaterialStockService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.wms.controller.admin.allocatedrecord.vo.*;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;
import com.dofast.module.wms.convert.allocatedrecord.AllocatedRecordConvert;
import com.dofast.module.wms.service.allocatedrecord.AllocatedRecordService;

@Tag(name = "管理后台 - 调拨单身记录")
@RestController
@RequestMapping("/wms/allocated-record")
@Validated
public class AllocatedRecordController {

    @Resource
    private AllocatedRecordService allocatedRecordService;

    @Resource
    private AllocatedHeaderService allocatedHeaderService;

    @Resource
    private MaterialStockService materialStockService;

    @Resource
    private TaskApi taskApi;

    @Resource
    private ProcessApi processApi;

    @Resource
    private IssueHeaderService issueHeaderService;


    @PostMapping("/create")
    @Operation(summary = "创建调拨单身记录")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:create')")
    public CommonResult<Long> createAllocatedRecord(@Valid @RequestBody AllocatedRecordCreateReqVO createReqVO) {
        return success(allocatedRecordService.createAllocatedRecord(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新调拨单身记录")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:update')")
    public CommonResult<Boolean> updateAllocatedRecord(@Valid @RequestBody AllocatedRecordUpdateReqVO updateReqVO) {
        allocatedRecordService.updateAllocatedRecord(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除调拨单身记录")
    @Parameter(name = "id", description = "编号", required = true)
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:delete')")
    public CommonResult<Boolean> deleteAllocatedRecord(@RequestParam("id") Long id) {
        allocatedRecordService.deleteAllocatedRecord(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得调拨单身记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:query')")
    public CommonResult<AllocatedRecordRespVO> getAllocatedRecord(@RequestParam("id") Long id) {
        AllocatedRecordDO allocatedRecord = allocatedRecordService.getAllocatedRecord(id);
        return success(AllocatedRecordConvert.INSTANCE.convert(allocatedRecord));
    }

    @GetMapping("/getByHeaderId")
    @Operation(summary = "获得调拨单身记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:query')")
    public CommonResult<List<AllocatedRecordRespVO>> getAllocatedRecordByHeaderId(@RequestParam("id") Long id) {
        AllocatedRecordExportReqVO exportReqVO = new AllocatedRecordExportReqVO();
        exportReqVO.setAllocatedId(id);
        List<AllocatedRecordDO> allocatedRecord = allocatedRecordService.getAllocatedRecordList(exportReqVO);
        return success(AllocatedRecordConvert.INSTANCE.convertList(allocatedRecord));
    }

    @GetMapping("/list")
    @Operation(summary = "获得调拨单身记录列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:query')")
    public CommonResult<List<AllocatedRecordRespVO>> getAllocatedRecordList(@RequestParam("ids") Collection<Long> ids) {
        List<AllocatedRecordDO> list = allocatedRecordService.getAllocatedRecordList(ids);
        return success(AllocatedRecordConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得调拨单身记录分页")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:query')")
    public CommonResult<PageResult<AllocatedRecordRespVO>> getAllocatedRecordPage(@Valid AllocatedRecordPageReqVO pageVO) {
        PageResult<AllocatedRecordDO> pageResult = allocatedRecordService.getAllocatedRecordPage(pageVO);
        return success(AllocatedRecordConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出调拨单身记录 Excel")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:export')")
    @OperateLog(type = EXPORT)
    public void exportAllocatedRecordExcel(@Valid AllocatedRecordExportReqVO exportReqVO,
                                           HttpServletResponse response) throws IOException {
        List<AllocatedRecordDO> list = allocatedRecordService.getAllocatedRecordList(exportReqVO);
        // 导出 Excel
        List<AllocatedRecordExcelVO> datas = AllocatedRecordConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "调拨明细.xls", "数据", AllocatedRecordExcelVO.class, datas);
    }

    @PostMapping("/checkExist")
    @Operation(summary = "校验是否为调拨单身条目")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:create')")
    public CommonResult<Boolean> checkExist(@Valid @RequestBody Map<String, Object> resMap) {
        // 基于调拨单头获取调拨单身条目
        String allocatedId = (String) resMap.get("allocatedHeaderId");
        String batchCode = (String) resMap.get("batchCode");
        String itemCode = (String) resMap.get("itemCode");
        AllocatedRecordExportReqVO exportReqVO = new AllocatedRecordExportReqVO();
        exportReqVO.setAllocatedId(Long.valueOf(allocatedId));
        exportReqVO.setBatchCode(batchCode);
        exportReqVO.setItemCode(itemCode);
        List<AllocatedRecordDO> recordList = allocatedRecordService.getAllocatedRecordList(exportReqVO);
        boolean exist = recordList.size() > 0;
        return success(exist);
    }

    @PostMapping("/confirm")
    @Operation(summary = "确认调拨单身条目")
    //@PreAuthorize("@ss.hasPermission('wms:allocated-record:create')")
    public void confirm(@Valid @RequestBody Map<String, Object> resMap) {
        // 基于调拨单头获取调拨单身条目
        String allocatedId = (String) resMap.get("allocatedHeaderId");
        AllocatedHeaderDO allocatedHeader = allocatedHeaderService.getAllocatedHeader(Long.valueOf(allocatedId));
        List<Map<String, Object>> recordList = (List<Map<String, Object>>) resMap.get("recordList");
        for (Map<String, Object> record : recordList) {
            String batchCode = (String) record.get("batchCode");
            String parentBatchCode = (String) record.get("parentBatchCode");
            String itemCode = (String) record.get("itemCode");
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setBatchCode(batchCode);
            exportReqVO.setItemCode(itemCode);
            // 2025-6-8 追加母批次
            exportReqVO.setParentBatchCode(parentBatchCode);
            exportReqVO.setWarehouseCode(allocatedHeader.getWarehouseCode());
            exportReqVO.setLocationCode(allocatedHeader.getLocationCode());
            exportReqVO.setAreaCode(allocatedHeader.getAreaCode());
            List<MaterialStockDO> materialStockList = materialStockService.getMaterialStockList(exportReqVO);
            MaterialStockDO materialStock = materialStockList.get(0);
            materialStock.setRecptStatus("Y");
            materialStock.setUpdateTime(LocalDateTime.now());
            materialStockService.updateMaterialStock(MaterialStockConvert.INSTANCE.convert02(materialStock));
        }
        // 查看当前调拨单条目是否全都确认完成
        AllocatedRecordExportReqVO exportReqVO = new AllocatedRecordExportReqVO();
        exportReqVO.setAllocatedId(Long.valueOf(allocatedId));
        List<AllocatedRecordDO> records = allocatedRecordService.getAllocatedRecordList(exportReqVO);
        Integer count = 0;
        for (AllocatedRecordDO record : records) {
            // 查看库存
            MaterialStockExportReqVO query = new MaterialStockExportReqVO();
            query.setBatchCode(record.getBatchCode());
            query.setItemCode(record.getItemCode());
            query.setWarehouseCode(allocatedHeader.getWarehouseCode());
            query.setLocationCode(allocatedHeader.getLocationCode());
            query.setAreaCode(allocatedHeader.getAreaCode());
            List<MaterialStockDO> stockList = materialStockService.getMaterialStockList(query);
           if("Y".equals(stockList.get(0).getRecptStatus())){
               count++;
           }
        }
        if(count == records.size()){
            // 视为当前调拨单确认完成
            // 开始更新调拨单状态
            allocatedHeader.setStatus(Constant.ORDER_STATUS_FINISHED);// 确认完成
            allocatedHeader.setUpdateTime(LocalDateTime.now());
            allocatedHeaderService.updateAllocatedHeader(AllocatedHeaderConvert.INSTANCE.convert01(allocatedHeader));
            // 开始追加领料单信息
            TaskDTO taskDTO = taskApi.getTask(allocatedHeader.getTaskCode());
            ProcessDTO reqDTO = processApi.getcess(taskDTO.getProcessCode());

            // 追加领料单信息
            IssueHeaderDO issueHeaderDO = new IssueHeaderDO();
            issueHeaderDO.setStatus(Constant.ORDER_STATUS_PREPARE);
            issueHeaderDO.setWorkorderId(allocatedHeader.getWorkorderId());
            issueHeaderDO.setWorkorderCode(allocatedHeader.getWorkorderCode());
            issueHeaderDO.setClientCode(allocatedHeader.getClientCode());
            issueHeaderDO.setClientName(allocatedHeader.getClientName());
            issueHeaderDO.setClientId(allocatedHeader.getClientId());
            issueHeaderDO.setWarehouseId(allocatedHeader.getWarehouseId());
            issueHeaderDO.setWarehouseCode(allocatedHeader.getWarehouseCode());
            issueHeaderDO.setWarehouseName(allocatedHeader.getWarehouseName());
            issueHeaderDO.setLocationId(allocatedHeader.getLocationId());
            issueHeaderDO.setLocationCode(allocatedHeader.getLocationCode());
            issueHeaderDO.setLocationName(allocatedHeader.getLocationName());
            issueHeaderDO.setAreaId(allocatedHeader.getAreaId());
            issueHeaderDO.setAreaCode(allocatedHeader.getAreaCode());
            issueHeaderDO.setAreaName(allocatedHeader.getAreaName());
            issueHeaderDO.setIssueDate(LocalDateTime.now());
            issueHeaderDO.setTaskId(allocatedHeader.getTaskId());
            issueHeaderDO.setTaskCode(allocatedHeader.getTaskCode());
            issueHeaderDO.setWorkstationCode(allocatedHeader.getWorkstationCode());
            issueHeaderDO.setWorkstationName(allocatedHeader.getWorkstationName());
            issueHeaderDO.setWorkstationId(allocatedHeader.getWorkstationId());
            // 获取当前日期
            LocalDate currentDate = LocalDate.now();
            // 定义日期格式
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String issueCode = "ISSUE" + currentDate.format(formatter) + ThreadLocalRandom.current().nextInt(100, 1000);
            issueHeaderDO.setIssueCode(issueCode);
            issueHeaderDO.setIssueName(allocatedHeader.getAllocatedCode());
            issueHeaderDO.setProcessCode(reqDTO.getProcessCode());
            issueHeaderDO.setProcessName(reqDTO.getProcessName());
            IssueHeaderCreateReqVO issueHeader = IssueHeaderConvert.INSTANCE.convert02(issueHeaderDO);
            issueHeaderService.createIssueHeader(issueHeader);
        }

    }


}
