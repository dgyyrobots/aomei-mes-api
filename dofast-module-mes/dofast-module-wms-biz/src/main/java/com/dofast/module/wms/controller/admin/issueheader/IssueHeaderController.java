package com.dofast.module.wms.controller.admin.issueheader;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.util.StringUtils;
import com.dofast.framework.common.pojo.UserConstants;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.cmms.api.dvmachinery.DvMachineryApi;
import com.dofast.module.cmms.api.dvmachinery.dto.DvMachineryDTO;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.api.FeedbackApi.FeedbackApi;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.ProcessApi.dto.ProcessDTO;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.pro.api.WorkorderApi.WorkorderApi;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderBomDTO;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderDTO;
import com.dofast.module.wms.api.ERPApi.WorkorderERPAPI;
import com.dofast.module.wms.controller.admin.allocatedheader.vo.AllocatedHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.allocatedheader.vo.AllocatedHeaderUpdateReqVO;
import com.dofast.module.wms.controller.admin.allocatedrecord.vo.AllocatedRecordExportReqVO;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineExportReqVO;
import com.dofast.module.wms.controller.admin.issueline.vo.IssueLineExportReqVO;
import com.dofast.module.wms.controller.admin.issueline.vo.IssueLineListVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockExportReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockUpdateReqVO;
import com.dofast.module.wms.convert.allocatedheader.AllocatedHeaderConvert;
import com.dofast.module.wms.convert.issueline.IssueLineConvert;
import com.dofast.module.wms.dal.dataobject.allocatedheader.AllocatedHeaderDO;
import com.dofast.module.wms.dal.dataobject.allocatedrecord.AllocatedRecordDO;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.dal.mysql.issueline.IssueLineMapper;
import com.dofast.module.wms.enums.ErrorCodeConstants;
import com.dofast.module.wms.service.feedline.FeedLineService;
import com.dofast.module.wms.service.issueline.IssueLineService;
import com.dofast.module.wms.service.materialstock.MaterialStockService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagecore.StorageCoreService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.common.pojo.UserConstants.BATCH_CODE_SWITCH_DATE;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.wms.controller.admin.issueheader.vo.*;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.convert.issueheader.IssueHeaderConvert;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.pro.api.ProcessApi.ProcessApi;


@Tag(name = "管理后台 - 生产领料单头")
@RestController
@RequestMapping("/wms/issue-header")
@Validated
public class IssueHeaderController {

    @Resource
    private IssueHeaderService issueHeaderService;
    @Resource
    private IssueLineService issueLineService;

    @Resource
    private IssueLineMapper issuLineMapper;


    @Resource
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Resource
    private StorageCoreService storageCoreServicel;

    @Resource
    private TaskApi taskApi;

    @Resource
    private DvMachineryApi dvMachineryApi;

    @Resource
    private ProcessApi processApi;

    @Resource
    private FeedLineService feedLineService;

    @Resource
    private WorkorderERPAPI workorderERPAPI;

    @Resource
    private WorkorderApi workorderApi;

    @Resource
    private MaterialStockService materialStockService;

    @Resource
    private FeedbackApi feedbackApi;

