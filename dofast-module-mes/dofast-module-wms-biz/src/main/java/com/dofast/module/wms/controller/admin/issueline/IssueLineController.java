package com.dofast.module.wms.controller.admin.issueline;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.util.StringUtils;
import com.dofast.framework.common.pojo.UserConstants;
import com.dofast.framework.common.util.bean.BeanUtils;
import com.dofast.framework.common.util.string.StrUtils;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.cmms.api.dvmachinery.DvMachineryApi;
import com.dofast.module.cmms.api.dvmachinery.dto.DvMachineryDTO;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.api.TaskApi.dto.TaskDTO;
import com.dofast.module.pro.api.WorkorderApi.WorkorderApi;
import com.dofast.module.pro.api.WorkorderApi.dto.WorkorderBomDTO;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.tm.convert.tool.ToolConvert;
import com.dofast.module.tm.dal.dataobject.tool.ToolDO;
import com.dofast.module.wms.api.ERPApi.WorkorderERPAPI;
import com.dofast.module.wms.controller.admin.allocatedheader.vo.AllocatedHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineExportReqVO;
import com.dofast.module.wms.controller.admin.issueheader.vo.IssueHeaderExportReqVO;
import com.dofast.module.wms.controller.admin.materialstock.vo.MaterialStockExportReqVO;
import com.dofast.module.wms.controller.admin.rtissue.vo.RtIssueCreateReqVO;
import com.dofast.module.wms.controller.admin.rtissue.vo.RtIssueExportReqVO;
import com.dofast.module.wms.controller.admin.rtissueline.vo.RtIssueLineCreateReqVO;
import com.dofast.module.wms.controller.admin.rtissueline.vo.RtIssueLineExportReqVO;
import com.dofast.module.wms.controller.admin.storagearea.vo.StorageAreaExportReqVO;
import com.dofast.module.wms.controller.admin.transaction.vo.TransactionUpdateReqVO;
import com.dofast.module.wms.convert.issueheader.IssueHeaderConvert;
import com.dofast.module.wms.convert.rtissue.RtIssueConvert;
import com.dofast.module.wms.dal.dataobject.feedline.FeedLineDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueTxBean;
import com.dofast.module.wms.dal.dataobject.materialstock.MaterialStockDO;
import com.dofast.module.wms.dal.dataobject.rtissue.RtIssueDO;
import com.dofast.module.wms.dal.dataobject.rtissueline.RtIssueLineDO;
import com.dofast.module.wms.dal.dataobject.storagearea.StorageAreaDO;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import com.dofast.module.wms.dal.dataobject.warehouse.WarehouseDO;
import com.dofast.module.wms.service.feedline.FeedLineService;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.materialstock.MaterialStockService;
import com.dofast.module.wms.service.rtissue.RtIssueService;
import com.dofast.module.wms.service.rtissueline.RtIssueLineService;
import com.dofast.module.wms.service.storagearea.StorageAreaService;
import com.dofast.module.wms.service.storagelocation.StorageLocationService;
import com.dofast.module.wms.service.transaction.TransactionService;
import com.dofast.module.wms.service.warehouse.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import com.dofast.module.wms.enums.ErrorCodeConstants;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.common.pojo.UserConstants.BATCH_CODE_SWITCH_DATE;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.wms.controller.admin.issueline.vo.*;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.convert.issueline.IssueLineConvert;
import com.dofast.module.wms.service.issueline.IssueLineService;
import org.springframework.web.servlet.View;

@Tag(name = "仓储管理 - 生产领料单行")
@RestController
@RequestMapping("/mes/wms/issue-line")
@Validated
public class IssueLineController {

    @Resource
    private IssueLineService issueLineService;

    @Resource
    private IssueHeaderService issueHeaderService;

    @Resource
    private WarehouseService warehouseService;

    @Resource
    private StorageLocationService storageLocationService;

    @Resource
    private StorageAreaService storageAreaService;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private FeedLineService feedLineService;

    @Autowired
    private TransactionService transactionService;

    @Resource
    private WorkorderApi workorderService;

    @Resource
    private TaskApi taskApi;

    @Resource
    private WorkorderERPAPI workorderERPAPI;

    @Resource
    private RtIssueService rtIssueService;

    @Resource
    private WorkorderApi workorderApi;

    @Resource
    private RtIssueLineService rtIssueLineService;

    @Resource
    private MaterialStockService materialStockService;

    @Resource
    private DvMachineryApi machineryApi;

