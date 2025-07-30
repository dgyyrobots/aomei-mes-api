package com.dofast.module.wms.controller.admin.rtissue;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.util.StringUtils;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.api.FeedbackApi.FeedbackApi;
import com.dofast.module.pro.api.FeedbackApi.dto.FeedbackDTO;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.pro.api.WorkorderApi.WorkorderApi;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderBomDTO;
import com.dofast.module.wms.api.ERPApi.WorkorderERPAPI;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineExportReqVO;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockExportReqVO;
import com.dofast.module.wms.controller.admin.rtissueline.vo.RtIssueLineCreateReqVO;
import com.dofast.module.wms.controller.admin.rtissueline.vo.RtIssueLineExportReqVO;
import com.dofast.module.wms.controller.admin.rtissueline.vo.RtIssueLineListVO;
import com.dofast.module.wms.convert.issueline.IssueLineConvert;
import com.dofast.module.wms.convert.rtissueline.RtIssueLineConvert;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.dal.dataobject.rtissue.RtIssueTxBean;
import com.dofast.module.wms.dal.dataobject.rtissueline.RtIssueLineDO;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.enums.ErrorCodeConstants;
import com.dofast.module.wms.service.feedline.FeedLineService;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.issueline.IssueLineService;
import com.dofast.module.wms.service.materialstock.MaterialStockService;
import com.dofast.module.wms.service.rtissueline.RtIssueLineService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagecore.StorageCoreService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.poi.sl.usermodel.Line;
import org.redisson.executor.TasksService;
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
import java.beans.beancontext.BeanContext;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.common.pojo.UserConstants.BATCH_CODE_SWITCH_DATE;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.wms.controller.admin.rtissue.vo.*;
import com.dofast.module.wms.dal.dataobject.rtissue.RtIssueDO;
import com.dofast.module.wms.convert.rtissue.RtIssueConvert;
import com.dofast.module.wms.service.rtissue.RtIssueService;

@Tag(name = "仓储管理 - 生产退料单头")
@RestController
@RequestMapping("/mes/wms/rt-issue")
@Validated
public class RtIssueController {

    @Resource
    private RtIssueService rtIssueService;

    @Resource
    private RtIssueLineService rtIssueLineService;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Resource
    private StorageCoreService storageCoreService;

    @Resource
    private MaterialStockService materialStockService;

    @Resource
    private TaskApi taskApi;

    @Resource
    private FeedLineService feedLineService;

    @Resource
    private FeedbackApi feedBackApi;

    @Resource
    private IssueLineService issuseLineService;

    @Resource
    private IssueHeaderService issueHeaderService;

    @Resource
    private WorkorderERPAPI workorderERPAPI;

    @Resource
    private WorkorderApi workorderApi;