    @PostMapping("/create")
    @Operation(summary = "创建生产领料单头")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:create')")
    public CommonResult<Long> createIssueHeader(@Valid @RequestBody IssueHeaderCreateReqVO createReqVO) {
        if (Constant.NOT_UNIQUE.equals(issueHeaderService.checkIssueCodeUnique(createReqVO))) {
            return error(ErrorCodeConstants.ISSUE_HEADER_CDOE_EXISTS);
        }

        WorkorderDTO workorderDO = workorderApi.getWorkorder(createReqVO.getWorkorderCode());
        if ("FINISHED".equals(workorderDO.getStatus())) {
            return error(ErrorCodeConstants.WORKORDER_FINSHED_NOT_AVALIABLE);
        }

        // 校验当前的任务单是否已创建调拨单
        String taskCode = createReqVO.getTaskCode();
        IssueHeaderExportReqVO exportReqVO = new IssueHeaderExportReqVO();
        exportReqVO.setTaskCode(taskCode);
        List<IssueHeaderDO> issueHeaderList = issueHeaderService.getIssueHeaderList(exportReqVO);
        if (!issueHeaderList.isEmpty()) {
            return error(ErrorCodeConstants.ISSUE_HEADER_TASK_EXISTS);
        }

        TaskDTO taskDTO = taskApi.getTask(taskCode);
        LocalDate localDate = LocalDate.now();
        String dateStr = localDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 生成3位随机数
        int random = (int) ((Math.random() * 9 + 1) * 100);
        createReqVO.setIssueName(taskDTO.getProcessName() + "领料单" + dateStr + random);

        if (taskDTO.getAttr1() == null) {
            return error(ErrorCodeConstants.ALLOCATED_HEADER_NEED_TASK_TEAM);
        }

        ProcessDTO processDTO = processApi.getcess(createReqVO.getProcessCode());
        createReqVO.setProcessName(processDTO.getProcessName());

        //根据领料单头上的仓库、库区、库位ID设置对应的编号和名称
        if (StrUtils.isNotNull(createReqVO.getWarehouseId())) {
            WarehouseDO warehouseDO = warehouseService.getWarehouse(createReqVO.getWarehouseId());
            createReqVO.setWarehouseCode(warehouseDO.getWarehouseCode());
            createReqVO.setWarehouseName(warehouseDO.getWarehouseName());
        }
        if (StrUtils.isNotNull(createReqVO.getLocationId())) {
            StorageLocationDO storageLocationDO = storageLocationService.getStorageLocation(createReqVO.getLocationId());
            createReqVO.setLocationCode(storageLocationDO.getLocationCode());
            createReqVO.setLocationName(storageLocationDO.getLocationName());
        }
        if (StrUtils.isNotNull(createReqVO.getAreaId())) {
            StorageAreaDO storageAreaDO = storageAreaService.getStorageArea(createReqVO.getAreaId());
            createReqVO.setAreaCode(storageAreaDO.getAreaCode());
            createReqVO.setAreaName(storageAreaDO.getAreaName());
        }
        // 将任务单实际开工时间进行记录
        taskDTO.setActualStartTime(LocalDateTime.now());
        taskApi.updateTask(taskDTO);
        return success(issueHeaderService.createIssueHeader(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产领料单头")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:update')")
    public CommonResult<Boolean> updateIssueHeader(@Valid @RequestBody IssueHeaderUpdateReqVO updateReqVO) {
        if (Constant.NOT_UNIQUE.equals(issueHeaderService.checkIssueCodeUnique(updateReqVO))) {
            return error(ErrorCodeConstants.ISSUE_HEADER_CDOE_EXISTS);
        }

        WorkorderDTO workorderDO = workorderApi.getWorkorder(updateReqVO.getWorkorderCode());
        if ("FINISHED".equals(workorderDO.getStatus())) {
            return error(ErrorCodeConstants.WORKORDER_FINSHED_NOT_AVALIABLE);
        }

        if (StrUtils.isNotNull(updateReqVO.getWarehouseId())) {
            WarehouseDO warehouse = warehouseService.getWarehouse(updateReqVO.getWarehouseId());
            updateReqVO.setWarehouseCode(warehouse.getWarehouseCode());
            updateReqVO.setWarehouseName(warehouse.getWarehouseName());
        }
        if (StrUtils.isNotNull(updateReqVO.getLocationId())) {
            StorageLocationDO location = storageLocationService.getStorageLocation(updateReqVO.getLocationId());
            updateReqVO.setLocationCode(location.getLocationCode());
            updateReqVO.setLocationName(location.getLocationName());
        }
        if (StrUtils.isNotNull(updateReqVO.getAreaId())) {
            StorageAreaDO area = storageAreaService.getStorageArea(updateReqVO.getAreaId());
            updateReqVO.setAreaCode(area.getAreaCode());
            updateReqVO.setAreaName(area.getAreaName());
        }
        issueHeaderService.updateIssueHeader(updateReqVO);
        return success(true);
    }

    @PutMapping("/updateMachinery")
    @Operation(summary = "更新生产领料单头")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:update')")
    public CommonResult<Boolean> updateMachinery(@Valid @RequestBody IssueHeaderUpdateReqVO updateReqVO) {

        WorkorderDTO workorderDO = workorderApi.getWorkorder(updateReqVO.getWorkorderCode());
        if ("FINISHED".equals(workorderDO.getStatus())) {
            return error(ErrorCodeConstants.WORKORDER_FINSHED_NOT_AVALIABLE);
        }

        // 根据设备编码获取设备信息, 并更新当前的领料单头
        String machineryCode = updateReqVO.getMachineryCode();
        DvMachineryDTO machineryDTO = dvMachineryApi.getMachineryInfo(machineryCode);
        updateReqVO.setMachineryId(machineryDTO.getId());
        updateReqVO.setMachineryName(machineryDTO.getMachineryName());
        issueHeaderService.updateIssueHeader(updateReqVO);
        return success(true);
    }

    /**
     * 执行出库
     * @return
     */
    @PreAuthorize("@ss.hasPermission('wms:issue-header:update')")
    @Transactional
    @PutMapping("/{issueId}")
    @Operation(summary = "执行出库")
    public CommonResult execute(@PathVariable Long issueId) {
        IssueHeaderDO header = issueHeaderService.getIssueHeader(issueId);

        WorkorderDTO workorderDO = workorderApi.getWorkorder(header.getWorkorderCode());
        if ("FINISHED".equals(workorderDO.getStatus())) {
            return error(ErrorCodeConstants.WORKORDER_FINSHED_NOT_AVALIABLE);
        }

        // 2025-1-17追加校验
        // 若当前单身存在已上料且报工单号为空, 禁止执行出库
        IssueLineListVO param = new IssueLineListVO();

        param.setIssueId(issueId);
        param.setStatus("Y");
        param.setFeedbackStatus("N");
        List<IssueLineDO> feedBacklines = issueLineService.selectList(param); // 当前领料单下已上料未报工的单身信息
        param.setStatus("N");
        param.setFeedbackStatus("N");

        List<IssueLineDO> noFeedBacklines = issueLineService.selectList(param); // 当前领料单下未上料未报工的单身信息
        if (!noFeedBacklines.isEmpty() && !feedBacklines.isEmpty()) {
            for (IssueLineDO noFeedBackline : noFeedBacklines) {
                for (IssueLineDO feedBackline : feedBacklines) {
                    if (noFeedBackline.getMachineryCode().equals(feedBackline.getMachineryCode())) {
                        // 两者中存在相同机台设备, 禁止出库
                        return error(ErrorCodeConstants.ISSUE_HEADER_NOT_FEEDBACK_EXISTS);
                    }
                }
            }
        } else if (!feedBacklines.isEmpty()) {
            return error(ErrorCodeConstants.ISSUE_HEADER_NOT_FEEDBACK_EXISTS);
        }
        param.setStatus("N");// 防止重复上料
        List<IssueLineDO> lines = issueLineService.selectList(param); // 当前领料单身中为上料的单据信息
        if (lines.isEmpty()) {
            return error(ErrorCodeConstants.ISSUE_HEADER_NEED_LINE);
        }

        // 构建ERP接口参数
        Map<String, Object> erpParams = new HashMap<>();
        List<Map<String, Object>> goodsList = new ArrayList<>();

        if (!workorderDO.getWorkorderCode().startsWith("MO")) {
            // ERP接口调用标识
            List<IssueLineDO> erpEnableList = new ArrayList<>();
            for (IssueLineDO line : lines) {
                Long sequence = line.getSequence();
                Long sequenceOrder = line.getSequenceOrder();
                if (sequence == null || sequenceOrder == null) {
                    continue;
                }

                // 获取BOM信息
                WorkorderBomDTO bom = workorderApi.getWorkorderBom(header.getWorkorderId()).stream()
                        .filter(dto -> dto.getItemCode().equals(line.getItemCode()) && dto.getSequence().equals(sequence) && dto.getSequenceOrder().equals(sequenceOrder))
                        .findFirst()
                        .orElse(null);
                if (bom == null) {
                    continue;
                }

                // 单位换算逻辑
                String bomUnit = bom.getUnitOfMeasure(); // BOM单位
                String lineUnit = line.getUnitOfMeasure(); // 领料单行单位
                BigDecimal convertedQuantity = line.getQuantityIssued(); // 初始化为原始数量

                // 如果单位不同，进行换算
                if (!bomUnit.equals(lineUnit)) {
                    if ("吨".equals(bomUnit) && "公斤".equals(lineUnit)) {
                        convertedQuantity = line.getQuantityIssued().divide(new BigDecimal(1000), 4, RoundingMode.HALF_UP);
                    }else if ("公斤".equals(bomUnit) && "吨".equals(lineUnit)) {
                        convertedQuantity = line.getQuantityIssued().multiply(new BigDecimal(1000));
                    }else if ("公斤".equals(bomUnit) && "米".equals(lineUnit)) {
                        // convertedQuantity = line.getQuantityIssued().multiply(new BigDecimal(1000));
                        // 2025-11-7 修改剥离返工单数量获取
                        // 判定当前领用行批次是否为TASK开头, 且当前的报工单存在转换数量
                        if (line.getBatchCode().startsWith("TASK")) {
                            FeedbackDTO feedbackDTO = feedbackApi.getFeedBackByBatchCode(line.getBatchCode());
                            if(feedbackDTO != null && feedbackDTO.getMachineryCode().contains("AMSB-BF")){
                                Double qualifiedQuantity = Optional.ofNullable( feedbackDTO.getQuantityQualified()).orElse(0.0);
                                BigDecimal changeQuantity = Optional.ofNullable( feedbackDTO.getConversionQuantity()).orElse(BigDecimal.ZERO);
                                BigDecimal rate = BigDecimal.ZERO;
                                // 将当前的行的领用数量与报工合格数做比对, 判定占比
                                if (qualifiedQuantity > 0) {
                                    rate = line.getQuantityIssued().divide(new BigDecimal(qualifiedQuantity), 4, RoundingMode.HALF_UP);
                                }
                                // 将当前比率再次乘转换数量, 得出实际的转换数量占比
                                convertedQuantity = changeQuantity.multiply(rate).setScale(4, RoundingMode.HALF_UP);
                            }
                        }
                    }
                }

                // 判定当前领料单, 使用物料是否超标
                // 开始判定当前领料单下改物料的所有用量
                IssueLineExportReqVO lineExportReqVO = new IssueLineExportReqVO();
                lineExportReqVO.setIssueId(header.getId());
                lineExportReqVO.setItemCode(line.getItemCode());
                lineExportReqVO.setSequence(line.getSequence()); // 卡控项次与项序
                lineExportReqVO.setSequenceOrder(line.getSequenceOrder());
                lineExportReqVO.setStatus("Y"); // 已上料
                lineExportReqVO.setErpEnable("Y"); // 已同步ERP
                List<IssueLineDO> lineDOList = issueLineService.getIssueLineList(lineExportReqVO);

                // 循环lineDOlist, 获取每一行数据的使用量并进行汇总
                BigDecimal totalUsed = new BigDecimal(0);
                for (IssueLineDO lineDO : lineDOList) {
                    // 对已领用量也进行单位换算
                    BigDecimal issuedQty = lineDO.getQuantityIssued();
                    if (!bomUnit.equals(lineDO.getUnitOfMeasure())) {
                        if ("吨".equals(bomUnit) && "公斤".equals(lineDO.getUnitOfMeasure())) {
                            issuedQty = lineDO.getQuantityIssued().divide(new BigDecimal(1000), 4, RoundingMode.HALF_UP);
                        } else if ("公斤".equals(bomUnit) && "吨".equals(lineDO.getUnitOfMeasure())) {
                            issuedQty = lineDO.getQuantityIssued().multiply(new BigDecimal(1000));
                        }else if ("公斤".equals(bomUnit) && "米".equals(lineUnit)) {
                            // 2025-11-7 修改剥离返工单数量获取
                            if (lineDO.getBatchCode().startsWith("TASK")) {
                                FeedbackDTO feedbackDTO = feedbackApi.getFeedBackByBatchCode(lineDO.getBatchCode());
                                if(feedbackDTO != null  && feedbackDTO.getMachineryCode().contains("AMSB-BF")){
                                    Double qualifiedQuantity = Optional.ofNullable( feedbackDTO.getQuantityQualified()).orElse(0.0);
                                    BigDecimal changeQuantity = Optional.ofNullable( feedbackDTO.getConversionQuantity()).orElse(BigDecimal.ZERO);
                                    BigDecimal rate = BigDecimal.ZERO;
                                    if (qualifiedQuantity > 0) {
                                        rate = lineDO.getQuantityIssued().divide(new BigDecimal(qualifiedQuantity), 4, RoundingMode.HALF_UP);
                                    }
                                    issuedQty = changeQuantity.multiply(rate).setScale(4, RoundingMode.HALF_UP);
                                }
                            }
                        }
                    }
                    totalUsed = totalUsed.add(issuedQty);
                }

                BigDecimal currentQty = convertedQuantity; // 使用换算后的数量
                BigDecimal bomQty = new BigDecimal(bom.getQuantity());

                // 计算可分配数量
                BigDecimal remaining = bomQty.subtract(totalUsed);
                BigDecimal h01Qty = BigDecimal.ZERO;
                BigDecimal h02Qty = BigDecimal.ZERO;

                if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                    // 全部超领
                    h02Qty = convertedQuantity; // 使用换算后的数量
                } else if (currentQty.compareTo(remaining) <= 0) {
                    // 全部成套领料
                    h01Qty = convertedQuantity; // 使用换算后的数量
                } else {
                    // 拆分领料
                    h01Qty = remaining;
                    h02Qty = currentQty.subtract(remaining);
                }

                // 将数量转换回原始单位
                if (!bomUnit.equals(lineUnit)) {
                    if ("吨".equals(bomUnit) && "公斤".equals(lineUnit)) {
                        h01Qty = h01Qty.multiply(new BigDecimal(1000));
                        h02Qty = h02Qty.multiply(new BigDecimal(1000));
                    } else if ("公斤".equals(bomUnit) && "吨".equals(lineUnit)) {
                        h01Qty = h01Qty.divide(new BigDecimal(1000), 4, RoundingMode.HALF_UP);
                        h02Qty = h02Qty.divide(new BigDecimal(1000), 4, RoundingMode.HALF_UP);
                    }
                }

                // 配置4位小数
                h01Qty = h01Qty.setScale(4, RoundingMode.HALF_UP);
                h02Qty = h02Qty.setScale(4, RoundingMode.HALF_UP);

                // 生成H01报文
                if (h01Qty.compareTo(BigDecimal.ZERO) > 0) {
                    Map<String, Object> item = buildErpItem(header, line, "H01", h01Qty);
                    if (item != null) {
                        goodsList.add(item);
                        erpEnableList.add(line);
                    }
                }

                // 生成H02报文
                if (h02Qty.compareTo(BigDecimal.ZERO) > 0) {
                    Map<String, Object> item = buildErpItem(header, line, "H02", h02Qty);
                    if (item != null) {
                        goodsList.add(item);
                        erpEnableList.add(line);
                    }
                }
            }

            // 按领料类型分组（H01/H02）
            Map<String, List<Map<String, Object>>> groupedData = goodsList.stream()
                    .collect(Collectors.groupingBy(item -> (String) item.get("sfdc015")));

            // 统一ERP参数基础配置
            erpParams.put("sfda002", "11");  // 保持原值，根据实际情况调整
            erpParams.put("source_no", header.getIssueCode());

            Map<Long, IssueLineDO> lineMap = lines.stream().collect(Collectors.toMap(IssueLineDO::getId, Function.identity()));

            // 遍历分组进行接口调用
            for (Map.Entry<String, List<Map<String, Object>>> entry : groupedData.entrySet()) {
                // 设置当前分组的领料数据
                erpParams.put("goodsList", entry.getValue());
                // 调用ERP接口
                List<Map<String, Object>> list = entry.getValue();
                String erpResult = workorderERPAPI.workOrderIssueCreate(erpParams);
                // String erpResult ="ERROR";
                if (erpResult.contains("SUCCESS")) {
                    // 接口调用成功, 追加ERP同步标识
                    List<IssueLineDO> lineDOList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        Number lineId = (Number) map.get("issueLineId");
                        IssueLineDO line = lineMap.get(lineId.longValue());
                        if (line != null) {
                            line.setErpEnable("Y");
                            issueLineService.updateIssueLine(IssueLineConvert.INSTANCE.convert01(line));
                        }
                    }
                }
            }
        }

        // 追加上料详情
        List<FeedLineDO> createReqVOList = new ArrayList<>();
        for (IssueLineDO issueLine : lines) {
            TaskDTO task = taskApi.getTask(header.getTaskId());
            FeedLineDO lineDO = new FeedLineDO();
            lineDO.setTaskCode(header.getTaskCode());
            lineDO.setTaskName(task.getTaskName());
            lineDO.setAreaName(issueLine.getAreaName());
            lineDO.setAreaCode(issueLine.getAreaCode());
            lineDO.setAreaId(issueLine.getAreaId());
            lineDO.setLocationCode(issueLine.getLocationCode());
            lineDO.setLocationName(issueLine.getLocationName());
            lineDO.setLocationId(issueLine.getLocationId());
            lineDO.setWarehouseCode(issueLine.getWarehouseCode());
            lineDO.setWarehouseName(issueLine.getWarehouseName());
            lineDO.setWarehouseId(issueLine.getWarehouseId());
            lineDO.setIssueId(issueId);
            lineDO.setMaterialStockId(issueLine.getMaterialStockId());
            lineDO.setItemId(issueLine.getItemId());
            lineDO.setItemCode(issueLine.getItemCode());
            lineDO.setItemName(issueLine.getItemName());
            lineDO.setSpecification(issueLine.getSpecification());
            lineDO.setUnitOfMeasure(issueLine.getUnitOfMeasure());
            lineDO.setQuantity(issueLine.getQuantityIssued().doubleValue());
            lineDO.setBatchCode(issueLine.getBatchCode());
            lineDO.setWorkorderCode(header.getWorkorderCode());
            lineDO.setWorkstationCode(header.getWorkstationCode());
            lineDO.setWorkstationName(header.getWorkstationName());
            lineDO.setVendorCode(issueLine.getVendorCode());
            lineDO.setStatus("Y");
            lineDO.setFeedbackStatus("N"); // 追加报工状态
            lineDO.setMachineryCode(issueLine.getMachineryCode());
            lineDO.setMachineryName(issueLine.getMachineryName());
            lineDO.setMachineryId(issueLine.getMachineryId());
            lineDO.setBarcodeNumber(issueLine.getBarcodeNumber());
            // 2025-04-03: 追加ERP项次, 项序用于领料接口
            lineDO.setSequence(issueLine.getSequence());
            lineDO.setSequenceOrder(issueLine.getSequenceOrder());
            // 2025-04-25: 追加ERP母批次
            lineDO.setErpBatchCode(issueLine.getErpBatchCode());
            // 2025-06-08 追加母批次
            lineDO.setParentBatchCode(issueLine.getParentBatchCode());
            createReqVOList.add(lineDO);
        }
        feedLineService.insertBatch(createReqVOList);

        List<IssueTxBean> beans = issueHeaderService.getTxBeans(issueId);

        //调用库存核心
        storageCoreServicel.processIssue(beans);

        //更新单据状态
        header.setStatus(Constant.ORDER_STATUS_CONFIRMED); // 先修改为已确认, 当报工后将领料单转为完成
        IssueHeaderUpdateReqVO updateReqVO = IssueHeaderConvert.INSTANCE.convert01(header);
        issueHeaderService.updateIssueHeader(updateReqVO);

        // 上料后库存进入线边仓, 将领料单单身物料位置同步到虚拟线边仓
        WarehouseDO warehouse = warehouseService.selectWmWarehouseByWarehouseCode(Constant.VIRTUAL_WH);
        StorageLocationDO location = storageLocationService.selectWmStorageLocationByLocationCode(Constant.VIRTUAL_WS);
        StorageAreaDO area = storageAreaService.selectWmStorageAreaByAreaCode(Constant.VIRTUAL_WA);

        // 将当前单身状态改为已领料
        for (IssueLineDO line : lines) {
            line.setStatus("Y"); // 已领料
            line.setWarehouseId(warehouse.getId());
            line.setWarehouseCode(warehouse.getWarehouseCode());
            line.setWarehouseName(warehouse.getWarehouseName());
            line.setLocationId(location.getId());
            line.setLocationCode(location.getLocationCode());
            line.setLocationName(location.getLocationName());
            line.setAreaId(area.getId());
            line.setAreaCode(area.getAreaCode());
            line.setAreaName(area.getAreaName());
        }

        // 将对应任务单状态改为生产中
        TaskDTO taskDO = taskApi.getTask(header.getTaskCode());
        taskDO.setStatus("STARTED");
        taskApi.updateTask(taskDO);

        issuLineMapper.updateBatch(lines);
        return success(true);
    }