    @PostMapping("/create")
    @Operation(summary = "创建生产领料单行")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:create')")
    public CommonResult<Long> createIssueLine(@Valid @RequestBody IssueLineCreateReqVO createReqVO) {
        // 获取当前单身信息
        IssueHeaderDO issueHeaderDO = issueHeaderService.getIssueHeader(createReqVO.getIssueId());

        if(issueHeaderDO == null){
            return error(ErrorCodeConstants.ISSUE_HEADER_NOT_EXISTS);
        }

        String originWorkorder = issueHeaderDO.getWorkorderCode();

        if(createReqVO.getBarcodeNumber() == null){
            // 基于库存获取当前的来源单号Id
            /*Long originId = materialStockService.getMaterialStockList(new MaterialStockExportReqVO().setBatchCode(createReqVO.getBatchCode()).setRecptStatus("Y")).get(0).getOriginId();
            if(originId!=null){
                createReqVO.setBarcodeNumber(originId);
            }*/
            Long originId = materialStockService.getOriginIdByBatchCode(createReqVO.getBatchCode());
            if(originId!=null){
                createReqVO.setBarcodeNumber(originId);
            }
        }

        String scannedWorkorderCode = null;
        // 追加工单卡控校验
        List<MaterialStockDO> stocks = materialStockService.getMaterialStockList(
                new MaterialStockExportReqVO()
                        .setBatchCode(createReqVO.getBatchCode())
                        .setRecptStatus("Y")
        );
        if (!stocks.isEmpty()) {
            scannedWorkorderCode = stocks.get(0).getWorkorderCode();
        }

       /*
       澳美一个任务单无法对应一个机台
       if(issueHeaderDO.getMachineryCode() != null){
            createReqVO.setMachineryCode(issueHeaderDO.getMachineryCode());
        }
        if(issueHeaderDO.getMachineryName() != null){
            createReqVO.setMachineryName(issueHeaderDO.getMachineryName());
        }
        if(issueHeaderDO.getMachineryId() != null){
            createReqVO.setMachineryId(String.valueOf(issueHeaderDO.getMachineryId()));
        }*/

        if (StrUtils.isNotNull(createReqVO.getWarehouseId())) {
            WarehouseDO warehouseDO = warehouseService.getWarehouse(createReqVO.getWarehouseId());
            //2025-03-13 追加校验当前扫码物料是否在虚拟线边仓
            if (warehouseDO.getWarehouseCode().equals(Constant.VIRTUAL_WH)) {
                return error(ErrorCodeConstants.ISSUE_LINE_VIRTUAL_WH);
            }
            createReqVO.setWarehouseCode(warehouseDO.getWarehouseCode());
            createReqVO.setWarehouseName(warehouseDO.getWarehouseName());
        }
        if (StrUtils.isNotNull(createReqVO.getLocationId())) {
            StorageLocationDO storageLocationDO = storageLocationService.getStorageLocation(createReqVO.getLocationId());
            createReqVO.setLocationCode(storageLocationDO.getLocationCode());
            createReqVO.setLocationName(storageLocationDO.getLocationName());
            // 追加校验, 无法添加非库存的数据
            String processCode = issueHeaderDO.getProcessCode();
            if (!processCode.equals(storageLocationDO.getProcessCode()) && !"AM010".equals(storageLocationDO.getProcessCode())) {
                return error(ErrorCodeConstants.ISSUE_HEADER_NO_PROCESS);
            }
        }
        if (StrUtils.isNotNull(createReqVO.getAreaId())) {
            StorageAreaDO storageAreaDO = storageAreaService.getStorageArea(createReqVO.getAreaId());
            createReqVO.setAreaCode(storageAreaDO.getAreaCode());
            createReqVO.setAreaName(storageAreaDO.getAreaName());
        }
        // 追加校验: 未派工不允许进行扫码上料操作
        TaskDTO taskDto = taskApi.getTask(issueHeaderDO.getTaskCode());
        if (taskDto == null) {
            return error(ErrorCodeConstants.ISSUE_HEADER_NO_TASK);
        }
        String teamCode = Optional.ofNullable(taskDto.getAttr1()).orElse(null);
        if (teamCode == null) {
            return error(ErrorCodeConstants.ALLOCATED_HEADER_NEED_TASK_TEAM);
        }

        // 追加校验:
        // 获取当前领料单对应工单, 校验当前物料是否属于bom, 若存在则绑定ERP项次, 项序用于接口传参
        List<WorkorderBomDTO> bomList = workorderService.getWorkorderBom(issueHeaderDO.getWorkorderCode());
        createReqVO.getItemCode();
        for (WorkorderBomDTO bom : bomList) {
            // 校验当前物料是否属于BOM列表
            if (bom.getItemCode().equals(createReqVO.getItemCode())) {
                createReqVO.setSequence(bom.getSequence());
                createReqVO.setSequenceOrder(bom.getSequenceOrder());
                break;
            }
        }

        if (createReqVO.getSequence() == null && !originWorkorder.equals(scannedWorkorderCode)) {
            return error(ErrorCodeConstants.ISSUE_LINE_ITEM_NOT_CONTAIN_BOM);
        }

        // 追加卡控, 若当前领用物料为上道制程产成品且需要ERP过账, 追加卡控
        if(!"Y".equals(stocks.get(0).getConfirmStatus())) {
            return error(ErrorCodeConstants.ISSUE_HEADER_NEED_ERP);
        }

        createReqVO.setParentBatchCode(stocks.get(0).getParentBatchCode());
        return success(issueLineService.createIssueLine(createReqVO));
    }