    @PostMapping("/create")
    @Operation(summary = "创建生产退料单头")
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:create')")
    public CommonResult<Long> createRtIssue(@Valid @RequestBody RtIssueCreateReqVO createReqVO) {
        if(Constant.NOT_UNIQUE.equals(rtIssueService.checkUnique(createReqVO))){
            return error(ErrorCodeConstants.RT_ISSUE_CODE_EXISTS);
        }
        // 校验当前的任务单是否已报工
        FeedbackDTO feedbackInfo = feedBackApi.getFeedBackByTaskCode(createReqVO.getTaskCode());
       /* if(feedbackInfo != null){
            return error(ErrorCodeConstants.RT_ISSUE_HAS_FEEDBACK);
        }*/

        WarehouseDO warehouse = null;
        StorageLocationDO location = null;
        StorageAreaDO area = null;
        if(StrUtils.isNotNull(createReqVO.getWarehouseId())){
            warehouse =  warehouseService.getWarehouse(createReqVO.getWarehouseId());
            createReqVO.setWarehouseCode(warehouse.getWarehouseCode());
            createReqVO.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StrUtils.isNotNull(createReqVO.getLocationId())){
            location = storageLocationService.getStorageLocation(createReqVO.getLocationId());
            createReqVO.setLocationCode(location.getLocationCode());
            createReqVO.setLocationName(location.getLocationName());
        }
        if(StrUtils.isNotNull(createReqVO.getAreaId())){
            area = storageAreaService.getStorageArea(createReqVO.getAreaId());
            createReqVO.setAreaCode(area.getAreaCode());
            createReqVO.setAreaName(area.getAreaName());
        }
        Long rtissueId = rtIssueService.createRtIssue(createReqVO);
        List<Map<String, Object>> lines = createReqVO.getRtissuelineList();
        for(Map<String, Object> line : lines){
            //追加单身
            RtIssueLineCreateReqVO req = new RtIssueLineCreateReqVO();
            // 追加库存
            req.setWarehouseId(createReqVO.getWarehouseId());
            req.setWarehouseCode(warehouse.getWarehouseCode());
            req.setWarehouseName(warehouse.getWarehouseName());
            req.setLocationId(createReqVO.getLocationId());
            req.setLocationCode(location.getLocationCode());
            req.setLocationName(location.getLocationName());
            req.setAreaId(createReqVO.getAreaId());
            req.setAreaCode(area.getAreaCode());
            req.setAreaName(area.getAreaName());
            req.setRtId(rtissueId);
            req.setBatchCode((String) line.get("batchCode"));
            // 2025-06-08 追加母批次
            req.setParentBatchCode((String) line.get("parentBatchCode"));
            req.setItemCode((String) line.get("itemCode"));
            req.setItemName((String) line.get("itemName"));
            req.setSpecification((String) line.get("specification"));
            Integer itemId = (Integer) line.get("itemId");
            req.setItemId(itemId.longValue());
            req.setUnitOfMeasure((String) line.get("unitOfMeasure"));
            // 基于当前的物料与批次号获取库存Id
            MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
            exportReqVO.setBatchCode((String) line.get("batchCode"));
            exportReqVO.setItemCode((String) line.get("itemCode"));
            List<MaterialStockDO> materialStockList = materialStockService.getMaterialStockListContainZero(exportReqVO);
            if(materialStockList.isEmpty()){
                continue;
            }
            req.setMaterialStockId(materialStockList.get(0).getId());
            BigDecimal quantity = new BigDecimal(String.valueOf(line.get("quantity")));
            req.setQuantityRt(quantity);
            // 追加ERP项次, 项序用于接口回传
            Integer sequence = Optional.ofNullable((Integer) line.get("sequence")).orElse(0);
            req.setSequence(sequence.longValue());
            Integer setSequenceOrder = Optional.ofNullable((Integer) line.get("sequenceOrder")).orElse(0);
            req.setSequenceOrder(setSequenceOrder.longValue());
            rtIssueLineService.createRtIssueLine(req);
        }
        // 追加退料单身信息
        return success();
    }