    private Map<String, Object> buildErpItem(IssueHeaderDO header, IssueLineDO line, String reasonCode, BigDecimal qty) {
        // 前置校验批次号
        String batchCode;
        MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
        exportReqVO.setItemCode(line.getItemCode());
        exportReqVO.setBatchCode(line.getBatchCode());

        exportReqVO.setRecptStatus("Y");
        //MaterialStockDO stockDO = materialStockService.getMaterialStockList(exportReqVO) == null ? null : materialStockService.getMaterialStockList(exportReqVO).get(0);
        List<MaterialStockDO> stockDOList = materialStockService.getMaterialStockListContainZero(exportReqVO) == null ? null : materialStockService.getMaterialStockListContainZero(exportReqVO);
        if (stockDOList.isEmpty()) {
            return null;
        }
        stockDOList.sort(Comparator.comparing(MaterialStockDO::getCreateTime));
        MaterialStockDO stockDO = stockDOList.get(0);

        if (stockDO.getCreateTime().isBefore(BATCH_CODE_SWITCH_DATE)) {
            batchCode = stockDO.getErpBatchCode();
            // 关键校验：当需要erpBatchCode但为空时返回null
            if (batchCode == null) {
                System.out.println("ERP批次号缺失 | 领料单行ID：" + line.getId());
                return null;
            }
        } else {
            // 2025-06-08 修改为母批
            // batchCode = line.getBatchCode();
            batchCode = line.getParentBatchCode();
            if (batchCode == null) {
                System.out.println("批次号缺失 | 领料单行ID：" + line.getId());
                return null;
            }
        }

        // 2025-9-4 追加返工单领料需求, 当前分切工序传递给ERP为公斤, 故调拨时判定是否为产成品 => 判定是否存在转换单位与转换数量
        // 现根据当前line对于的领料数量与qty进行比对, 判定占比
        BigDecimal rate = qty.divide(line.getQuantityIssued(), 4, BigDecimal.ROUND_HALF_UP);
        // 默认使用传递的对应数量与单位
        BigDecimal quantityIssued = qty;
        String unit = line.getUnitOfMeasure();

        // 判定转一致单行, 但保留单位获取
        if(line.getBatchCode().startsWith("TASK")){
            FeedbackDTO feedbackDTO = feedbackApi.getFeedBackByBatchCode(line.getBatchCode());
            if(feedbackDTO != null){
                unit = Optional.ofNullable( feedbackDTO.getConversionUnit()).orElse(line.getUnitOfMeasure());
                /*quantityIssued =  Optional.ofNullable( feedbackDTO.getConversionQuantity()).orElse(qty);
                if(feedbackDTO.getConversionQuantity() != null){
                    // 考虑当前占比
                    quantityIssued = quantityIssued.multiply(rate);
                }*/
            }
        }

        Map<String, Object> item = new HashMap<>();
        item.put("sfdc001", header.getWorkorderCode()); // 工单号
        item.put("sfdc002", line.getSequence());        // 项次
        item.put("sfdc003", line.getSequenceOrder());   // 项序

        /*item.put("sfdc007", qty);                       // 数量
        item.put("typeud001", line.getUnitOfMeasure()); // 追加单位*/

        item.put("sfdc007", quantityIssued.setScale(4, RoundingMode.HALF_UP));                       // 数量
        item.put("typeud001", unit); // 单位

        item.put("sfdc012", line.getLocationCode());    // 库区
        item.put("sfdc013", line.getAreaCode());        // 库位
        item.put("sfdc014", batchCode);                 // 批次
        item.put("sfdc015", reasonCode);                // 理由码
        item.put("source_seq", "");   // MES项次
        item.put("issueLineId", line.getId());

        return item;
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产领料单头")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wms:issue-header:delete')")
    public CommonResult<Boolean> deleteIssueHeader(@RequestParam("id") Long id) {
        IssueHeaderDO header = issueHeaderService.getIssueHeader(id);
        if (!Constant.ORDER_STATUS_PREPARE.equals(header.getStatus())) {
            return error(ErrorCodeConstants.ISSUE_HEADER_NOT_DELETED);
        }
        issueLineService.deleteByIssueHeaderId(id);
        issueHeaderService.deleteIssueHeader(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得生产领料单头")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:query')")
    public CommonResult<IssueHeaderRespVO> getIssueHeader(@RequestParam("id") Long id) {
        IssueHeaderDO issueHeader = issueHeaderService.getIssueHeader(id);
        // 基于当前工单信息带出工单BOM
        // 工单BOM当前只允许在ERP修改, 故前端页面禁止变更
        List<Map<String, Object>> bomList = issueHeaderService.initBomByWorkOrder(issueHeader.getWorkorderCode());
        //issueHeader.setBomList(bomList);
        IssueHeaderRespVO respVO = IssueHeaderConvert.INSTANCE.convert(issueHeader);
        //respVO.setBomList(bomList);
        return success(respVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得生产领料单头列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:query')")
    public CommonResult<List<IssueHeaderRespVO>> getIssueHeaderList(@RequestParam("ids") Collection<Long> ids) {
        List<IssueHeaderDO> list = issueHeaderService.getIssueHeaderList(ids);
        return success(IssueHeaderConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产领料单头分页")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:query')")
    public CommonResult<PageResult<IssueHeaderRespVO>> getIssueHeaderPage(@Valid IssueHeaderPageReqVO pageVO) {
        PageResult<IssueHeaderDO> pageResult = issueHeaderService.getIssueHeaderPage(pageVO);
        return success(IssueHeaderConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出生产领料单头 Excel")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:export')")
    @OperateLog(type = EXPORT)
    public void exportIssueHeaderExcel(@Valid IssueHeaderExportReqVO exportReqVO,
                                       HttpServletResponse response) throws IOException {
        List<IssueHeaderDO> list = issueHeaderService.getIssueHeaderList(exportReqVO);
        // 导出 Excel
        List<IssueHeaderExcelVO> datas = IssueHeaderConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "生产领料单头.xls", "数据", IssueHeaderExcelVO.class, datas);
    }


    /**
     * 完成领料
     *
     * @param allocatedId
     * @return
     */
    @GetMapping("/finsh")
    @Operation(summary = "更新调拨单头")
    @PreAuthorize("@ss.hasPermission('wms:allocated-header:update')")
    public CommonResult<Boolean> finshIssueHeader(@RequestParam("id") Long allocatedId) {
        IssueHeaderDO issueHeader = issueHeaderService.getIssueHeader(allocatedId);
        if (!issueHeader.getStatus().equals(Constant.ORDER_STATUS_CONFIRMED)) {
            return error(ErrorCodeConstants.ISSUE_HEADER_NO_ISSUE);
        }
        issueHeader.setStatus(Constant.ORDER_STATUS_FINISHED);
        issueHeaderService.updateIssueHeader(IssueHeaderConvert.INSTANCE.convert01(issueHeader));
        return success(true);
    }

    @GetMapping("/traceIssueHeaderPage")
    @Operation(summary = "获得生产领料单头分页")
    @PreAuthorize("@ss.hasPermission('wms:issue-header:query')")
    public CommonResult<PageResult<IssueHeaderRespVO>> traceIssueHeaderPage(@Valid IssueHeaderPageReqVO pageVO) {
        String workorderCode = pageVO.getWorkorderCode();
        String taskCode = pageVO.getTaskCode();
        String batchCode = pageVO.getBatchCode();

        if (workorderCode == null && taskCode == null && batchCode == null) {
            return success();
        }

        Set<String> issueCodeSet = new HashSet<>();

        if (workorderCode != null && !"".equals(workorderCode)) {
            // 基于工单获取所有领料单
            List<IssueHeaderDO> issueHeaderDOList = issueHeaderService.getIssueHeaderList(new IssueHeaderExportReqVO().setWorkorderCode(workorderCode));
            if (!issueHeaderDOList.isEmpty()) {
                for (IssueHeaderDO issueHeaderDO : issueHeaderDOList) {
                    issueCodeSet.add(issueHeaderDO.getIssueCode());
                }
            }
        }

        if (taskCode != null && !"".equals(taskCode)) {
            // 基于任务单获取领料单
            List<IssueHeaderDO> issueHeaderDOList = issueHeaderService.getIssueHeaderList(new IssueHeaderExportReqVO().setTaskCode(taskCode));
            if (!issueHeaderDOList.isEmpty()) {
                for (IssueHeaderDO issueHeaderDO : issueHeaderDOList) {
                    issueCodeSet.add(issueHeaderDO.getIssueCode());
                }
            }
        }

        if (batchCode != null && !"".equals(batchCode)) {
            List<IssueLineDO> issueLineDOList = issueLineService.getIssueLineList(new IssueLineExportReqVO().setBatchCode(batchCode));
            if (!issueLineDOList.isEmpty()) {
                for (IssueLineDO issueLineDO : issueLineDOList) {
                    IssueHeaderDO issueHeaderDO = Optional.ofNullable(issueHeaderService.getIssueHeader(issueLineDO.getIssueId())).orElse(null);
                    if (issueHeaderDO != null) {
                        issueCodeSet.add(issueHeaderDO.getIssueCode());
                    }
                }
            }
        }

        if (issueCodeSet.isEmpty()) {
            return success();
        }

        List<String> issueHeaderDOList = new ArrayList<>(issueCodeSet);
        pageVO.setIssueCodeList(issueHeaderDOList);
        pageVO.setWorkorderCode(null);
        pageVO.setTaskCode(null);
        pageVO.setBatchCode(null);
        PageResult<IssueHeaderDO> pageResult = issueHeaderService.getIssueHeaderPage(pageVO);
        return success(IssueHeaderConvert.INSTANCE.convertPage(pageResult));
    }


    /**
     * 获取任务单状态
     *
     * @param
     * @return
     */
    @GetMapping("/initTaskInfoByIssueId")
    @Operation(summary = "更新调拨单头")
    @PreAuthorize("@ss.hasPermission('wms:allocated-header:update')")
    public CommonResult<Map<String, Object>> initTaskInfoByIssueId(@RequestParam("id") Long issueId) {
        IssueHeaderDO issueHeader = issueHeaderService.getIssueHeader(issueId);
        TaskDTO task = taskApi.getTask(issueHeader.getTaskCode());
        Map<String, Object> map = new HashMap<>();
        map.put("issueCode", issueHeader.getIssueCode());
        map.put("issueName", issueHeader.getIssueName());
        map.put("id", task.getId());
        map.put("taskCode", issueHeader.getTaskCode());
        map.put("taskName", task.getTaskName());
        map.put("taskStatus", task.getStatus());
        map.put("quantity", task.getQuantity());
        map.put("quantityProduced", 0);
        map.put("produced", task.getQuantityProduced());
        return success(map);
    }

}