    @PostMapping("/checkMaxIssue")
    @Operation(summary = "创建生产领料单行")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:create')")
    public CommonResult<Integer> checkMaxIssue(@Valid @RequestBody IssueLineCreateReqVO createReqVO) {
        Integer count = -1;
        IssueHeaderDO issueHeaderDO = issueHeaderService.getIssueHeader(createReqVO.getIssueId());
        String processCode = issueHeaderDO.getProcessCode();
        if (createReqVO.getItemCode().startsWith("61") && "AM005".equals(processCode)) {
            // 查看当前设备是否存在多卷膜类物料
            IssueLineExportReqVO exportReqVO = new IssueLineExportReqVO();
            exportReqVO.setMachineryId(createReqVO.getMachineryId());
            exportReqVO.setIssueId(createReqVO.getIssueId());
            List<IssueLineDO> issueLineByMachineryId = issueLineService.getIssueLineList(exportReqVO);
            // 当前设备存在物料
            // 2025-4-25注释: 分条不是每次都给复合18000的膜
            // 特殊情况: 分条膜3000 , 复合需扫码两次以对应6000米的纸
            /*if (!issueLineByMachineryId.isEmpty()) {
                for (IssueLineDO line : issueLineByMachineryId) {
                    if (line.getItemCode().startsWith("61")) {
                        // 校验最新一卷膜类物料状态, 禁止多次上膜
                        if ("N".equals(line.getStatus()) && "N".equals(line.getFeedbackStatus()) || "N".equals(line.getStatus()) && "Y".equals(line.getFeedbackStatus())) {
                            return error(ErrorCodeConstants.ISSUE_LINE_MULTI_MATERIAL);
                        }
                        String feedbackCode = Optional.ofNullable(line.getFeedbackCode()).orElse("");
                        String[] str = feedbackCode.split(",");
                        // 检查是否为空数组或仅包含空字符串
                        if (str.length == 1 && str[0].isEmpty()) {
                            count = 0;
                            break;
                        } else {
                            count = str.length;
                            break;
                        }
                    }
                }
            }*/
        }
        return success(count);
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产领料单行")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:update')")
    public CommonResult<Boolean> updateIssueLine(@Valid @RequestBody IssueLineUpdateReqVO updateReqVO) {
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
        issueLineService.updateIssueLine(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产领料单行")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('wms:issue-line:delete')")
    public CommonResult<Boolean> deleteIssueLine(@RequestParam("id") ArrayList<Long> ids) {
        // 追加卡控，无法删除已领料行数据
        for (Long id : ids) {
            if ("Y".equals(issueLineService.getIssueLine(id).getStatus())) {
                return error(ErrorCodeConstants.ISSUE_LINE_DELETE_ERROR);
            }
            ;
        }
        issueLineService.batchDeleteIssueLine(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得生产领料单行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:query')")
    public CommonResult<IssueLineRespVO> getIssueLine(@RequestParam("id") Long id) {
        IssueLineDO issueLine = issueLineService.getIssueLine(id);
        return success(IssueLineConvert.INSTANCE.convert(issueLine));
    }

    @GetMapping("/list")
    @Operation(summary = "获得生产领料单行列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:query')")
    public CommonResult<List<IssueLineRespVO>> getIssueLineList(@RequestParam("ids") Collection<Long> ids) {
        List<IssueLineDO> list = issueLineService.getIssueLineList(ids);
        return success(IssueLineConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产领料单行分页")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:query')")
    public CommonResult<PageResult<IssueLineRespVO>> getIssueLinePage(@Valid IssueLinePageReqVO pageVO) {
        /*AdminUserRespDTO adminUser = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        Set<Long> postIds =adminUser.getPostIds();
        if(postIds.isEmpty()){
            return error(ErrorCodeConstants.USER_NO_POST);
        }
        // 校验postIds是否包含42(车间主任)
        if (!postIds.contains(42L)) {
            // 若当前并非车间主任, 则仅允许查看自己的上料记录
            pageVO.setCreator(adminUser.getId());
        }*/
        PageResult<IssueLineDO> pageResult = issueLineService.getIssueLinePage(pageVO);
        List<IssueLineDO> voList = pageResult.getList();
        // 循环列表, 数据创建人id, 去用户表转换为用户昵称
        for (IssueLineDO vo : voList) {
            AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(Long.valueOf(vo.getCreator()));
            adminUserRespDTO.getNickname();
            vo.setCreator(adminUserRespDTO.getNickname());
        }
        System.out.println(voList);
        System.out.println(pageResult);

        return success(IssueLineConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出生产领料单行 Excel")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:export')")
    @OperateLog(type = EXPORT)
    public void exportIssueLineExcel(@Valid IssueLineExportReqVO exportReqVO,
                                     HttpServletResponse response) throws IOException {
        List<IssueLineDO> list = issueLineService.getIssueLineList(exportReqVO);
        // 导出 Excel
        List<IssueLineExcelVO> datas = IssueLineConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "生产领料单行.xls", "数据", IssueLineExcelVO.class, datas);
    }

    @PutMapping("/updateEnable")
    @Operation(summary = "更新生产领料单行")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:update')")
    public CommonResult<Boolean> updateEnable(@RequestBody ArrayList<Long> ids) {
        List<IssueLineDO> lineList = issueLineService.getIssueLineList(ids);
        // 追加卡控, 仅允许复合车间使用
        /*IssueHeaderDO issueHeaderDO = issueHeaderService.getIssueHeader(lineList.get(0).getIssueId());
        if (!"AM005".equals(issueHeaderDO.getProcessCode())) {
            return error(ErrorCodeConstants.ISSUE_HEADER_NO_ENABLE_PROCESS);
        }*/
        // 追加校验: 仅允许启用膜类物料, 开头为61
        /*for (IssueLineDO line : lineList) {
            if (!line.getItemCode().startsWith("61")) {
                return error(ErrorCodeConstants.ISSUE_LINE_NOT_ENABLE);
            }
        }*/

        for (IssueLineDO line : lineList) {
            String status = line.getStatus();
            if("N".equals(status)){
                return error(ErrorCodeConstants.ISSUE_LINE_NOT_EXECUTE);
            }
        }


        for (IssueLineDO line : lineList) {
            String flag = "true".equals(line.getEnableFlag()) ? "false" : "true";
            line.setEnableFlag(flag);
        }
        issueLineService.updateIssueLineBatch(lineList);
        return success(true);
    }

    @PutMapping("/cancleIssue")
    @Operation(summary = "撤销生产领料单行")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:update')")
    public CommonResult<Boolean> cancleIssue(@RequestBody ArrayList<Long> ids) {
        List<IssueLineDO> lineList = issueLineService.getIssueLineList(ids);
        IssueHeaderDO headerDO = issueHeaderService.getIssueHeader(lineList.get(0).getIssueId());

        // ==================== ERP 接口调用部分 - 只处理 erp_status=Y 的行 ====================
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> list = new ArrayList<>();

        // 分离需要调用ERP的行 (erp_status = 'Y')
        List<IssueLineDO> erpSyncLines = lineList.stream()
                .filter(line -> "Y".equals(line.getErpEnable()))
                .collect(Collectors.toList());

        if (!erpSyncLines.isEmpty()) {
            // 获取工单BOM信息（只需获取一次）
            List<WorkorderBomDTO> bomList = workorderApi.getWorkorderBom(headerDO.getWorkorderId());

            // 构建BOM查找映射（按工序+工序顺序）
            Map<String, WorkorderBomDTO> bomMap = new HashMap<>();
            for (WorkorderBomDTO bom : bomList) {
                String key = bom.getSequence() + "|" + bom.getSequenceOrder();
                bomMap.put(key, bom);
            }

            // 按物料+工序+工序顺序复合分组
            Map<String, List<IssueLineDO>> materialGroups = erpSyncLines.stream()
                    .filter(line -> line.getSequence() != null && line.getSequenceOrder() != null)
                    .collect(Collectors.groupingBy(line ->
                            line.getItemCode() + "|" + line.getSequence() + "|" + line.getSequenceOrder()
                    ));

            // 处理每个物料工序组
            for (Map.Entry<String, List<IssueLineDO>> entry : materialGroups.entrySet()) {
                String key = entry.getKey();
                List<IssueLineDO> lines = entry.getValue();

                // 获取组内首行（同组行工序相同）
                IssueLineDO firstLine = lines.get(0);

                // 获取工单BOM应发量
                String bomKey = firstLine.getSequence() + "|" + firstLine.getSequenceOrder();
                WorkorderBomDTO bom = bomMap.get(bomKey);
                BigDecimal bomQty = bom != null ? new BigDecimal(bom.getQuantity()) : BigDecimal.ZERO;

                // 获取历史已退量
                // 获取当前已完成的退料单信息
                RtIssueExportReqVO usedRtIssue = new RtIssueExportReqVO();
                usedRtIssue.setTaskCode(headerDO.getTaskCode());
                usedRtIssue.setWorkorderCode(headerDO.getAreaCode());
                usedRtIssue.setStatus("FINISHED"); // 仅获取已完成物料
                List<RtIssueDO> usedRtIssueDOList = rtIssueService.getRtIssueList(usedRtIssue);

                BigDecimal totalReturned = BigDecimal.ZERO;
                for (RtIssueDO rtIssue : usedRtIssueDOList) {
                    RtIssueLineExportReqVO usedLineExportReqVO = new RtIssueLineExportReqVO();
                    usedLineExportReqVO.setItemCode(firstLine.getItemCode());
                    usedLineExportReqVO.setRtId(rtIssue.getId());
                    // 使用当前组的工序信息
                    usedLineExportReqVO.setSequence(firstLine.getSequence());
                    usedLineExportReqVO.setSequenceOrder(firstLine.getSequenceOrder());

                    List<RtIssueLineDO> rtIssueLine = rtIssueLineService.getRtIssueLineList(usedLineExportReqVO);
                    if (rtIssueLine != null && !rtIssueLine.isEmpty()) {
                        for (RtIssueLineDO rt : rtIssueLine) {
                            totalReturned = totalReturned.add(rt.getQuantityRt());
                        }
                    }
                }

                // 计算当前待退总量（组内所有行）
                BigDecimal currentTotal = BigDecimal.ZERO;
                for (IssueLineDO line : lines) {
                    currentTotal = currentTotal.add(line.getQuantityIssued());
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

                // 生成H01报文（使用组内首行）
                if (y01Qty.compareTo(BigDecimal.ZERO) > 0) {
                    Map<String, Object> item = buildErpItem(headerDO, firstLine, "Y01", y01Qty);
                    if (item != null) {
                        list.add(item);
                    }
                }

                if (y02Qty.compareTo(BigDecimal.ZERO) > 0) {
                    Map<String, Object> item = buildErpItem(headerDO, firstLine, "Y02", y02Qty);
                    if (item != null) {
                        list.add(item);
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
                erpParams.put("source_no", headerDO.getIssueCode());
                if (!items.isEmpty()) {
                    // 调用ERP接口
                    String erpResult = workorderERPAPI.workOrderIssueCreate(erpParams);
                }
            }
        }
        // ==================== ERP 处理结束 ====================

        // 开始追加生产退料单信息 - 后续用于统计已退料总数, 判定是否超退
        RtIssueCreateReqVO rtIssueCreateReqVO = new RtIssueCreateReqVO();
        rtIssueCreateReqVO.setTaskCode(headerDO.getTaskCode());
        rtIssueCreateReqVO.setWorkorderCode(headerDO.getWorkorderCode());
        rtIssueCreateReqVO.setWorkorderId(headerDO.getWorkorderId());

        // 退料仓库默认为当前领料仓库
        rtIssueCreateReqVO.setWarehouseId(headerDO.getWarehouseId());
        rtIssueCreateReqVO.setWarehouseCode(headerDO.getWarehouseCode());
        rtIssueCreateReqVO.setWarehouseName(headerDO.getWarehouseName());
        rtIssueCreateReqVO.setLocationId(headerDO.getLocationId());
        rtIssueCreateReqVO.setLocationCode(headerDO.getLocationCode());
        rtIssueCreateReqVO.setLocationName(headerDO.getLocationName());
        rtIssueCreateReqVO.setAreaId(headerDO.getAreaId());
        rtIssueCreateReqVO.setAreaCode(headerDO.getAreaCode());
        rtIssueCreateReqVO.setAreaName(headerDO.getAreaName());
        rtIssueCreateReqVO.setStatus("FINISHED"); // ERP回传成功后转为已完成
        Random random = new Random();
        int randomNum = random.nextInt(900) + 100;
        // 为避免与正常退料重复, 此处使用CX(撤销)作为开头, 以随机数结尾
        rtIssueCreateReqVO.setRtCode("CX" + DateUtil.format(new Date(), "yyyyMMdd") + randomNum);
        Long rtIssueHeaderId = rtIssueService.createRtIssue(rtIssueCreateReqVO);
        List<RtIssueLineDO> rtIssueList = new ArrayList<>();

        // 获取对应的上料详情
        for (IssueLineDO issueLine : lineList) {
            FeedLineExportReqVO feedLineExportReqVO = new FeedLineExportReqVO();
            feedLineExportReqVO.setIssueId(issueLine.getIssueId());
            feedLineExportReqVO.setItemCode(issueLine.getItemCode());
            feedLineExportReqVO.setBatchCode(issueLine.getBatchCode());
            feedLineExportReqVO.setBarcodeNumber(issueLine.getBarcodeNumber());
            List<FeedLineDO> feedLines = feedLineService.getFeedLineList(feedLineExportReqVO);
            if (feedLines == null || feedLines.isEmpty()) {
                continue; // 若用户勾选到未上料单据直接略过
            }
            FeedLineDO feedLineDO = feedLines.get(0);

            // 开始追加退料单身信息
            RtIssueLineDO rtIssueLineCreateReqVO = new RtIssueLineDO();
            rtIssueLineCreateReqVO.setRtId(rtIssueHeaderId);
            rtIssueLineCreateReqVO.setItemCode(feedLineDO.getItemCode());
            rtIssueLineCreateReqVO.setItemName(feedLineDO.getItemName());
            rtIssueLineCreateReqVO.setItemId(feedLineDO.getItemId());
            rtIssueLineCreateReqVO.setSpecification(feedLineDO.getSpecification());
            rtIssueLineCreateReqVO.setUnitOfMeasure(feedLineDO.getUnitOfMeasure());
            rtIssueLineCreateReqVO.setSequence(feedLineDO.getSequence());
            rtIssueLineCreateReqVO.setSequenceOrder(feedLineDO.getSequenceOrder());
            rtIssueLineCreateReqVO.setBatchCode(feedLineDO.getBatchCode());
            // 追加库存
            rtIssueLineCreateReqVO.setWarehouseId(feedLineDO.getWarehouseId());
            rtIssueLineCreateReqVO.setWarehouseCode(feedLineDO.getWarehouseCode());
            rtIssueLineCreateReqVO.setWarehouseName(feedLineDO.getWarehouseName());
            rtIssueLineCreateReqVO.setLocationId(feedLineDO.getLocationId());
            rtIssueLineCreateReqVO.setLocationCode(feedLineDO.getLocationCode());
            rtIssueLineCreateReqVO.setLocationName(feedLineDO.getLocationName());
            rtIssueLineCreateReqVO.setAreaId(feedLineDO.getAreaId());
            rtIssueLineCreateReqVO.setAreaCode(feedLineDO.getAreaCode());
            rtIssueLineCreateReqVO.setAreaName(feedLineDO.getAreaName());
            BigDecimal rtQuantity = new BigDecimal(feedLineDO.getQuantity());
            rtIssueLineCreateReqVO.setQuantityRt(rtQuantity);
            rtIssueList.add(rtIssueLineCreateReqVO);
        }
        rtIssueLineService.insertRtIssueLineBatch(rtIssueList);

        for (IssueLineDO line : lineList) {
            // 获取上料记录信息
            FeedLineExportReqVO exportReqVO = new FeedLineExportReqVO();
            exportReqVO.setIssueId(line.getIssueId());
            exportReqVO.setBarcodeNumber(line.getBarcodeNumber());
            exportReqVO.setItemCode(line.getItemCode());
            exportReqVO.setBatchCode(line.getBatchCode());
            List<FeedLineDO> feedLines = Optional.ofNullable(feedLineService.getFeedLineList(exportReqVO)).orElse(new ArrayList<>());
            FeedLineDO feedLine = feedLines.stream().findFirst().orElse(null);
            if (feedLine == null) {
                continue; // 若用户勾选到未上料单据直接略过
            }
            // 基于上料记录进行回滚
            String transactionType_out = Constant.TRANSACTION_TYPE_ITEM_REISSUE_OUT;
            String transactionType_in = Constant.TRANSACTION_TYPE_ITEM_REISSUE_IN;

            //这里先构造一条原库存减少的事务
            TransactionUpdateReqVO transaction_out = new TransactionUpdateReqVO();
            BeanUtils.copyBeanProp(transaction_out, line);
            transaction_out.setTransactionType(transactionType_out);
            transaction_out.setTransactionFlag(-1);//库存减少
            transaction_out.setTransactionQuantity(line.getQuantityIssued());
            transaction_out.setTransactionDate(LocalDateTime.now());
            transaction_out.setSourceDocCode(headerDO.getIssueCode());
            transaction_out.setSourceDocId(headerDO.getId());
            transaction_out.setSourceDocLineId(line.getId());
            transactionService.processTransaction(transaction_out);
            //再构造一条目的库存增加的事务
            TransactionUpdateReqVO transaction_in = new TransactionUpdateReqVO();
            BeanUtils.copyBeanProp(transaction_in, feedLine);
            transaction_in.setTransactionType(transactionType_in);
            transaction_in.setTransactionFlag(1);//库存增加
            transaction_in.setTransactionQuantity(BigDecimal.valueOf(feedLine.getQuantity()));
            //由于是新增的库存记录所以需要将查询出来的库存记录ID置为空
            transaction_in.setMaterialStockId(null);
            transaction_in.setSourceDocCode(headerDO.getIssueCode());
            transaction_in.setSourceDocId(headerDO.getId());
            transaction_in.setSourceDocLineId(line.getId());
            //使用出库事务的供应商初始化入库事务的供应商
            transaction_in.setVendorId(transaction_out.getVendorId());
            transaction_in.setVendorCode(transaction_out.getVendorCode());
            transaction_in.setVendorName(transaction_out.getVendorName());
            transaction_in.setVendorNick(transaction_out.getVendorNick());
            //这里使用上料记录的线边库初始化对应的入库仓库、库区、库位
            WarehouseDO warehouse = warehouseService.selectWmWarehouseByWarehouseCode(feedLine.getWarehouseCode());
            transaction_in.setWarehouseId(warehouse.getId());
            transaction_in.setWarehouseCode(warehouse.getWarehouseCode());
            transaction_in.setWarehouseName(warehouse.getWarehouseName());
            StorageLocationDO location = storageLocationService.getStorageLocation(feedLine.getLocationId());
            transaction_in.setLocationId(location.getId());
            transaction_in.setLocationCode(location.getLocationCode());
            transaction_in.setLocationName(location.getLocationName());
            StorageAreaExportReqVO areaExportReqVO = new StorageAreaExportReqVO();
            areaExportReqVO.setLocationId(location.getId());
            areaExportReqVO.setAreaCode(feedLine.getAreaCode());
            StorageAreaDO area = storageAreaService.getStorageAreaList(areaExportReqVO).get(0) == null ? null : storageAreaService.getStorageAreaList(areaExportReqVO).get(0);
            if (area != null) {
                transaction_in.setAreaId(area.getId());
                transaction_in.setAreaCode(area.getAreaCode());
                transaction_in.setAreaName(area.getAreaName());
            }
            //设置入库相关联的出库事务ID
            transaction_in.setRelatedTransactionId(transaction_out.getId());
            transactionService.processTransaction(transaction_in);

            // 修改领料单行状态
            line.setStatus("N");
            line.setWarehouseId(feedLine.getWarehouseId());
            line.setWarehouseCode(feedLine.getWarehouseCode());
            line.setWarehouseName(feedLine.getWarehouseName());
            line.setLocationId(feedLine.getLocationId());
            line.setLocationCode(feedLine.getLocationCode());
            line.setLocationName(feedLine.getLocationName());
            line.setAreaId(feedLine.getAreaId());
            line.setAreaCode(feedLine.getAreaCode());
            line.setAreaName(feedLine.getAreaName());
            issueLineService.updateIssueLine(IssueLineConvert.INSTANCE.convert01(line));

            // 移除上料记录
            feedLineService.deleteFeedLine(feedLine.getId());
        }
        return success(true);
    }


    private Map<String, Object> buildErpItem(IssueHeaderDO header, IssueLineDO line,
                                             String reasonCode, BigDecimal qty) {

        // 前置校验批次号
        String batchCode;
        MaterialStockExportReqVO exportReqVO = new MaterialStockExportReqVO();
        exportReqVO.setItemCode(line.getItemCode());
        exportReqVO.setBatchCode(line.getBatchCode());
        exportReqVO.setRecptStatus("Y");
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
                System.out.println("ERP批次号缺失 | 要撤销的领料单行ID：" +  line.getId());
                return null;
            }
        } else {
            // 修改为母批次
            // batchCode = line.getBatchCode();
            batchCode = line.getParentBatchCode();
            if (batchCode == null) {
                System.out.println("批次号缺失 | 要撤销的领料单行ID：" +  line.getId());
                return null;
            }
        }

        Map<String, Object> item = new LinkedHashMap<>();
        item.put("sfdc001", header.getWorkorderCode()); // 工单号
        item.put("sfdc002", line.getSequence()); // 项次
        item.put("sfdc003", line.getSequenceOrder()); // 项序
        item.put("sfdc007", qty.setScale(4, RoundingMode.HALF_UP)); // 撤销数量
        item.put("sfdc012", header.getLocationCode()); // 库区
        item.put("sfdc013", header.getAreaCode()); // 库位
        // 2025-06-08 修改为母批
        item.put("sfdc014", batchCode); // 批次
        item.put("sfdc015", reasonCode); // 理由码
        item.put("sfdc016", ""); // 库存管理特征
        item.put("issueLineId", line.getId());
        item.put("typeud001" , line.getUnitOfMeasure()); // 追加单位
        return item;
    }

    @GetMapping("/getMachineryInfo")
    @Operation(summary = "获得生产领料单行")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('wms:issue-line:query')")
    public CommonResult<DvMachineryDTO> getMachineryInfo(@RequestParam("taskCode") String taskCode) {
        List<IssueHeaderDO> headerDOList = issueHeaderService.getIssueHeaderList(new IssueHeaderExportReqVO().setTaskCode(taskCode));
        if(headerDOList.isEmpty()){
            return error(ErrorCodeConstants.ISSUE_HEADER_NOT_EXISTS);
        }
        IssueHeaderDO header = headerDOList.get(0);
        if(header==null){
            return error(ErrorCodeConstants.ISSUE_HEADER_NOT_EXISTS);
        }
        List<IssueLineDO> lineList = issueLineService.getIssueLineByHeaderId(header.getId());
        if(lineList==null){
            return error(ErrorCodeConstants.ISSUE_LINE_NOT_EXISTS);
        }
        // 将lineList按创建时间排序, 获取最新一条信息
        lineList.sort(Comparator.comparing(IssueLineDO::getCreateTime).reversed());
        String machineryCode = lineList.get(0).getMachineryCode();
        DvMachineryDTO machineryDTO = machineryApi.getMachineryInfo(machineryCode);
        return success(machineryDTO);
    }

    @PutMapping("/issueErp")
    @Operation(summary = "手动领料")
    //@PreAuthorize("@ss.hasPermission('wms:issue-header:erpInterface')")
    public CommonResult<Boolean> issueErp(@RequestBody ArrayList<Long> ids) {
        List<IssueLineDO> lineList = issueLineService.getIssueLineList(ids);
        IssueHeaderDO header = issueHeaderService.getIssueHeader(lineList.get(0).getIssueId());

        // 构建ERP接口参数
        Map<String, Object> erpParams = new HashMap<>();
        List<Map<String, Object>> goodsList = new ArrayList<>();

        // ERP接口调用标识
        List<IssueLineDO> erpEnableList = new ArrayList<>();
        for (IssueLineDO line : lineList) {
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
                totalUsed = totalUsed.add(lineDO.getQuantityIssued());
            }

            BigDecimal currentQty = line.getQuantityIssued();
            BigDecimal bomQty = new BigDecimal(bom.getQuantity());

            // 计算可分配数量
            BigDecimal remaining = bomQty.subtract(totalUsed);
            BigDecimal h01Qty = BigDecimal.ZERO;
            BigDecimal h02Qty = BigDecimal.ZERO;

            if (remaining.compareTo(BigDecimal.ZERO) <= 0) {
                // 全部超领
                h02Qty = currentQty;
            } else if (currentQty.compareTo(remaining) <= 0) {
                // 全部成套领料
                h01Qty = currentQty;
            } else {
                // 拆分领料
                h01Qty = remaining;
                h02Qty = currentQty.subtract(remaining);
            }

            // 配置4为小数
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

        // 遍历分组进行接口调用
        for (Map.Entry<String, List<Map<String, Object>>> entry : groupedData.entrySet()) {
            // 设置当前分组的领料数据
            erpParams.put("goodsList", entry.getValue());
            // 调用ERP接口
            List<Map<String, Object>> list = entry.getValue();
            String erpResult = workorderERPAPI.workOrderIssueCreate(erpParams);
            // String erpResult = "ERROR";
            if (erpResult.contains("SUCCESS")) {
                // 接口调用成功, 追加ERP同步标识
                for (Map<String, Object> map : list){
                    Number lineId = (Number) map.get("issueLineId");
                    IssueLineDO queryLine = issueLineService.getIssueLine(lineId.longValue());
                    queryLine.setErpEnable("Y");
                    issueLineService.updateIssueLine(IssueLineConvert.INSTANCE.convert01(queryLine));
                }
            }
        }
        /*if(erpEnableList.isEmpty()){
            return error(ErrorCodeConstants.ISSUE_LINE_NO_NEED_ERP);
        }*/

        return success();
    }


}