    /**
     * 执行退料
     * @param rtId
     * @return
     */
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:update')")
    @Transactional
    @PutMapping("/{rtId}")
    public CommonResult execute(@PathVariable Long rtId){
        RtIssueDO rtIssue = rtIssueService.getRtIssue(rtId);
        // 校验当前的任务单是否已报工
        /*FeedbackDTO feedbackInfo = feedBackApi.getFeedBackByTaskCode(rtIssue.getTaskCode());
        if(feedbackInfo != null){
            return error(ErrorCodeConstants.RT_ISSUE_HAS_FEEDBACK);
        }*/
        RtIssueLineListVO param = new RtIssueLineListVO();
        param.setRtId(rtId);
        List<RtIssueLineDO> lines = rtIssueLineService.selectList(param);
        if(CollUtil.isEmpty(lines)){
            return error(ErrorCodeConstants.RT_ISSUE_NEED_MAT);
        }

        // 追加ERP接口调用
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        // 获取工单BOM信息
        List<WorkorderBomDTO> bomList = workorderApi.getWorkorderBom(rtIssue.getWorkorderId());

        // 按物料维度分组处理
        Map<String, List<RtIssueLineDO>> materialGroups = lines.stream()
                .filter(line -> line.getSequence() != null && line.getSequenceOrder() != null)
                .collect(Collectors.groupingBy(RtIssueLineDO::getItemCode));

        // ERP接口调用标识
        List<Map<String, Object>> erpEnableList = new ArrayList<>();

        // 处理每个物料批次组
        for (Map.Entry<String, List<RtIssueLineDO>> entry : materialGroups.entrySet()) {
            String key = entry.getKey();
            List<RtIssueLineDO> rtLines = entry.getValue();

            // 获取首行基本信息
            RtIssueLineDO sampleLine = rtLines.get(0);
            // 获取工单BOM应发量
            BigDecimal bomQty = BigDecimal.ZERO;
            for (WorkorderBomDTO bom : bomList) {
                if (bom.getSequence().equals(sampleLine.getSequence()) && bom.getSequenceOrder().equals(sampleLine.getSequenceOrder())) {
                    bomQty = new BigDecimal(bom.getQuantity());
                    break;
                }
            }
            // 获取历史已退量
            // 获取当前已完成的退料单信息
            RtIssueExportReqVO usedRtIssue = new RtIssueExportReqVO();
            usedRtIssue.setTaskCode(rtIssue.getTaskCode());
            usedRtIssue.setWorkorderCode(rtIssue.getAreaCode());
            usedRtIssue.setStatus("FINISHED"); // 仅获取已完成物料
            List<RtIssueDO> usedRtIssueDOList = rtIssueService.getRtIssueList(usedRtIssue);

            BigDecimal totalReturned = BigDecimal.ZERO;
            for (RtIssueDO usedRtIssueHeader : usedRtIssueDOList) {
                RtIssueLineExportReqVO usedLineExportReqVO = new RtIssueLineExportReqVO();
                usedLineExportReqVO.setItemCode(sampleLine.getItemCode());
                //usedLineExportReqVO.setBatchCode(sampleLine.getBatchCode());
                usedLineExportReqVO.setRtId(usedRtIssueHeader.getId());
                usedLineExportReqVO.setSequence(sampleLine.getSequence());
                usedLineExportReqVO.setSequenceOrder(sampleLine.getSequenceOrder());
                List<RtIssueLineDO> rtIssueLine = rtIssueLineService.getRtIssueLineList(usedLineExportReqVO) == null ? new ArrayList<>() : rtIssueLineService.getRtIssueLineList(usedLineExportReqVO);

                if (!rtIssueLine.isEmpty()) {
                    for (RtIssueLineDO rt : rtIssueLine) {
                        totalReturned = totalReturned.add(rt.getQuantityRt());
                    }
                }
            }

            // 计算当前物料待退总量
            BigDecimal currentTotal = BigDecimal.ZERO;
            for (RtIssueLineDO line : rtLines) {
                currentTotal = currentTotal.add(line.getQuantityRt());
            }

            // 计算可退量分配
            BigDecimal remaining = bomQty.subtract(totalReturned);
            BigDecimal y01Qty = BigDecimal.ZERO;
            BigDecimal y02Qty = BigDecimal.ZERO;

            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                // 全部超退
                y02Qty = currentTotal;
            } else if (currentTotal.compareTo(remaining) <= 0) {
                // 全部正常退
                y01Qty = currentTotal;
            } else {
                // 拆分退料
                y01Qty = remaining;
                y02Qty = currentTotal.subtract(remaining);
            }

            // 生成H01报文
            if (y01Qty.compareTo(BigDecimal.ZERO) > 0) {
                Map<String, Object> item = buildErpItem(rtIssue, sampleLine, "Y01", y01Qty);
                if (item != null) {
                    list.add(item);
                    erpEnableList.add(item);
                }
            }

            if (y02Qty.compareTo(BigDecimal.ZERO) > 0) {
                Map<String, Object> item = buildErpItem(rtIssue, sampleLine, "Y02", y02Qty);
                if (item != null) {
                    list.add(item);
                    erpEnableList.add(item);
                }
            }
        }
        // 按退料类型分组调用
        Map<String, List<Map<String, Object>>> typeGroups = list.stream()
                .collect(Collectors.groupingBy(item -> (String) item.get("sfdc015")));
        // 执行分组调用
        for (Map.Entry<String, List<Map<String, Object>>> entry : typeGroups.entrySet()) {
            String type = entry.getKey();
            List<Map<String, Object>> items = entry.getValue();
            Map<String, Object> erpParams = new HashMap<>();
            erpParams.put("goodsList", items);

            String sfda002Value = "Y01".equals(type) ? "21" : "22";

            erpParams.put("sfda002", sfda002Value);
            erpParams.put("source_no", rtIssue.getRtCode());

            List<Map<String, Object>> rtIssueList = entry.getValue();
            if (!items.isEmpty()) {
                String erpResult = workorderERPAPI.workOrderIssueCreate(erpParams);
                // String erpResult = "SUCCESS";
                if (erpResult.contains("SUCCESS")) {
                    for (Map<String, Object> map : rtIssueList){
                        Number lineId = (Number) map.get("rtissueId");
                        RtIssueLineDO queryLine = rtIssueLineService.getRtIssueLine(lineId.longValue());
                        queryLine.setErpStatus("Y");
                        rtIssueLineService.updateRtIssueLine(RtIssueLineConvert.INSTANCE.convert01(queryLine));
                    }
                }
            }
        }

        /*if(!erpEnableList.isEmpty()){
            // 将当前领料信息追加ERP调用标识
            for (Map<String, Object> map: erpEnableList){
                Integer id = (Integer) map.get("id");
                RtIssueLineDO lineDO = rtIssueLineService.getRtIssueLine(id.longValue());
                lineDO.setErpStatus("Y");
                rtIssueLineService.updateRtIssueLine(RtIssueLineConvert.INSTANCE.convert01(lineDO));
            }
        }*/

        List<RtIssueTxBean> beans = rtIssueService.getTxBeans(rtId);

        //执行生产退料
        storageCoreService.processRtIssue(beans, rtIssue);

        rtIssue.setStatus(Constant.ORDER_STATUS_FINISHED);

        RtIssueUpdateReqVO updateReqVO = RtIssueConvert.INSTANCE.convert01(rtIssue);
        rtIssueService.updateRtIssue(updateReqVO);

        // 修改上料记录表数据-用于后续报工数据卡控
        TaskDTO taskDTO = taskApi.getTask(rtIssue.getTaskCode());
        FeedLineExportReqVO exportReqVO = new FeedLineExportReqVO();
        exportReqVO.setTaskCode(taskDTO.getTaskCode());
        List<FeedLineDO> feedLineList = feedLineService.getFeedLineList(exportReqVO);
        // 将feedLineList与param进行对比, 若当前的物料料号相同, 则将feedLineList中对应的数量减去param中的数量
        for(FeedLineDO feedLine : feedLineList){
            for(RtIssueLineDO line : lines){
                if(feedLine.getItemCode().equals(line.getItemCode()) && feedLine.getBatchCode().equals(line.getBatchCode())){
                    BigDecimal feedQuantity = new BigDecimal(feedLine.getQuantity());
                    BigDecimal finQuantity = feedQuantity.subtract(line.getQuantityRt());
                    feedLine.setQuantity(finQuantity.doubleValue());
                }
            }
        }
        // 更新FeedLine表
        feedLineService.updateFeedLineBatch(feedLineList);

        // 修改领料单身表信息
        IssueHeaderExportReqVO issueReq = new IssueHeaderExportReqVO();
        issueReq.setTaskCode(taskDTO.getTaskCode());
        List<IssueHeaderDO> issueList = issueHeaderService.getIssueHeaderList(issueReq);
        if(CollUtil.isEmpty(issueList)){
            return error(ErrorCodeConstants.ISSUE_LINE_NOT_EXISTS);
        }
        List<IssueLineDO> issueLine = issuseLineService.getIssueLineByHeaderId(issueList.get(0).getId());
        // 操作同上料记录表, 若当前的物料料号相同, 则将issueLine中对应的数量减去param中的数量
        for(IssueLineDO issue : issueLine){
            for(RtIssueLineDO line : lines){
                if(issue.getItemCode().equals(line.getItemCode()) && issue.getBatchCode().equals(line.getBatchCode())){
                    BigDecimal issueQuantity = new BigDecimal(String.valueOf(issue.getQuantityIssued()));
                    BigDecimal finQuantity = issueQuantity.subtract(line.getQuantityRt());
                    issue.setQuantityIssued(finQuantity);
                }
            }
        }
        // 更新IssueLine表
        issuseLineService.updateIssueLineBatch(issueLine);
        return success(true);
    }

    private Map<String, Object> buildErpItem(RtIssueDO header, RtIssueLineDO line,
                                             String reasonCode, BigDecimal qty) {
        // 前置校验批次号
        String batchCode;

        MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
        exportReqVO.setItemCode(line.getItemCode());
        exportReqVO.setBatchCode(line.getBatchCode());
        // 2025-06-08 母批次
        exportReqVO.setParentBatchCode(line.getParentBatchCode());

        exportReqVO.setRecptStatus("Y");
        //MaterialStockDO stockDO = materialStockService.getMaterialStockList(exportReqVO) == null ? null : materialStockService.getMaterialStockList(exportReqVO).get(0);
        List<MaterialStockDO> stockDOList = materialStockService.getMaterialStockListContainZero(exportReqVO) == null ? null : materialStockService.getMaterialStockListContainZero(exportReqVO);
        if(stockDOList.isEmpty()){
            return null;
        }
        stockDOList.sort(Comparator.comparing(MaterialStockDO::getCreateTime));
        MaterialStockDO stockDO = stockDOList.get(0);

        if (stockDO.getCreateTime().isBefore(BATCH_CODE_SWITCH_DATE)) {
            batchCode = stockDO.getErpBatchCode();
            // 关键校验：当需要erpBatchCode但为空时返回null
            if (batchCode == null) {
                System.out.println("ERP批次号缺失 | 要退料单行ID：" +  line.getId());
                return null;
            }
        } else {
            // 2025-06-08 修改为母批
            // batchCode = line.getBatchCode();
            batchCode = stockDO.getParentBatchCode();
            if (batchCode == null) {
                System.out.println("批次号缺失 | 要退料单行ID：" +  line.getId());
                return null;
            }
        }
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("sfdc001", header.getWorkorderCode()); // 工单号
        item.put("sfdc002", line.getSequence()); // 项次
        item.put("sfdc003", line.getSequenceOrder()); // 项序
        item.put("sfdc007", qty.setScale(4, RoundingMode.HALF_UP)); // 退料数量
        item.put("sfdc012", header.getLocationCode()); // 库区
        item.put("sfdc013", header.getAreaCode()); // 库位
        item.put("sfdc014", batchCode); // 批次
        item.put("sfdc015", reasonCode); // 理由码
        item.put("sfdc016", ""); // 库存管理特征
        item.put("rtissueId", line.getId());
        item.put("typeud001" , line.getUnitOfMeasure()); // 追加单位
        return item;
    }


    @PutMapping("/update")
    @Operation(summary = "更新生产退料单头")
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:update')")
    public CommonResult<Boolean> updateRtIssue(@Valid @RequestBody RtIssueUpdateReqVO updateReqVO) {
        if(Constant.NOT_UNIQUE.equals(rtIssueService.checkUnique(updateReqVO))){
            return error(ErrorCodeConstants.RT_ISSUE_CODE_EXISTS);
        }
        List<Map<String, Object>> rtissueLine = updateReqVO.getRtissuelineList();
        if(StrUtils.isNotNull(updateReqVO.getWarehouseId())){
            WarehouseDO warehouse = warehouseService.getWarehouse(updateReqVO.getWarehouseId());
            updateReqVO.setWarehouseCode(warehouse.getWarehouseCode());
            updateReqVO.setWarehouseName(warehouse.getWarehouseName());
        }
        if(StrUtils.isNotNull(updateReqVO.getLocationId())){
            StorageLocationDO location = storageLocationService.getStorageLocation(updateReqVO.getLocationId());
            updateReqVO.setLocationCode(location.getLocationCode());
            updateReqVO.setLocationName(location.getLocationName());
        }
        if(StrUtils.isNotNull(updateReqVO.getAreaId())){
            StorageAreaDO area = storageAreaService.getStorageArea(updateReqVO.getAreaId());
            updateReqVO.setAreaCode(area.getAreaCode());
            updateReqVO.setAreaName(area.getAreaName());
        }
        rtIssueService.updateRtIssue(updateReqVO);
        // 获取当前的单身信息
        RtIssueLineDO rtIssueLineDO = new RtIssueLineDO();
        rtIssueLineDO.setRtId(updateReqVO.getId());
        List<RtIssueLineDO> rtIssueLineList = rtIssueLineService.selectList(RtIssueConvert.INSTANCE.conver02(rtIssueLineDO));
        // 将当前前台获取的RtissueLine与获取的rtIssueLineList进行对比, 若当前的物料料号相同, 则将rtIssueLineList中对应的数量更新为param中的数量
        List<RtIssueLineDO> updateRt = new ArrayList<>();
        List<RtIssueLineDO> addRt = new ArrayList<>();

        for (Map<String, Object> map : rtissueLine) {
            String itemCode = (String) map.get("itemCode");
            Integer itemId =  (Integer) map.get("itemId");
            String itemName = (String) map.get("itemName");
            String unitOfMEasuer = (String) map.get("unitOfMeasure");

            Integer warehouseId = (Integer) map.get("warehouseId");
            String warehouseName = (String) map.get("warehouseName");
            String warehouseCode = (String) map.get("warehouseCode");

            Integer locationId = (Integer) map.get("locationId");
            String locationName = (String) map.get("locationName");
            String locationCode = (String) map.get("locationCode");

            Integer areaId = (Integer) map.get("areaId");
            String areaName = (String) map.get("areaName");
            String areaCode = (String) map.get("areaCode");

            String batchCode = (String) map.get("batchCode");
            BigDecimal quantity = new BigDecimal(String.valueOf(map.get("quantity")));

            boolean found = false;
            for (RtIssueLineDO line : rtIssueLineList) {
                if (line.getItemCode().equals(itemCode) && line.getBatchCode().equals(batchCode)) {
                    // 如果找到匹配的记录，更新数量
                    line.setQuantityRt(quantity);
                    updateRt.add(line);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // 如果没有找到匹配的记录，添加新的记录
                RtIssueLineDO newLine = new RtIssueLineDO();
                newLine.setItemId(itemId.longValue());
                newLine.setItemCode(itemCode);
                newLine.setItemName(itemName);
                newLine.setRtId(updateReqVO.getId());
                newLine.setBatchCode(batchCode);
                newLine.setQuantityRt(quantity);
                newLine.setUnitOfMeasure(unitOfMEasuer);
                newLine.setWarehouseId(warehouseId.longValue());
                newLine.setWarehouseCode(warehouseCode);
                newLine.setWarehouseName(warehouseName);
                newLine.setLocationId(locationId.longValue());
                newLine.setLocationCode(locationCode);
                newLine.setLocationName(locationName);
                newLine.setAreaId(areaId.longValue());
                newLine.setAreaCode(areaCode);
                newLine.setAreaName(areaName);
                addRt.add(newLine);
            }
        }

        // 更新现有记录
        if (!updateRt.isEmpty()) {
            rtIssueLineService.updateRtIssueLineBatch(updateRt);
        }

        // 添加新记录
        if (!addRt.isEmpty()) {
            rtIssueLineService.insertRtIssueLineBatch(addRt);
        }

        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产退料单头")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:delete')")
    public CommonResult<Boolean> deleteRtIssue(@RequestParam("id") Long id) {
        rtIssueLineService.deleteByRtId(id);
        rtIssueService.deleteRtIssue(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得生产退料单头")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:query')")
    public CommonResult<RtIssueRespVO> getRtIssue(@RequestParam("id") Long id) {
        RtIssueDO rtIssue = rtIssueService.getRtIssue(id);
        RtIssueRespVO respVO = RtIssueConvert.INSTANCE.convert(rtIssue);
        // 初始化上料信息
        FeedLineExportReqVO exportReqVO = new FeedLineExportReqVO();
        exportReqVO.setTaskCode(rtIssue.getTaskCode());
        List<FeedLineDO> feedLineList = feedLineService.getFeedLineList(exportReqVO);
        // 将feedLineList转为List<Map<String, Object>
        List<Map<String, Object>> feedLineMapList = new ArrayList<>();
        for(FeedLineDO feedLine : feedLineList){
            Map<String, Object> map = new HashMap<>();
            map.put("id", feedLine.getId());
            map.put("batchCode", feedLine.getBatchCode());
            map.put("itemCode", feedLine.getItemCode());
            map.put("itemName", feedLine.getItemName());
            map.put("itemId", feedLine.getItemId());
            map.put("specification", feedLine.getSpecification());
            map.put("unitOfMeasure", feedLine.getUnitOfMeasure());
            map.put("quantity", feedLine.getQuantity());
            map.put("warehouseCode", feedLine.getWarehouseCode());
            map.put("warehouseName", feedLine.getWarehouseName());
            map.put("warehouseId", feedLine.getWarehouseId());
            map.put("locationCode", feedLine.getLocationCode());
            map.put("locationName", feedLine.getLocationName());
            map.put("locationId", feedLine.getLocationId());
            map.put("areaCode", feedLine.getAreaCode());
            map.put("areaName", feedLine.getAreaName());
            map.put("areaId", feedLine.getAreaId());
            feedLineMapList.add(map);
        }
        // 初始化上料信息
        respVO.setIssuelineList(feedLineMapList);
        // 基于单身表初始化退料信息
        RtIssueLineListVO param = new RtIssueLineListVO();
        param.setRtId(id);
        List<RtIssueLineDO> rtIssueLineList = rtIssueLineService.selectList(param);
        // 将rtIssueLineList转为List<Map<String, Object>
        List<Map<String, Object>> rtIssueLineMapList = new ArrayList<>();
        for(RtIssueLineDO rtIssueLine : rtIssueLineList){
            Map<String, Object> map = new HashMap<>();
            map.put("id", rtIssueLine.getId());
            map.put("batchCode", rtIssueLine.getBatchCode());
            map.put("itemCode", rtIssueLine.getItemCode());
            map.put("itemName", rtIssueLine.getItemName());
            map.put("itemId", rtIssueLine.getItemId());
            map.put("specification", rtIssueLine.getSpecification());
            map.put("unitOfMeasure", rtIssueLine.getUnitOfMeasure());
            map.put("quantity", rtIssueLine.getQuantityRt());
            map.put("itemId", rtIssueLine.getItemId());
            map.put("warehouseCode", rtIssueLine.getWarehouseCode());
            map.put("warehouseName", rtIssueLine.getWarehouseName());
            map.put("warehouseId", rtIssueLine.getWarehouseId());
            map.put("locationCode", rtIssueLine.getLocationCode());
            map.put("locationName", rtIssueLine.getLocationName());
            map.put("locationId", rtIssueLine.getLocationId());
            map.put("areaCode", rtIssueLine.getAreaCode());
            map.put("areaName", rtIssueLine.getAreaName());
            map.put("areaId", rtIssueLine.getAreaId());
            map.put("erpStatus", rtIssueLine.getErpStatus());
            rtIssueLineMapList.add(map);
        }
        // 初始化退料信息
        respVO.setRtissuelineList(rtIssueLineMapList);
        return success(respVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得生产退料单头列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:query')")
    public CommonResult<List<RtIssueRespVO>> getRtIssueList(@RequestParam("ids") Collection<Long> ids) {
        List<RtIssueDO> list = rtIssueService.getRtIssueList(ids);
        return success(RtIssueConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产退料单头分页")
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:query')")
    public CommonResult<PageResult<RtIssueRespVO>> getRtIssuePage(@Valid RtIssuePageReqVO pageVO) {
        PageResult<RtIssueDO> pageResult = rtIssueService.getRtIssuePage(pageVO);
        return success(RtIssueConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出生产退料单头 Excel")
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:export')")
    @OperateLog(type = EXPORT)
    public void exportRtIssueExcel(@Valid RtIssueExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<RtIssueDO> list = rtIssueService.getRtIssueList(exportReqVO);
        // 导出 Excel
        List<RtIssueExcelVO> datas = RtIssueConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "生产退料单头.xls", "数据", RtIssueExcelVO.class, datas);
    }

    @GetMapping("/rtissueErp")
    @Operation(summary = "同步ERP退料接口")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:rt-issue:query')")
    public CommonResult<RtIssueRespVO> rtissueErp(@RequestParam("id") Long id) {
        RtIssueDO rtIssue = rtIssueService.getRtIssue(id);
        RtIssueRespVO respVO = RtIssueConvert.INSTANCE.convert(rtIssue);

        // 基于单身表初始化退料信息
        RtIssueLineListVO param = new RtIssueLineListVO();
        param.setRtId(id);
        param.setErpStatus("N"); // 卡控未同步ERP的退料信息
        List<RtIssueLineDO> lines = rtIssueLineService.selectList(param);
        // 将rtIssueLineList转为List<Map<String, Object>
        List<Map<String, Object>> rtIssueLineMapList = new ArrayList<>();
        if(CollUtil.isEmpty(lines)){
            return error(ErrorCodeConstants.RT_ISSUE_NEED_MAT);
        }

        // 追加ERP接口调用
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        // 获取工单BOM信息
        List<WorkorderBomDTO> bomList = workorderApi.getWorkorderBom(rtIssue.getWorkorderId());

        // 按物料维度分组处理
        Map<String, List<RtIssueLineDO>> materialGroups = lines.stream()
                .filter(line -> line.getSequence() != null && line.getSequenceOrder() != null)
                .collect(Collectors.groupingBy(RtIssueLineDO::getItemCode));

        // ERP接口调用标识
        List<Map<String, Object>> erpEnableList = new ArrayList<>();

        // 处理每个物料批次组
        for (Map.Entry<String, List<RtIssueLineDO>> entry : materialGroups.entrySet()) {
            String key = entry.getKey();
            List<RtIssueLineDO> rtLines = entry.getValue();
            // 获取首行基本信息
            RtIssueLineDO sampleLine = rtLines.get(0);
            // 获取工单BOM应发量
            BigDecimal bomQty = BigDecimal.ZERO;
            for (WorkorderBomDTO bom : bomList) {
                if (bom.getSequence().equals(sampleLine.getSequence()) && bom.getSequenceOrder().equals(sampleLine.getSequenceOrder())) {
                    bomQty = new BigDecimal(bom.getQuantity());
                    break;
                }
            }
            // 获取历史已退量
            // 获取当前已完成的退料单信息
            RtIssueExportReqVO usedRtIssue = new RtIssueExportReqVO();
            usedRtIssue.setTaskCode(rtIssue.getTaskCode());
            usedRtIssue.setWorkorderCode(rtIssue.getAreaCode());
            usedRtIssue.setStatus("FINISHED"); // 仅获取已完成物料
            List<RtIssueDO> usedRtIssueDOList = rtIssueService.getRtIssueList(usedRtIssue);

            BigDecimal totalReturned = BigDecimal.ZERO;
            for (RtIssueDO usedRtIssueHeader : usedRtIssueDOList) {
                RtIssueLineExportReqVO usedLineExportReqVO = new RtIssueLineExportReqVO();
                usedLineExportReqVO.setItemCode(sampleLine.getItemCode());
                //usedLineExportReqVO.setBatchCode(sampleLine.getBatchCode());
                usedLineExportReqVO.setRtId(usedRtIssueHeader.getId());
                usedLineExportReqVO.setSequence(sampleLine.getSequence());
                usedLineExportReqVO.setSequenceOrder(sampleLine.getSequenceOrder());
                List<RtIssueLineDO> rtIssueLine = rtIssueLineService.getRtIssueLineList(usedLineExportReqVO) == null ? new ArrayList<>() : rtIssueLineService.getRtIssueLineList(usedLineExportReqVO);

                if (!rtIssueLine.isEmpty()) {
                    for (RtIssueLineDO rt : rtIssueLine) {
                        totalReturned = totalReturned.add(rt.getQuantityRt());
                    }
                }
            }

            // 计算当前物料待退总量
            BigDecimal currentTotal = BigDecimal.ZERO;
            for (RtIssueLineDO line : rtLines) {
                currentTotal = currentTotal.add(line.getQuantityRt());
            }

            // 计算可退量分配
            BigDecimal remaining = bomQty.subtract(totalReturned);
            BigDecimal y01Qty = BigDecimal.ZERO;
            BigDecimal y02Qty = BigDecimal.ZERO;

            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                // 全部超退
                y02Qty = currentTotal;
            } else if (currentTotal.compareTo(remaining) <= 0) {
                // 全部正常退
                y01Qty = currentTotal;
            } else {
                // 拆分退料
                y01Qty = remaining;
                y02Qty = currentTotal.subtract(remaining);
            }

            // 生成H01报文
            if (y01Qty.compareTo(BigDecimal.ZERO) > 0) {
                Map<String, Object> item = buildErpItem(rtIssue, sampleLine, "Y01", y01Qty);
                if (item != null) {
                    list.add(item);
                    erpEnableList.add(item);
                }
            }

            if (y02Qty.compareTo(BigDecimal.ZERO) > 0) {
                Map<String, Object> item = buildErpItem(rtIssue, sampleLine, "Y02", y02Qty);
                if (item != null) {
                    list.add(item);
                    erpEnableList.add(item);
                }
            }
        }
        // 按退料类型分组调用
        Map<String, List<Map<String, Object>>> typeGroups = list.stream()
                .collect(Collectors.groupingBy(item -> (String) item.get("sfdc015")));
        // 执行分组调用
        for (Map.Entry<String, List<Map<String, Object>>> entry : typeGroups.entrySet()) {
            String type = entry.getKey();
            List<Map<String, Object>> items = entry.getValue();
            Map<String, Object> erpParams = new HashMap<>();
            erpParams.put("goodsList", items);
            String sfda002Value = "Y01".equals(type) ? "21" : "22";
            erpParams.put("sfda002", sfda002Value);
            erpParams.put("source_no", rtIssue.getRtCode());
            List<Map<String, Object>> rtIssueList = entry.getValue();
            if (!items.isEmpty()) {
                String erpResult = workorderERPAPI.workOrderIssueCreate(erpParams);
                if (erpResult.contains("SUCCESS")) {
                    for (Map<String, Object> map : rtIssueList){
                        Number lineId = (Number) map.get("rtissueId");
                        RtIssueLineDO queryLine = rtIssueLineService.getRtIssueLine(lineId.longValue());
                        queryLine.setErpStatus("Y");
                        rtIssueLineService.updateRtIssueLine(RtIssueLineConvert.INSTANCE.convert01(queryLine));
                    }
                }
            }
        }
        if(erpEnableList.isEmpty()){
            return error(ErrorCodeConstants.RT_ISSUE_NO_NEED_ERP);
        }

        return success();
    }

}
