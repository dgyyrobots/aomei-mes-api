package com.dofast.module.pro.controller.admin.workorder;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONObject;
import com.dofast.framework.common.util.io.MinioUtil;
import com.dofast.module.mes.api.ProductBomApi.ProductBomApi;
import com.dofast.module.mes.api.ProductBomApi.dto.MdProductBomDTO;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.mes.controller.admin.mditemcategory.vo.MdItemCategoryExportReqVO;
import com.dofast.module.mes.dal.dataobject.mditemcategory.MdItemCategoryDO;
import com.dofast.module.mes.service.mditemcategory.MdItemCategoryService;
import com.dofast.module.pro.controller.admin.feedback.vo.FeedbackExportReqVO;
import com.dofast.module.pro.controller.admin.process.vo.ProcessExportReqVO;
import com.dofast.module.pro.controller.admin.routeprocess.vo.RouteProcessExportReqVO;
import com.dofast.module.pro.controller.admin.routeproduct.vo.RouteProductExportReqVO;
import com.dofast.module.pro.controller.admin.task.vo.TaskExportReqVO;
import com.dofast.module.pro.controller.admin.workorderbom.vo.WorkorderBomCreateReqVO;
import com.dofast.module.pro.controller.admin.workorderbom.vo.WorkorderBomExportReqVO;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.feedbackdefect.FeedbackDefectDO;
import com.dofast.module.pro.dal.dataobject.feedbackmember.FeedbackMemberDO;
import com.dofast.module.pro.dal.dataobject.process.ProcessDO;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import com.dofast.module.pro.dal.dataobject.routeproduct.RouteProductDO;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.dal.dataobject.workorderbom.WorkorderBomDO;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.feedbackdefect.FeedbackDefectService;
import com.dofast.module.pro.service.feedbackmember.FeedbackMemberService;
import com.dofast.module.pro.service.process.ProcessService;
import com.dofast.module.pro.service.route.RouteService;
import com.dofast.module.pro.service.routeprocess.RouteProcessService;
import com.dofast.module.pro.service.routeproduct.RouteProductService;
import com.dofast.module.pro.service.task.TaskService;
import com.dofast.module.pro.service.workorderbom.WorkorderBomService;
import com.dofast.module.report.api.PrintLog.PrintLogApi;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLinePageReqVO;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineSummaryVO;
import com.dofast.module.wms.dal.dataobject.issueheader.IssueHeaderDO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.service.feedline.FeedLineService;
import com.dofast.module.wms.service.issueheader.IssueHeaderService;
import com.dofast.module.wms.service.issueline.IssueLineService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.*;
import javax.servlet.http.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;

import static com.dofast.framework.common.pojo.CommonResult.error;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.pro.controller.admin.workorder.vo.*;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.convert.workorder.WorkorderConvert;
import com.dofast.module.pro.service.workorder.WorkorderService;

import java.util.Map;


@Tag(name = "生产管理 - 生产工单")
@RestController
@RequestMapping("/mes/pro/workorder")
@Validated
public class WorkorderController {

    @Resource
    private WorkorderService workorderService;
    @Resource
    private WorkorderBomService workorderBomService;

    @Resource
    private ProductBomApi productBomApi;

    @Resource
    private TaskService taskService;

    @Resource
    private PrintLogApi printLogApi;

    @Resource
    private MinioUtil minioUtil;

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private IssueLineService issueLineService;

    @Resource
    private IssueHeaderService issueHeaderService;

    @Resource
    private RouteService routeService;

    @Resource
    private RouteProcessService routeProcessService;

    @Resource
    private RouteProductService routeProductService;

    @Resource
    private FeedLineService feedLineService;

    @Resource
    private MdItemCategoryService mdItemCategoryService;

    @Resource
    private ProcessService processService;

    @Resource
    private FeedbackMemberService feedBackMemberService;

    @Resource
    private FeedbackDefectService feedbackDefectService;


    @PostMapping("/create")
    @Operation(summary = "创建生产工单")
    @PreAuthorize("@ss.hasPermission('pro:workorder:create')")
    public CommonResult<Long> createWorkorder(@Valid @RequestBody WorkorderCreateReqVO createReqVO) {
        if (Constant.NOT_UNIQUE.equals(workorderService.checkWorkorderCodeUnique(createReqVO))) {
            return error(ErrorCodeConstants.WORKORDER_CODE_EXISTS);
        }

        if (createReqVO.getParentId() == null || createReqVO.getParentId() == 0) {
            createReqVO.setAncestors("0");
        }
        List<RouteProductDO> routeProcessDOList = routeProductService.getRouteProductList(new RouteProductExportReqVO().setItemCode(createReqVO.getProductCode()));
        if (routeProcessDOList.isEmpty()) {
            return error(ErrorCodeConstants.ROUTE_PRODUCT_NOT_EXISTS_PROCESS);
        }
        RouteDO route = routeService.getRoute(routeProcessDOList.get(0).getRouteId());
        if (route == null) {
            return error(ErrorCodeConstants.ROUTE_PRODUCT_NOT_EXISTS);
        }
        String str = route.getRouteName().split("-")[1];
        createReqVO.setStatus("CONFIRMED");
        createReqVO.setRouteCode(str);

        Long workorderId = workorderService.createWorkorder(createReqVO);
        generateBomLine(workorderId);
        return success(workorderId);
    }

    @PutMapping("/update")
    @Operation(summary = "更新生产工单")
    @PreAuthorize("@ss.hasPermission('pro:workorder:update')")
    public CommonResult<Boolean> updateWorkorder(@Valid @RequestBody WorkorderUpdateReqVO updateReqVO) {
        WorkorderDO workorder = workorderService.getWorkorder(updateReqVO.getId());
        workorderService.updateWorkorder(updateReqVO);

        if (workorder.getProductId().longValue() != updateReqVO.getProductId().longValue() ||
                workorder.getQuantity() > updateReqVO.getQuantity()) {
            removeBomLine(updateReqVO.getId());
            generateBomLine(updateReqVO.getId());
            generateBomLine(updateReqVO.getId());
        }

        return success(true);
    }

    @PutMapping("/updateAdjuncts")
    @Operation(summary = "更新生产工单")
    @PreAuthorize("@ss.hasPermission('pro:workorder:update')")
    public CommonResult<Boolean> updateAdjuncts(@RequestBody WorkorderUpdateReqVO updateReqVO) {
        WorkorderDO workorder = workorderService.getWorkorder(updateReqVO.getId());
        if (workorder == null) {
            return success(true);
        }
        String url = updateReqVO.getAdjuncts();
        if (url == null) {
            return success(true);
        }
        // 将url基于","进行拆分
        String[] urls = url.split(",");
        if (urls.length == 0) {
            return success(true);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < urls.length; i++) {
            String currentUrl = urls[i].trim();

            if (currentUrl.isEmpty()) {
                continue;
            }
            // 如果需要去掉路径部分，只保留文件名
            String[] parts = currentUrl.split("/");
            // 校验当前parts是否包含%, 若包含只保留%前信息
            if (parts[parts.length - 1].contains("%")) {
                parts[parts.length - 1] = parts[parts.length - 1].substring(0, parts[parts.length - 1].indexOf("%"));
                if (parts[parts.length - 1].contains("?")) {
                    parts[parts.length - 1] = parts[parts.length - 1].substring(0, parts[parts.length - 1].indexOf("?"));
                }
            }
            String finName = parts[parts.length - 1];
            sb.append(finName);
            // 校验当前url是否为最后一个
            if (i != urls.length - 1) {
                sb.append(",");
            }
        }
        String adjuncts = Optional.ofNullable(sb.toString()).orElse("");
        if (adjuncts != "") {
            workorder.setAdjuncts(adjuncts);
        }
        workorderService.updateWorkorder(WorkorderConvert.INSTANCE.convert1(workorder));
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除生产工单")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:workorder:delete')")
    public CommonResult<Boolean> deleteWorkorder(@RequestParam("id") Long id) {
        WorkorderDO workorder = workorderService.getWorkorder(id);
        if (!Constant.ORDER_STATUS_PREPARE.equals(workorder.getStatus())) {
            return error(ErrorCodeConstants.WORKORDER_NOT_DELETED);
        }
        removeBomLine(id);
        workorderService.deleteWorkorder(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得生产工单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<WorkorderRespVO> getWorkorder(@RequestParam("id") Long id) {
        WorkorderDO workorder = workorderService.getWorkorder(id);
        // 获取实际图片地址
        String fileName = workorder.getAdjuncts();
        // 校验当前fileName是否存在"," 基于逗号进行拆分
        StringBuffer sb = new StringBuffer();
        /*
        2025-8-12 kkfileView预览只需提供图片名称, 无需完整的访问连接
        if (fileName != null) {
            String[] fileNames = fileName.split(",");
            if (fileNames.length > 0) {
                for (int i = 0; i < fileNames.length; i++) {
                    String currentUrl = fileNames[i].trim();
                    if (currentUrl.isEmpty()) {
                        continue;
                    }
                    String queryFileName = minioUtil.getUploadObjectUrl("ammes", fileNames[i], 60 * 60 * 24 * 7);

                    sb.append(queryFileName);
                    if (i != fileNames.length - 1) {
                        sb.append(",");
                    }
                }
            }
            workorder.setAdjuncts(sb.toString());
        }*/
        WorkorderRespVO workorderRespVO = WorkorderConvert.INSTANCE.convert(workorder);
        workorderRespVO.setRouteCode(workorder.getRouteCode());
        return success(workorderRespVO);
    }

    @GetMapping("/list")
    @Operation(summary = "获得生产工单列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<List<WorkorderRespVO>> getWorkorderList(@RequestParam("ids") Collection<Long> ids) {
        List<WorkorderDO> list = workorderService.getWorkorderList(ids);
        return success(WorkorderConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all")
    @Operation(summary = "获得生产工单列表")
    @Parameter(name = "mixinOrderId", description = "销售订单编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<List<WorkorderRespVO>> getWorkorderAllList(@Valid @RequestParam("mixinOrderId") Long mixinOrderId) {
        WorkorderListAllReqVO reqVO = new WorkorderListAllReqVO();
        reqVO.setMixinOrderId(mixinOrderId);
        List<WorkorderDO> list = workorderService.getWorkorderList(reqVO);
        return success(WorkorderConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得生产工单分页")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<PageResult<WorkorderRespPlusVO>> getWorkorderPage(@Valid WorkorderPageReqVO pageVO) {
        PageResult<WorkorderDO> pageResult = workorderService.getWorkorderPage(pageVO);
        List<WorkorderRespPlusVO> workorderRespPlusVOList = new ArrayList<>();

        for (WorkorderDO workorderDO : pageResult.getList()) {
            TaskExportReqVO taskExportReqVO = new TaskExportReqVO();
            taskExportReqVO.setWorkorderId(workorderDO.getId());
            List<TaskDO> taskList = taskService.getTaskList(taskExportReqVO);
            // 澳美MES不存在子母工单关联关系
            // List<WorkorderDO> workorder = workorderService.getWorkderByParentId(BigInteger.valueOf(workorderDO.getId()));
            WorkorderRespPlusVO bean = BeanUtil.toBean(workorderDO, WorkorderRespPlusVO.class);
            bean.setTaskNum(taskList.size());
            bean.setGenerated(taskList.size());
            /*List<WorkorderRespVO> workorderRespVOS = WorkorderConvert.INSTANCE.convertList(workorder);
            for (WorkorderRespVO workorderRespVO : workorderRespVOS) {
                taskExportReqVO.setWorkorderId(workorderRespVO.getId());
                List<TaskDO> taskList1 = taskService.getTaskList(taskExportReqVO);
                workorderRespVO.setGenerated(taskList1.size());
            }
            bean.setWorkorderDOList(workorderRespVOS);*/
            bean.setIsPrint(printLogApi.selectAllByPrintLog(workorderDO.getWorkorderCode()).size());
            workorderRespPlusVOList.add(bean);
        }
        PageResult<WorkorderRespPlusVO> result = new PageResult<>();
        result.setList(workorderRespPlusVOList);
        result.setTotal(pageResult.getTotal());
        return success(result);
    }

    @GetMapping("/summaryBomPage")
    @Operation(summary = "获得生产工单分页")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<PageResult<WorkorderRespPlusVO>> getWorkorderContainBomPage(@Valid WorkorderPageReqVO pageVO) {
        PageResult<WorkorderDO> pageResult = workorderService.getWorkorderPage(pageVO);
        List<WorkorderRespPlusVO> workorderRespPlusVOList = new ArrayList<>();
        for (WorkorderDO workorderDO : pageResult.getList()) {
            // 基于工单初始化BOM
            List<WorkorderRespPlusVO> finBomList = null;
            List<WorkorderBomDO> workorderBomList = workorderBomService.getWorkorderBomList(new WorkorderBomExportReqVO().setWorkorderId(workorderDO.getId()));
            // 开始找寻工单领用进度信息
            FeedLinePageReqVO feedLinePageReqVO = new FeedLinePageReqVO();
            feedLinePageReqVO.setWorkorderCode(workorderDO.getWorkorderCode());
            List<FeedLineSummaryVO> feedLineSummaryList = feedLineService.selectMaterialUsageSummaryList(feedLinePageReqVO);
            WorkorderRespPlusVO bean = BeanUtil.toBean(workorderDO, WorkorderRespPlusVO.class);
            bean.setWorkorderBomList(feedLineSummaryList);
            workorderRespPlusVOList.add(bean);
        }
        PageResult<WorkorderRespPlusVO> result = new PageResult<>();
        result.setList(workorderRespPlusVOList);
        result.setTotal(pageResult.getTotal());
        return success(result);
    }


    @GetMapping("/export-excel")
    @Operation(summary = "导出生产工单 Excel")
    // @PreAuthorize("@ss.hasPermission('pro:workorder:export')")
    @OperateLog(type = EXPORT)
    public void exportWorkorderExcel(@Valid WorkorderExportReqVO exportReqVO,
                                     HttpServletResponse response) throws IOException {
        List<WorkorderDO> list = workorderService.getWorkorderList(exportReqVO);
        // 导出 Excel
        List<WorkorderExcelVO> datas = WorkorderConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "生产工单.xls", "数据", WorkorderExcelVO.class, datas);
    }

    /**
     * 根据生产工单中的产品生成BOM物料行
     *
     * @param workorderId
     */
    private void generateBomLine(Long workorderId) {
        //先根据ID找到对应的产品
        WorkorderDO workorder = workorderService.getWorkorder(workorderId);

        //根据产品找到BOM组成
        List<MdProductBomDTO> boms = productBomApi.selectListByItemId((workorder.getProductId()));

        //生成BOM数据
        Double orderQuantitiy = workorder.getQuantity();
        WorkorderBomCreateReqVO workorderBom = new WorkorderBomCreateReqVO();
        if (CollUtil.isNotEmpty(boms)) {
            for (MdProductBomDTO bom : boms
            ) {
                workorderBom.setWorkorderId(workorderId);
                workorderBom.setItemId(bom.getBomItemId());
                workorderBom.setItemCode(bom.getBomItemCode());
                workorderBom.setItemName(bom.getBomItemName());
                workorderBom.setItemSpc(bom.getBomItemSpec());
                workorderBom.setItemOrProduct(bom.getItemOrProduct());
                workorderBom.setUnitOfMeasure(bom.getUnitOfMeasure());
                workorderBom.setQuantity(orderQuantitiy * bom.getQuantity());
                workorderBomService.createWorkorderBom(workorderBom);
            }
        }
    }

    /**
     * 删除当前工单下所有BOM组成
     *
     * @param workorderId
     */
    private void removeBomLine(Long workorderId) {
        workorderBomService.deleteProWorkorderBomByWorkorderId(workorderId);
    }

    @GetMapping("/count-all")
    @Operation(summary = "获得生产工单总数")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<Integer> CountAll() {
        WorkorderExportReqVO workorderExportReqVO = new WorkorderExportReqVO();
        List<WorkorderDO> list = workorderService.getWorkorderList(workorderExportReqVO);
        Integer result = list == null ? 0 : list.size();
        return success(result);
    }

    @GetMapping("/count-out-all")
    @Operation(summary = "获得委外工单总数")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<Integer> CountOutAll() {
        WorkorderExportReqVO workorderExportReqVO = new WorkorderExportReqVO();
        workorderExportReqVO.setOrderSource("3");
        List<WorkorderDO> list = workorderService.getWorkorderList(workorderExportReqVO);
        Integer result = list == null ? 0 : list.size();
        return success(result);
    }

    @PostMapping("/finshWorkorder")
    public CommonResult<String> wareHousing(@RequestBody List<WorkorderDO> workorderList) {
        for (WorkorderDO workorder : workorderList) {
            workorder.setStatus("FINISHED"); // 完成的工单不允许进行调拨，领料与报工
        }
        workorderService.updateBatch(workorderList);
        return success();
    }

    @GetMapping("/count-month-workorder-lastYear")
    @Operation(summary = "获取工单去年产出总额")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<Map<String, Integer>> CountMonthWorkorderLastYear() {
        return success(workorderService.getCountMonthWorkorderLastYear());
    }

    @GetMapping("/count-month-workorder-thisYear")
    @Operation(summary = "获取工单今年产出总额")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<Map<String, Integer>> CountMonthWorkorderThisYear() {
        return success(workorderService.getCountMonthWorkorderThisYear());
    }

    @GetMapping("/traceWorkorderPage")
    @Operation(summary = "获得生产工单分页")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<PageResult<WorkorderRespPlusVO>> traceWorkorderPage(@Valid WorkorderPageReqVO pageVO) {
        // 已知参数: 工单, 任务单, 批次号
        Set<String> workorderSet = new HashSet<String>();

        String workorderCode = pageVO.getWorkorderCode();
        String taskCode = pageVO.getTaskCode();
        String batchCode = pageVO.getBatchCode();

        if (workorderCode == null && taskCode == null && batchCode == null) {
            return success();
        }
        if (workorderCode != null && !"".equals(workorderCode)) {
            workorderSet.add(workorderCode);
        }
        if (taskCode != null && !"".equals(taskCode)) {
            // 基于任务单获取唯一工单号
            TaskDO taskInfo = Optional.ofNullable(taskService.getTaskList(new TaskExportReqVO().setTaskCode(taskCode)).get(0)).orElse(null);
            if (taskInfo != null) {
                workorderSet.add(taskInfo.getWorkorderCode());
            }
        }
        if (batchCode != null && batchCode.startsWith("TASK")) {
            // 找寻报工信息
            // 基于报工信息获取工单号
            FeedbackDO feedbackDO = Optional.ofNullable(feedbackService.getFeedbackList(new FeedbackExportReqVO().setBatchCode(batchCode)).get(0)).orElse(null);
            if (feedbackDO != null) {
                workorderSet.add(feedbackDO.getWorkorderCode());
            }
        }
        List<String> workorderList = new ArrayList<>(workorderSet);
        if (workorderList.isEmpty()) {
            return success();
        }
        pageVO.setWorkorderList(workorderList);
        pageVO.setBatchCode(null); // 工单不对应批次信息, 清空
        PageResult<WorkorderDO> pageResult = workorderService.getWorkorderPage(pageVO);
        List<WorkorderRespPlusVO> workorderRespPlusVOList = new ArrayList<>();
        for (WorkorderDO workorderDO : pageResult.getList()) {
            TaskExportReqVO taskExportReqVO = new TaskExportReqVO();
            taskExportReqVO.setWorkorderId(workorderDO.getId());
            List<TaskDO> taskList = taskService.getTaskList(taskExportReqVO);
            List<WorkorderDO> workorder = workorderService.getWorkderByParentId(BigInteger.valueOf(workorderDO.getId()));
            WorkorderRespPlusVO bean = BeanUtil.toBean(workorderDO, WorkorderRespPlusVO.class);
            bean.setTaskNum(taskList.size());
            bean.setGenerated(taskList.size());
            List<WorkorderRespVO> workorderRespVOS = WorkorderConvert.INSTANCE.convertList(workorder);
            for (WorkorderRespVO workorderRespVO : workorderRespVOS) {
                taskExportReqVO.setWorkorderId(workorderRespVO.getId());
                List<TaskDO> taskList1 = taskService.getTaskList(taskExportReqVO);
                workorderRespVO.setGenerated(taskList1.size());
            }
            bean.setWorkorderDOList(workorderRespVOS);
            bean.setIsPrint(printLogApi.selectAllByPrintLog(workorderDO.getWorkorderCode()).size());
            workorderRespPlusVOList.add(bean);
        }

        PageResult<WorkorderRespPlusVO> result = new PageResult<>();
        result.setList(workorderRespPlusVOList);
        result.setTotal(pageResult.getTotal());

        return success(result);
    }


    private String extractNumber(String input) {
        if (input == null) {
            return null;
        }
        String regex = "[^0-9]+";
        return input.replaceAll(regex, "");
    }


    // 处理任务单对应的领料单行
    private void processTaskIssueLines(List<IssueLineDO> issueLines,
                                       Map<String, Object> processData,
                                       WorkorderDO workorder, TaskDO task) {
        for (IssueLineDO issueLine : issueLines) {

            String lineItemCode = issueLine.getItemCode();
            if (lineItemCode == null || lineItemCode.startsWith("301T")) {
                continue;
            }

            // 特殊工序过滤
            if ("AM005".equals(task.getProcessCode())) {
                String itemCode = issueLine.getItemCode();
                if (itemCode == null || !itemCode.startsWith("301Z")) {
                    continue;
                }
            }

            BigDecimal quantity = calculateConvertedQuantity(issueLine, workorder, task);
            double currentInput = (Double) processData.get("totalInput");
            processData.put("totalInput", currentInput + quantity.doubleValue());
        }
    }

    private BigDecimal calculateConvertedQuantity(IssueLineDO issueLine, WorkorderDO workorder, TaskDO task) {
        String unitOfMeasure = issueLine.getUnitOfMeasure();
        String specification = issueLine.getSpecification();
        BigDecimal quantity = issueLine.getQuantityIssued();

        if ("米".equals(unitOfMeasure)) {
            return quantity;
        }

        if ("吨".equals(unitOfMeasure)) {
            quantity = quantity.multiply(new BigDecimal(1000)); // 吨转公斤
        }

        try {
            String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
            RouteDO route = routeService.getRoute(routeCode);
            RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
            exportReqVO.setRouteId(route.getId());
            exportReqVO.setProcessCode(task.getProcessCode());

            List<RouteProcessDO> routeProcesses = routeProcessService.getRouteProcessList(exportReqVO);
            if (!routeProcesses.isEmpty()) {
                RouteProcessDO process = routeProcesses.get(0);
                String receiveUnit = process.getReceivingUnits();
                String outUnit = process.getOutUnits();
                BigDecimal numerator = process.getOutUnitsConversionNumerator();
                BigDecimal denominator = process.getOutUnitsConversionDenominator();

                if ("米".equals(outUnit) && !"公斤".equals(receiveUnit)) {
                    // 按 * 拆分规格，取最后一段作为 kg/m
                    if (specification != null && specification.contains("*")) {
                        String[] specifications = specification.split("\\*");
                        List<String> numbers = Arrays.stream(specifications)
                                .map(this::extractNumber)
                                .collect(Collectors.toList());

                        String quantitative = numbers.size() > 0 ? numbers.get(0) : null;
                        String wide = numbers.size() > 1 ? numbers.get(1) : null;

                        double quantitativeInt = 1.0;
                        double wideInt = 1.0;

                        if (quantitative != null) {
                            quantitativeInt = Integer.parseInt(quantitative) / 1000.0;
                        }
                        if (wide != null) {
                            wideInt = Integer.parseInt(wide) / 1000.0;
                        }
                        quantity = quantity
                                .divide(BigDecimal.valueOf(quantitativeInt), 10, RoundingMode.HALF_UP)
                                .divide(BigDecimal.valueOf(wideInt), 1, RoundingMode.HALF_UP);
                    }
                } else if ("米".equals(outUnit)) {
                    // 公斤转米
                    // quantity = quantity.multiply(denominator);
                    if (denominator == null || denominator.compareTo(BigDecimal.ZERO) == 0) {
                        denominator = BigDecimal.ONE;
                    }
                    BigDecimal conversionFactor = numerator.divide(denominator, 4, RoundingMode.HALF_UP);
                    quantity = quantity.divide(conversionFactor, 1, RoundingMode.HALF_UP);

                } else {
                    if (denominator == null || denominator.compareTo(BigDecimal.ZERO) == 0) {
                        denominator = BigDecimal.ONE;
                    }
                    BigDecimal conversionFactor = numerator.divide(denominator, 4, RoundingMode.HALF_UP);
                    quantity = quantity.multiply(conversionFactor);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return quantity;
    }

    private BigDecimal calculateFeedbackConvertedQuantity(WorkorderDO workorder, TaskDO task) {
        BigDecimal conversionFactor = BigDecimal.ONE;
        try {
            String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
            RouteDO route = routeService.getRoute(routeCode);
            RouteProcessExportReqVO exportReqVO = new RouteProcessExportReqVO();
            exportReqVO.setRouteId(route.getId());
            exportReqVO.setProcessCode(task.getProcessCode());
            List<RouteProcessDO> routeProcesses = routeProcessService.getRouteProcessList(exportReqVO);

            if (!routeProcesses.isEmpty()) {
                RouteProcessDO process = routeProcesses.get(0);
                String receiveUnit = process.getReceivingUnits();
                String outUnit = process.getOutUnits();
                BigDecimal numerator = process.getOutUnitsConversionNumerator();
                BigDecimal denominator = process.getOutUnitsConversionDenominator();

                if (denominator != null && denominator.compareTo(BigDecimal.ZERO) != 0) {
                    conversionFactor = numerator.divide(denominator, 4, RoundingMode.HALF_UP);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return conversionFactor;
    }

    @Operation(summary = "工单工序合格率查询")
    @GetMapping("/calculateQualityBatch")
    public CommonResult<List<JSONObject>> calculateQualityBatch(@RequestParam(value = "workorderCode", required = false) String workorderCode,
                                                                @RequestParam(value = "beginTime", required = false) String beginTime,
                                                                @RequestParam(value = "endTime", required = false) String endTime,
                                                                @RequestParam(value = "processCode", required = false) String processCodeStr) {

        WorkorderListVO reqVO = new WorkorderListVO();
        reqVO.setWorkorderCode(workorderCode);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate beginDate = LocalDate.parse(beginTime, formatter);
        LocalDateTime beginDateTime = beginDate.atStartOfDay();

        LocalDate endDate = LocalDate.parse(endTime, formatter);
        LocalDateTime endDateTime = endDate.atStartOfDay();

        if (beginDateTime != null && endDateTime != null) {
            reqVO.setCreateTime(new LocalDateTime[]{beginDateTime, endDateTime});
        }

        // 获取工单ID列表
        List<Long> workorderIds = new ArrayList<>();

        // 获取工单ID列表
        if( StringUtils.isNotBlank(processCodeStr) ){
            workorderIds = getWorkorderIdsByCriteria(reqVO , processCodeStr);
        }else {
            workorderIds = getWorkorderIdsByCriteria(reqVO);
        }

        if (workorderIds.isEmpty()) {
            return error(ErrorCodeConstants.WORKORDER_CODE_EXISTS);
        }

        // 批量计算工单合格率
        Map<Long, Map<String, Map<String, Object>>> qualityData = calculateQualityBatchForIds(workorderIds);

        // 构建响应数据
        List<JSONObject> dataList = new ArrayList<>();
        for (Map.Entry<Long, Map<String, Map<String, Object>>> workorderEntry : qualityData.entrySet()) {
            WorkorderDO workorder = workorderService.getWorkorder(workorderEntry.getKey());
            if (workorder == null) continue;

            String workorderKey = workorder.getWorkorderCode();
            Map<String, Map<String, Object>> processMap = workorderEntry.getValue();

            for (Map.Entry<String, Map<String, Object>> processEntry : processMap.entrySet()) {
                Map<String, Object> processInfo = processEntry.getValue();

                // 测试工序过滤
                if (StringUtils.isNotBlank(processCodeStr) && !processInfo.get("processCode").equals(processCodeStr) ) {
                    continue;
                }

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("workorderCode", workorderKey);
                jsonObject.put("taskCode", processInfo.get("taskCode"));
                jsonObject.put("processCode", processInfo.get("processCode"));
                jsonObject.put("processName", processInfo.get("processName"));

                BigDecimal totalInput = new BigDecimal((Double) processInfo.get("totalInput"));
                jsonObject.put("totalInput", totalInput.setScale(2, RoundingMode.HALF_UP).doubleValue());

                BigDecimal totalQualified = new BigDecimal((Double) processInfo.get("totalQualified"));
                jsonObject.put("totalQualified", totalQualified.setScale(2, RoundingMode.HALF_UP).doubleValue());

                BigDecimal passRate = new BigDecimal((Double) processInfo.get("passRate"));
                jsonObject.put("passRate", passRate.setScale(2, RoundingMode.HALF_UP).doubleValue() + "%");

                dataList.add(jsonObject);
            }
        }
        return success(dataList);
    }

    /**
     * 批量计算多个工单的合格率数据
     *
     * @param workorderIds 工单ID列表
     * @return Map<工单ID, Map < 工序标识, 工序数据>>
     */
    private Map<Long, Map<String, Map<String, Object>>> calculateQualityBatchForIds(List<Long> workorderIds) {
        // 并行处理工单计算
        return workorderIds.parallelStream().collect(Collectors.toConcurrentMap(
                workorderId -> workorderId,
                workorderId -> {
                    try {
                        return calculateSingleWorkorderQuality(workorderId);
                    } catch (Exception e) {
                        return Collections.emptyMap();
                    }
                }
        ));
    }

    /**
     * 计算单个工单的合格率数据
     */
    private Map<String, Map<String, Object>> calculateSingleWorkorderQuality(Long workorderId) {
        // 获取工单信息
        WorkorderDO workorder = workorderService.getWorkorder(workorderId);
        if (workorder == null) {
            return Collections.emptyMap();
        }

        List<TaskDO> tasks = taskService.getTaskList(new TaskExportReqVO().setWorkorderId(workorderId));
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyMap();
        }

        // 创建结果Map: <工序标识, 工序数据>
        Map<String, Map<String, Object>> resultMap = new HashMap<>();

        // 1. 基于任务单计算领料总额
        calculateInputByTasks(workorder, tasks, resultMap);

        // 2. 基于报工单计算合格数量
        calculateQualifiedByFeedbacks(workorder, tasks, resultMap);

        // 如果没有数据，返回空结果
        if (resultMap.isEmpty()) {
            return Collections.emptyMap();
        }

        // 计算各工序合格率
        resultMap.forEach((processKey, data) -> {
            double totalInput = (Double) data.get("totalInput");
            double totalQualified = (Double) data.get("totalQualified");
            BigDecimal originRate = Optional.ofNullable(data.get("originRate"))
                    .map(obj -> (BigDecimal) obj)
                    .orElse(BigDecimal.ZERO);

            // 特殊工序处理
            if (processKey.contains("AM001") && originRate.doubleValue() > 0.55) {
                totalInput = totalInput * 2;
            }

            double passRate = totalInput > 0 ? (totalQualified / totalInput) * 100 : 0.0;
            data.put("passRate", passRate);
        });

        return resultMap;
    }

    /**
     * 基于任务单计算领料总额
     */
    private void calculateInputByTasks(WorkorderDO workorder, List<TaskDO> tasks,
                                       Map<String, Map<String, Object>> resultMap) {

        // 收集所有任务单编码
        Set<String> taskCodes = tasks.stream()
                .map(TaskDO::getTaskCode)
                .collect(Collectors.toSet());

        if (taskCodes.isEmpty()) {
            return;
        }

        // 批量获取任务单对应的生产领料单（1对1关系）
        Map<String, IssueHeaderDO> issueHeadByTaskCode = issueHeaderService.getIssueHeadListByTaskCodes(new ArrayList<>(taskCodes));

        // 收集所有领料单ID
        Set<Long> issueHeadIds = issueHeadByTaskCode.values().stream()
                .map(IssueHeaderDO::getId)
                .collect(Collectors.toSet());

        if (issueHeadIds.isEmpty()) {
            return;
        }

        // 批量获取领料单行数据，按领料单ID分组
        Map<Long, List<IssueLineDO>> issueLinesByHeadId = issueLineService.getIssueLineListByHeadIds(new ArrayList<>(issueHeadIds));

        // 处理每个任务单的领料数据
        for (TaskDO task : tasks) {
            String taskCode = task.getTaskCode();
            String processCode = task.getProcessCode();
            String processName = task.getProcessName();

            // 使用复合键区分不同工序
            String processKey = processCode + "|" + processName;

            // 初始化工序数据
            Map<String, Object> processData = resultMap.computeIfAbsent(processKey, k -> {
                Map<String, Object> data = new HashMap<>();
                data.put("processCode", processCode);
                data.put("processName", processName);
                data.put("taskCode", taskCode);
                data.put("workorder", workorder);
                data.put("task", task);
                data.put("totalInput", 0.0);
                data.put("totalQualified", 0.0);
                return data;
            });

            // 获取任务单对应的领料单（1对1关系）
            IssueHeaderDO issueHead = issueHeadByTaskCode.get(taskCode);
            if (issueHead == null) {
                continue;
            }

            // 获取领料单行数据
            List<IssueLineDO> issueLinesForHead = issueLinesByHeadId.get(issueHead.getId());
            if (issueLinesForHead == null || issueLinesForHead.isEmpty()) {
                continue;
            }

            // 处理领料单行数据
            processTaskIssueLines(issueLinesForHead, processData, workorder, task);
        }
    }


    /**
     * 基于报工单计算合格数量
     */
    private void calculateQualifiedByFeedbacks(WorkorderDO workorder, List<TaskDO> tasks,
                                               Map<String, Map<String, Object>> resultMap) {
        // 收集所有任务编码
        Set<String> taskCodes = tasks.stream()
                .map(TaskDO::getTaskCode)
                .collect(Collectors.toSet());

        if (taskCodes.isEmpty()) {
            return;
        }

        // 批量获取所有任务对应的报工记录
        Map<String, List<FeedbackDO>> feedbacksByTaskCode = feedbackService.getFeedbackListByTaskCods(
                        new ArrayList<>(taskCodes)).stream()
                .collect(Collectors.groupingBy(FeedbackDO::getTaskCode));

        // 处理每个任务单的报工数据
        for (TaskDO task : tasks) {
            String taskCode = task.getTaskCode();
            String processCode = task.getProcessCode();
            String processName = task.getProcessName();

            String processKey = processCode + "|" + processName;

            // 获取当前任务的报工记录
            List<FeedbackDO> feedbacks = feedbacksByTaskCode.get(taskCode);
            if (feedbacks == null || feedbacks.isEmpty()) {
                continue;
            }

            long count = feedbacks.stream().filter(f -> "AM001".equals(f.getProcessCode()) &&
                            f.getOriginCode() != null &&
                            !f.getOriginCode().isEmpty())
                    .count();

            BigDecimal originRate = feedbacks.isEmpty() ? BigDecimal.ZERO :
                    BigDecimal.valueOf(count).divide(BigDecimal.valueOf(feedbacks.size()), 2, RoundingMode.HALF_UP);

            Map<String, Object> processData = resultMap.computeIfAbsent(processKey, k -> {
                Map<String, Object> data = new HashMap<>();
                data.put("processCode", processCode);
                data.put("processName", processName);
                data.put("taskCode", taskCode);
                data.put("workorder", workorder);
                data.put("task", task);
                data.put("totalInput", 0.0);
                data.put("totalQualified", 0.0);
                data.put("totalUnQualified", 0.0);
                return data;
            });

            processData.put("originRate", originRate);

            BigDecimal convertedQuantity = BigDecimal.ONE;
            if ("AM006".equals(task.getProcessCode())) {
                convertedQuantity = calculateFeedbackConvertedQuantity(workorder, task);
            }

            // 累加合格数量
            for (FeedbackDO feedback : feedbacks) {
                double currentQualified = (Double) processData.get("totalQualified");
                double currentUnQualified = (Double) Optional.ofNullable(processData.get("totalUnQualified")).orElse(0.0);

                // 分切工序单位转换
                if ("张".equals(feedback.getUnitOfMeasure())) {
                    BigDecimal finalChangeQuantity = BigDecimal.valueOf(feedback.getQuantityQualified())
                            .multiply(convertedQuantity);

                    BigDecimal finalChangeUnQuantity = BigDecimal.valueOf(feedback.getQuantityUnquanlified())
                            .multiply(convertedQuantity);

                    processData.put("totalQualified", currentQualified + finalChangeQuantity.doubleValue());
                    processData.put("totalUnQualified", currentUnQualified + finalChangeUnQuantity.doubleValue());
                } else {
                    processData.put("totalQualified", currentQualified + feedback.getQuantityQualified());
                    processData.put("totalUnQualified", currentUnQualified + feedback.getQuantityUnquanlified());
                }
            }
        }
    }

    // 根据查询条件获取工单ID列表
    private List<Long> getWorkorderIdsByCriteria(WorkorderListVO reqVO) {
        if (reqVO.getId() != null) {
            return Collections.singletonList(reqVO.getId());
        }
        List<WorkorderDO> workorders = workorderService.getWorkorderList(reqVO);
        return workorders.stream()
                .map(WorkorderDO::getId)
                .collect(Collectors.toList());
    }

    private List<Long> getWorkorderIdsByCriteria(WorkorderListVO reqVO, String processCode) {
        if (reqVO.getId() != null) {
            return Collections.singletonList(reqVO.getId());
        }

        if (processCode == null) {
            return Collections.emptyList();
        }

        List<WorkorderDO> workorders = workorderService.getWorkorderList(reqVO);
        if (workorders == null || workorders.isEmpty()) {
            return Collections.emptyList();
        }

        List<WorkorderDO> finWorkorders = new ArrayList<>();
        // 循环工单列表, 校验对应任务单是否包含所属工序
        for (WorkorderDO workorder : workorders) {
            // 基于工单获取任务单
            List<TaskDO> taskList = taskService.getTaskList(new TaskExportReqVO().setWorkorderCode(workorder.getWorkorderCode()));
            if (taskList == null) {
                continue;
            }

            List<TaskDO> filteredTasks = taskList.stream().filter(task -> task != null && processCode.equals(task.getProcessCode())).collect(Collectors.toList());
            if (!filteredTasks.isEmpty()) {
                finWorkorders.add(workorder);
            }
        }

        return finWorkorders.stream()
                .map(WorkorderDO::getId)
                .collect(Collectors.toList());
    }


    // TODO: 追加物料类型合格率表
    @Operation(summary = "产品合格率报表")
    @GetMapping("/productQualityReport")
    public CommonResult<List<JSONObject>> getProductQualityReport(
            @RequestParam(value = "beginTime", required = false) String beginTimeStr,
            @RequestParam(value = "endTime", required = false) String endTimeStr) {

        // 考虑到看板也需要使用合格率报表信息, 且无法传参. 故再此配置默认参数
        /*if (beginTimeStr == null || endTimeStr == null) {
            return error(ErrorCodeConstants.TIME_NOT_COMPLETE);
        }*/

        /*if (beginTimeStr == null || beginTimeStr.trim().isEmpty()) {
            LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
            beginTimeStr = firstDayOfMonth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        if (endTimeStr == null || endTimeStr.trim().isEmpty()) {
            endTimeStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginDateTime = LocalDateTime.parse(beginTimeStr, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endTimeStr, formatter);*/

        // 初始化时间范围
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 处理开始时间
        LocalDateTime beginDateTime = StringUtils.isBlank(beginTimeStr) ? LocalDate.now().withDayOfMonth(1).atStartOfDay() : parseDateTime(beginTimeStr, true);

        // 处理结束时间
        LocalDateTime endDateTime = StringUtils.isBlank(endTimeStr) ? LocalDateTime.now() : parseDateTime(endTimeStr, false);

        // 获取所有产品分类
        List<MdItemCategoryDO> categories = mdItemCategoryService.getMdItemCategoryList(new MdItemCategoryExportReqVO());

        // 去重
        categories = categories.stream().distinct().collect(Collectors.toList());
        if (CollUtil.isEmpty(categories)) {
            return success(Collections.emptyList());
        }

        List<JSONObject> result = new ArrayList<>();

        for (MdItemCategoryDO category : categories) {
            JSONObject categoryReport = calculateCategoryQuality(category, beginDateTime, endDateTime);
            if (categoryReport != null) {
                result.add(categoryReport);
            }
        }
       return success(result);
    }

    /**
     * 计算单个产品分类的合格率
     */
    private JSONObject calculateCategoryQuality(MdItemCategoryDO category,
                                                LocalDateTime beginTime,
                                                LocalDateTime endTime) {
        // 1. 基于物料开头码和时间范围获取相关工单
        List<WorkorderDO> workorders = getWorkordersByItemPrefix(category.getItemCategoryCode(), beginTime, endTime);

        if (CollUtil.isEmpty(workorders)) {
            return null;
        }

        // 2. 获取标准工序流程
        List<String> standardProcesses = parseProcessFlow(category.getItemRouteCode());
        if (CollUtil.isEmpty(standardProcesses)) {
            return null;
        }

        // 3. 获取实际工艺路线
        Map<String, List<String>> actualProcessRoutes = getActualProcessRoutes(workorders);

        // 4. 计算合格率
        return calculateProcessQuality(workorders, standardProcesses, actualProcessRoutes, category);
    }

    /**
     * 基于物料开头码获取工单
     */
    private List<WorkorderDO> getWorkordersByItemPrefix(String itemPrefix,
                                                        LocalDateTime beginTime,
                                                        LocalDateTime endTime) {
        WorkorderListVO exportReqVO = new WorkorderListVO();
        exportReqVO.setCreateTime(new LocalDateTime[]{beginTime, endTime});
        List<WorkorderDO> allWorkorders = workorderService.getWorkorderList(exportReqVO);

        return allWorkorders.stream()
                .filter(workorder -> workorder.getProductCode() != null
                        && workorder.getProductCode().startsWith(itemPrefix)
                        && workorder.getWorkorderCode().startsWith("AMGD01"))
                .collect(Collectors.toList());
    }

    /**
     * 解析工序流程字符串
     */
    private List<String> parseProcessFlow(String processFlow) {
        if (StringUtils.isBlank(processFlow)) {
            return Collections.emptyList();
        }
        return Arrays.stream(processFlow.split("->"))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    /**
     * 获取实际工艺路线
     */
    private Map<String, List<String>> getActualProcessRoutes(List<WorkorderDO> workorders) {
        Map<String, List<String>> processRoutes = new HashMap<>();

        for (WorkorderDO workorder : workorders) {
            String routeCode = workorder.getProductCode() + "-" + workorder.getRouteCode();
            RouteDO route = routeService.getRoute(routeCode);
            if (route != null) {
                // 获取该工艺路线的所有工序
                List<RouteProcessDO> routeProcesses = routeProcessService.getRouteProcessList(
                        new RouteProcessExportReqVO().setRouteId(route.getId()));
                // 按照项次sequence排序
                List<String> processes = routeProcesses.stream()
                        .sorted(Comparator.comparing(RouteProcessDO::getSequence))
                        .map(RouteProcessDO::getProcessCode)
                        .collect(Collectors.toList());

                processRoutes.put(workorder.getProductCode(), processes);
            }
        }
        return processRoutes;
    }

    /**
     * 计算工序合格率
     */
    private JSONObject calculateProcessQuality(List<WorkorderDO> workorders,
                                               List<String> standardProcesses,
                                               Map<String, List<String>> actualProcessRoutes,
                                               MdItemCategoryDO category) {
        JSONObject result = new JSONObject();
        result.put("categoryName", category.getItemCategoryName());
        result.put("itemPrefix", category.getItemCategoryCode());
        result.put("standardProcessFlow", category.getItemRouteCode());
        result.put("standardProcessFlowStr", category.getItemRoute());

        // 收集所有工单ID
        List<Long> workorderIds = workorders.stream()
                .map(WorkorderDO::getId)
                .collect(Collectors.toList());


        // 批量计算所有工单的合格率数据
        Map<Long, Map<String, Map<String, Object>>> allQualityData =
                calculateQualityBatchForWorkOrderIds(workorderIds, standardProcesses);

        // 分类统计匹配工序和非匹配工序
        Map<String, QualityStats> matchedProcessStats = new HashMap<>();
        Map<String, QualityStats> unmatchedProcessStats = new HashMap<>();

        for (WorkorderDO workorder : workorders) {

             Map<String, Map<String, Object>> workorderQuality = allQualityData.get(workorder.getId());
            if (workorderQuality == null) {
                continue;
            }

            List<String> actualProcesses = actualProcessRoutes.get(workorder.getProductCode());
            if (actualProcesses == null) {
                continue;
            }

            // 找出匹配和不匹配的工序
            List<String> matchedProcesses = findMatchedProcesses(standardProcesses, actualProcesses);
            List<String> unmatchedProcesses = findUnmatchedProcesses(standardProcesses, actualProcesses);

            List<ProcessDO> processList = processService.getcessList(new ProcessExportReqVO());
            Map<String, String> processCodeToNameMap = processList.stream()
                    .collect(Collectors.toMap(
                            ProcessDO::getProcessCode,
                            ProcessDO::getProcessName
                    ));

            // 统计匹配工序
            for (String process : matchedProcesses) {
                // 基于当前的process, 去processList中找寻相同processCode的行. 然后将process追加processName
                String processName = processCodeToNameMap.get(process);
                Map<String, Object> processData = workorderQuality.get(process + "|" + processName);
                if (processData != null) {
                    updateQualityStats(matchedProcessStats, process, processData, standardProcesses);
                }
            }

            // 统计不匹配工序
            for (String process : unmatchedProcesses) {
                String processName = processCodeToNameMap.get(process);
                Map<String, Object> extra = (Map<String, Object>) workorderQuality.get("extraResult");
                // 追加extra不为空的判定
                if (extra != null) {
                    for (Map.Entry<String, Object> entry : extra.entrySet()) {
                        String extraProcess = entry.getKey();
                        // 校验当前获取的extraProcess与当前process是否一致
                        if (extraProcess.split("\\|")[1].equals(process)) {
                            Map<String, Object> value = (Map<String, Object>) entry.getValue();
                            Map<String, Object> processData = (Map<String, Object>) value.get(process + "|" + processName);
                            if (processData != null) {
                                updateQualityStats(unmatchedProcessStats, process, processData, standardProcesses);
                            }
                        }
                    }
                }
            }
        }

        // 计算总合格率
        double totalQualifiedRate = calculateTotalQualifiedRate(matchedProcessStats, unmatchedProcessStats);
        result.put("totalQualifiedRate", String.format("%.2f%%", totalQualifiedRate));

        // 匹配工序合格率
        JSONObject matchedStats = buildProcessStats(matchedProcessStats);
        result.put("matchedProcessStats", matchedStats);

        double matchedProcesstotalInput = 0.0;
        double matchedProcesstotalQualified = 0.0;

        for (Map.Entry<String, QualityStats> entry : matchedProcessStats.entrySet()) {
            QualityStats stats = entry.getValue();
            if (stats == null) {
                continue;
            }
            // all: 独立工序 计算投入与产出
            // input: 首工序 计算投入
            // qualified: 末工序 计算产出
            double totalInput = "all".equals(stats.type) || "input".equals(stats.type) ? Optional.ofNullable(stats.totalInput).orElse(0.0) : 0.0;
            double totalQualified = "all".equals(stats.type) || !"input".equals(stats.type) ? Optional.ofNullable(stats.totalQualified).orElse(0.0) : 0.0;

            matchedProcesstotalInput += totalInput;
            matchedProcesstotalQualified += totalQualified;
        }


        // 追加主流程总投入与总产出信息
        result.put("matchedProcesstotalInput", matchedProcesstotalInput);
        result.put("matchedProcesstotalQualified", matchedProcesstotalQualified);

        // 非匹配工序合格率
        JSONObject unmatchedStats = buildProcessStats(unmatchedProcessStats);
        result.put("unmatchedProcessStats", unmatchedStats);

        // 产品数量统计
        result.put("productCount", workorders.size());
        result.put("workorderCount", workorders.size());

        return result;
    }

    /**
     * 找出匹配的工序（在标准工序中）
     */
    private List<String> findMatchedProcesses(List<String> standardProcesses, List<String> actualProcesses) {
        // 将标准工序转换为Set，提高查找效率
        Set<String> standardSet = new HashSet<>(standardProcesses);
        Set<String> matched = new HashSet<>();

        for (String process : actualProcesses) {
            // 电晕算分条，剥离算分切
            String normalizedProcess = process;
            if ("AM007".equals(process)) {
                normalizedProcess = "AM006";
            } else if ("AM008".equals(process)) {
                normalizedProcess = "AM004";
            }
            // 检查标准化后的工序是否存在于标准工序中
            if (standardSet.contains(normalizedProcess)) {
                matched.add(normalizedProcess);
            }
        }
        List<String> matchedList = new ArrayList<>(matched);
        Collections.sort(matchedList, Comparator.comparing(String::toString));
        return matchedList;
    }


    /**
     * 找出不匹配的工序
     */
    private List<String> findUnmatchedProcesses(List<String> standardProcesses, List<String> actualProcesses) {
        List<String> unmatched = new ArrayList<>(standardProcesses);
        unmatched.removeAll(findMatchedProcesses(standardProcesses, actualProcesses));
        return unmatched;
    }

    /**
     * 更新工序统计信息
     */
    private void updateQualityStats(Map<String, QualityStats> statsMap, String process, Map<String, Object> processData, List<String> standardProcesses) {
        QualityStats stats = statsMap.computeIfAbsent(process, k -> new QualityStats());

        Collections.sort(standardProcesses);
        double input = Optional.ofNullable((Double) processData.get("totalInput")).orElse(0.0);
        double qualified = Optional.ofNullable((Double) processData.get("totalQualified")).orElse(0.0);
        WorkorderDO workorderDO = (WorkorderDO) processData.get("workorder");
        TaskDO taskDO = (TaskDO) processData.get("task");
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("task", taskDO);
        taskMap.put("process", process);
        taskMap.put("processName", taskDO.getProcessName());
        taskMap.put("qualified", String.format("%.2f", qualified));
        taskMap.put("input", String.format("%.2f", input));
        // 如果input为0, qualifiedRate自动返回0
        if (input == 0) {
            taskMap.put("qualifiedRate", "0.00");
        } else {
            // 判定当前的工序是否为AM001, 且比率是否大于1.8. 若是, 则将投入*2 再次计算合格率
            if ("AM001".equals(process) && (qualified / input) > 1.8) {
                input *= 2;
            }
            taskMap.put("qualifiedRate", (double) qualified / input);
        }
        stats.workorders.add(workorderDO);
        stats.workorderProcessData.put(workorderDO.getWorkorderCode(), taskMap);

        // 只有当input和qualified都不为0时才参与汇总计算
        boolean shouldCalculate = input != 0 && qualified != 0;
        if (shouldCalculate) {
            stats.totalInput += input;
            stats.totalQualified += qualified;
        }

        // 判定standardProcesses长度是否为1, 为1则只有涂布工序, 计算投入与产出
        /*stats.totalInput += input;
        stats.totalQualified += qualified;*/

        if (standardProcesses.size() == 1) {
            /*stats.totalInput += input;
            stats.totalQualified += qualified;*/
            stats.type = "all";
        } else {
            if (standardProcesses.indexOf(process) != 0) {
                // stats.totalQualified += qualified;
                stats.type = "qualified";
            }
            if (standardProcesses.indexOf(process) != standardProcesses.size() - 1) {
                // stats.totalInput += input;
                stats.type = "input";
            }
        }

        stats.count++;
    }

    /**
     * 计算总合格率
     */
    private double calculateTotalQualifiedRate(Map<String, QualityStats> matchedStats,
                                               Map<String, QualityStats> unmatchedStats) {
        double totalInput = 0;
        double totalQualified = 0;

        // 使用entrySet()遍历，可以同时获取key和value
        for (Map.Entry<String, QualityStats> entry : matchedStats.entrySet()) {
            String processCode = entry.getKey();
            QualityStats stats = entry.getValue();

            // 使用Optional进行非空校验
            double currentInput = Optional.ofNullable(stats.totalInput).orElse(0.0);
            double currentQualified = Optional.ofNullable(stats.totalQualified).orElse(0.0);

            // 当key为AM001且合格率大于0.6时，投入数量翻倍
            if ("AM001".equals(processCode) && currentInput > 0) {
                double rate = currentQualified / currentInput;
                if (rate > 1.8) {
                    currentInput *= 2;
                }
            }

            totalInput += currentInput;
            totalQualified += currentQualified;
        }

        /*for (QualityStats stats : unmatchedStats.values()) {
            totalInput += stats.totalInput;
            totalQualified += stats.totalQualified;
        }*/

        return totalInput > 0 ? (totalQualified / totalInput) * 100 : 0.0;
    }


    /**
     * 构建工序统计结果
     */
    private JSONObject buildProcessStats(Map<String, QualityStats> statsMap) {
        JSONObject result = new JSONObject();

        for (Map.Entry<String, QualityStats> entry : statsMap.entrySet()) {
            QualityStats stats = entry.getValue();
            double rate = stats.totalQualified / stats.totalInput;
            if (entry.getKey().equals("AM001") && rate > 1.8) {
                stats.totalInput = stats.totalInput * 2;
            }

            double qualifiedRate = stats.totalInput > 0 ?
                    (stats.totalQualified / stats.totalInput) * 100 : 0.0;

            JSONObject processStat = new JSONObject();
            processStat.put("qualifiedRate", String.format("%.2f%%", qualifiedRate));
            processStat.put("totalInput", String.format("%.2f", stats.totalInput));
            processStat.put("totalQualified", String.format("%.2f", stats.totalQualified));
            processStat.put("count", stats.count);
            processStat.put("workorders", stats.workorders);
            processStat.put("workorderProcessData", stats.workorderProcessData);

            result.put(entry.getKey(), processStat);
        }

        return result;
    }

    /**
     * 工序统计辅助类
     */
    private static class QualityStats {
        double totalInput = 0;
        double totalQualified = 0;
        int count = 0;
        String type = null;
        Set<WorkorderDO> workorders = new HashSet<>();
        Map<String, Map<String, Object>> workorderProcessData = new HashMap<>();
    }

    /**
     * 批量计算多个工单产品的合格率数据,
     *
     * @param workorderIds 工单ID列表
     * @return Map<工单ID, Map < 工序标识, 工序数据>>
     */
    private Map<Long, Map<String, Map<String, Object>>> calculateQualityBatchForWorkOrderIds(List<Long> workorderIds, List<String> standardProcesses) {
        // 并行处理工单计算
        return workorderIds.parallelStream().collect(Collectors.toConcurrentMap(
                workorderId -> workorderId,
                workorderId -> {
                    try {
                        return calculateSingleWorkorderProductQuality(workorderId, standardProcesses);
                    } catch (Exception e) {
                        return Collections.emptyMap();
                    }
                }
        ));
    }

    private Map<String, Map<String, Object>> calculateSingleWorkorderProductQuality(Long workorderId, List<String> standardProcesses) {

        // 1. 并行获取基础数据
        CompletableFuture<WorkorderDO> workorderFuture = CompletableFuture.supplyAsync(() ->
                workorderService.getWorkorder(workorderId));

        // 预先获取工单所有任务，避免多次查询
        CompletableFuture<List<TaskDO>> allTasksFuture = CompletableFuture.supplyAsync(() ->
                taskService.getTaskList(new TaskExportReqVO().setWorkorderId(workorderId)));

        WorkorderDO workorder = workorderFuture.join();
        if (workorder == null) {
            return Collections.emptyMap();
        }

        // 获取工艺路线
        String itemRouteCode = workorder.getProductCode() + '-' + workorder.getRouteCode();
        RouteDO routeDO = routeService.getRouteByCode(itemRouteCode);
        if (routeDO == null) {
            return Collections.emptyMap();
        }

        List<RouteProcessDO> routeDetails = routeProcessService.getRouteProcessList(
                new RouteProcessExportReqVO().setRouteId(routeDO.getId()));
        if (routeDetails.isEmpty()) {
            return Collections.emptyMap();
        }

        routeDetails.sort(Comparator.comparing(RouteProcessDO::getSequence));
        Set<String> routeProcessCodes = routeDetails.stream()
                .map(RouteProcessDO::getProcessCode)
                .collect(Collectors.toSet());

        RouteProcessDO firstProcess = routeDetails.get(0);
        RouteProcessDO lastProcess = routeDetails.get(routeDetails.size() - 1);

        List<TaskDO> allTasks = allTasksFuture.join();
        Map<String, List<TaskDO>> tasksByProcessCode = allTasks.stream()
                .collect(Collectors.groupingBy(TaskDO::getProcessCode));

        List<TaskDO> issueTasks = tasksByProcessCode.getOrDefault(firstProcess.getProcessCode(), Collections.emptyList());
        List<TaskDO> feedbackTasks = tasksByProcessCode.getOrDefault(lastProcess.getProcessCode(), Collections.emptyList());

        if (issueTasks.isEmpty() || feedbackTasks.isEmpty()) {
            return Collections.emptyMap();
        }

        // 主工序计算结果
        Map<String, Map<String, Object>> resultMap = new HashMap<>();

        // 首工序投入
        calculateInputByTasks(workorder, issueTasks, resultMap);
        // 判定feedbackTasks中是否包含issueTasks列表中的任务单号. 若存在则剔除. 将最终结果保存至临时的list. 基于临时的list进行末工序投入查询
        List<TaskDO> feedbackTasksTemp = new ArrayList<>();
        List<TaskDO> issueTasksTemp = new ArrayList<>();
        for (TaskDO task : feedbackTasks) {
            if (!issueTasks.contains(task)) {
                feedbackTasksTemp.add(task);
            }
        }
        if(!feedbackTasksTemp.isEmpty()){
            // 末工序投入
            calculateInputByTasks(workorder, feedbackTasksTemp, resultMap);
        }

        // 追加末工序投入查询
        // calculateInputByTasks(workorder, feedbackTasks, resultMap);
        // 首工序产出查询
        // 末工序产出查询
        // calculateQualifiedByFeedbacks(workorder, issueTasks, resultMap);
        calculateQualifiedByFeedbacks(workorder, feedbackTasks, resultMap);
        for (TaskDO task : issueTasks) {
            if (!feedbackTasks.contains(task)) {
                issueTasksTemp.add(task);
            }
        }
        if(!issueTasksTemp.isEmpty()){
            calculateQualifiedByFeedbacks(workorder, issueTasksTemp, resultMap);
        }


        if (!resultMap.isEmpty()) {
            // 并行计算合格率
            resultMap.entrySet().parallelStream().forEach(entry -> {
                Map<String, Object> data = entry.getValue();
                double totalInput = (Double) data.get("totalInput");
                double totalQualified = (Double) data.get("totalQualified");
                BigDecimal originRate = Optional.ofNullable(data.get("originRate"))
                        .map(obj -> (BigDecimal) obj)
                        .orElse(BigDecimal.ZERO);

                if (entry.getKey().contains("AM001") && originRate.doubleValue() > 0.55) {
                    totalInput = totalInput * 2;
                }

                double passRate = totalInput > 0 ? (totalQualified / totalInput) * 100 : 0.0;
                data.put("passRate", passRate);
            });
        }

        // 优化额外工序处理
        List<String> extraProcesses = standardProcesses.stream()
                .filter(process -> !routeProcessCodes.contains(process))
                .collect(Collectors.toList());

        Map<String, Object> extraResult = new HashMap<>();

        if (!extraProcesses.isEmpty()) {
            // 批量获取BOM信息
            List<WorkorderBomDO> workorderBomList = workorderBomService.getWorkorderBomList(
                    new WorkorderBomExportReqVO().setWorkorderId(workorderId));

            List<WorkorderBomDO> bomList = workorderBomList.stream()
                    .filter(bom -> bom.getItemCode().startsWith("6"))
                    .collect(Collectors.toList());

            if (!bomList.isEmpty()) {
                // 批量获取相关工单信息
                Set<String> itemCodes = bomList.stream()
                        .map(WorkorderBomDO::getItemCode)
                        .collect(Collectors.toSet());

                // 使用现有方法但批量处理
                List<WorkorderDO> allBomWorkorders = new ArrayList<>();
                for (String itemCode : itemCodes) {
                    List<WorkorderDO> bomWorkorders = workorderService.getWorkorderList(
                            new WorkorderListVO().setProductCode(itemCode));
                    allBomWorkorders.addAll(bomWorkorders);
                }

                // 批量获取额外工序任务
                if (!allBomWorkorders.isEmpty()) {
                    Set<Long> bomWorkorderIds = allBomWorkorders.stream()
                            .map(WorkorderDO::getId)
                            .collect(Collectors.toSet());

                    // 使用现有方法批量查询任务
                    List<TaskDO> allExtraTasks = new ArrayList<>();
                    for (Long bomWorkorderId : bomWorkorderIds) {
                        for (String extraProcess : extraProcesses) {
                            List<TaskDO> tasks = taskService.getTaskList(
                                    new TaskExportReqVO()
                                            .setWorkorderId(bomWorkorderId)
                                            .setProcessCode(extraProcess));
                            allExtraTasks.addAll(tasks);
                        }
                    }

                    // 构建任务查找表
                    Map<String, List<TaskDO>> extraTasksMap = allExtraTasks.stream()
                            .collect(Collectors.groupingBy(task ->
                                    task.getWorkorderId() + "|" + task.getProcessCode()));

                    // 处理额外工序计算
                    for (WorkorderBomDO bom : bomList) {
                        for (WorkorderDO bomWork : allBomWorkorders) {
                            if (bomWork.getProductCode().equals(bom.getItemCode())) {
                                for (String extraProcess : extraProcesses) {
                                    String taskKey = bomWork.getId() + "|" + extraProcess;
                                    List<TaskDO> extraTasks = extraTasksMap.get(taskKey);

                                    if (extraTasks != null && !extraTasks.isEmpty()) {
                                        Map<String, Map<String, Object>> extraResultMap = new HashMap<>();

                                        calculateInputByTasks(bomWork, extraTasks, extraResultMap);
                                        calculateQualifiedByFeedbacks(bomWork, extraTasks, extraResultMap);

                                        extraResultMap.forEach((processKey, data) -> {
                                            double totalInput = (Double) data.get("totalInput");
                                            double totalQualified = (Double) data.get("totalQualified");
                                            BigDecimal originRate = Optional.ofNullable(data.get("originRate"))
                                                    .map(obj -> (BigDecimal) obj)
                                                    .orElse(BigDecimal.ZERO);

                                            if (processKey.contains("AM001") && originRate.doubleValue() > 0.55) {
                                                totalInput = totalInput * 2;
                                            }

                                            double passRate = totalInput > 0 ? (totalQualified / totalInput) * 100 : 0.0;
                                            data.put("passRate", passRate);
                                        });

                                        extraResult.put(bomWork.getWorkorderCode() + "|" + extraProcess, extraResultMap);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (!extraResult.isEmpty()) {
            resultMap.put("extraResult", extraResult);
        }

        return resultMap;
    }

    @Operation(summary = "班组合格率报表")
    @GetMapping("/teamQualityReport")
    public CommonResult<List<JSONObject>> getTeamQualityReport(
            @RequestParam(value = "beginTime", required = false) String beginTimeStr,
            @RequestParam(value = "endTime", required = false) String endTimeStr,
            @RequestParam(value = "workorderCode", required = false) String workorderCodeStr,
            @RequestParam(value = "processCode", required = false) String processCodeStr) {

        // 初始化时间范围
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 处理开始时间
        LocalDateTime beginDateTime = StringUtils.isBlank(beginTimeStr) ? LocalDate.now().withDayOfMonth(1).atStartOfDay() : parseDateTime(beginTimeStr, true);

        // 处理结束时间
        LocalDateTime endDateTime = StringUtils.isBlank(endTimeStr) ? LocalDateTime.now() : parseDateTime(endTimeStr, false);

        String workorderCode = Optional.ofNullable(workorderCodeStr).orElse(null);

        WorkorderListVO reqVO = new WorkorderListVO();
        reqVO.setCreateTime(new LocalDateTime[]{beginDateTime, endDateTime});
        reqVO.setWorkorderCode(workorderCode);

        List<Long> workorderIds = new ArrayList<>();

        // 获取工单ID列表
        if(processCodeStr != null){
            workorderIds = getWorkorderIdsByCriteria(reqVO , processCodeStr);
        }else {
            workorderIds = getWorkorderIdsByCriteria(reqVO);
        }


        // 批量计算工单合格率
        Map<Long, Map<String, Map<String, Object>>> qualityData = calculateQualityBatchForIds(workorderIds);

        // 提前收集所有需要查询的数据，批量查询
        Set<String> taskCodes = new HashSet<>();
        Set<Long> feedbackIds = new HashSet<>();

        // 收集所有需要查询的taskCode
        for (Map.Entry<Long, Map<String, Map<String, Object>>> workorderEntry : qualityData.entrySet()) {
            Map<String, Map<String, Object>> processMap = workorderEntry.getValue();
            for (Map.Entry<String, Map<String, Object>> processEntry : processMap.entrySet()) {
                Map<String, Object> processInfo = processEntry.getValue();
                // 测试工序过滤
                if (processCodeStr != null && !processInfo.get("processCode").equals(processCodeStr) ) {
                    continue;
                }

                taskCodes.add(String.valueOf(processInfo.get("taskCode")));
            }
        }

        // 批量查询所有任务单对应的报工单
        Map<String, List<FeedbackDO>> taskFeedbackMap = new HashMap<>();
        if (!taskCodes.isEmpty()) {
            List<FeedbackDO> allFeedbackList = feedbackService.getFeedbackListByTaskCodes(
                    new ArrayList<>(taskCodes), beginDateTime, endDateTime);

            // 过滤并分组
            allFeedbackList.stream()
                    .filter(feedback -> feedback.getMergeFlag() == null)
                    .forEach(feedback -> {
                        taskFeedbackMap.computeIfAbsent(feedback.getTaskCode(), k -> new ArrayList<>())
                                .add(feedback);
                        feedbackIds.add(feedback.getId());
                    });
        }

        // 批量查询所有报工单的班组成员
        Map<Long, List<FeedbackMemberDO>> feedbackMemberMap = new HashMap<>();
        if (!feedbackIds.isEmpty()) {
            List<FeedbackMemberDO> allMembers = feedBackMemberService.getFeedbackMembersByFeedbackIds(
                    new ArrayList<>(feedbackIds));

            allMembers.forEach(member -> {
                feedbackMemberMap.computeIfAbsent(Long.valueOf(member.getFeedbackId()), k -> new ArrayList<>())
                        .add(member);
            });
        }

        // 批量查询所有报工单的缺陷明细
        Map<Long, List<FeedbackDefectDO>> feedbackDefectMap = new HashMap<>();
        if (!feedbackIds.isEmpty()) {
            List<FeedbackDefectDO> allDefects = feedbackDefectService.getFeedbackDefectsByFeedbackIds(
                    new ArrayList<>(feedbackIds));

            allDefects.forEach(defect -> {
                feedbackDefectMap.computeIfAbsent(Long.valueOf(defect.getFeedbackId()), k -> new ArrayList<>())
                        .add(defect);
            });
        }

        // 构建响应数据 - 按照班组成员进行分组
        List<JSONObject> dataList = new ArrayList<>();

        for (Map.Entry<Long, Map<String, Map<String, Object>>> workorderEntry : qualityData.entrySet()) {
            Long workorderId = workorderEntry.getKey();
            WorkorderDO workorder = workorderService.getWorkorder(workorderEntry.getKey());
            if (workorder == null) continue;

            Map<String, Map<String, Object>> processMap = workorderEntry.getValue();

            for (Map.Entry<String, Map<String, Object>> processEntry : processMap.entrySet()) {
                Map<String, Object> processInfo = processEntry.getValue();

                // 测试工序过滤
                if (processCodeStr != null && !processInfo.get("processCode").equals(processCodeStr) ) {
                    continue;
                }

                String taskCode = String.valueOf(processInfo.get("taskCode"));

                JSONObject taskObject = new JSONObject();
                taskObject.put("workorderCode", workorder.getWorkorderCode());
                taskObject.put("taskCode", processInfo.get("taskCode"));
                taskObject.put("processCode", processInfo.get("processCode"));
                taskObject.put("processName", processInfo.get("processName"));

                // 处理数值格式
                BigDecimal totalInput = new BigDecimal((Double) processInfo.get("totalInput"));
                taskObject.put("totalInput", totalInput.setScale(2, RoundingMode.HALF_UP).doubleValue());

                BigDecimal totalQualified = new BigDecimal((Double) processInfo.get("totalQualified"));
                taskObject.put("totalQualified", totalQualified.setScale(2, RoundingMode.HALF_UP).doubleValue());

                BigDecimal totalUnQualified = new BigDecimal((Double) processInfo.get("totalUnQualified"));
                taskObject.put("totalUnQualified", totalUnQualified.setScale(2, RoundingMode.HALF_UP).doubleValue());

                BigDecimal passRate = new BigDecimal((Double) processInfo.get("passRate"));
                taskObject.put("passRate", passRate.setScale(2, RoundingMode.HALF_UP).doubleValue() + "%");

                // 获取该任务单下的所有报工单
                List<FeedbackDO> feedbackList = taskFeedbackMap.getOrDefault(taskCode, Collections.emptyList());

                // 按照班组成员进行分组
                // 使用成员组合的字符串作为分组key
                Map<String, JSONObject> teamMemberGroupMap = new HashMap<>();
                Map<String, List<FeedbackDO>> feedbackGroupMap = new HashMap<>();

                for (FeedbackDO feedbackDO : feedbackList) {
                    List<FeedbackMemberDO> members = feedbackMemberMap.getOrDefault(feedbackDO.getId(),
                            Collections.emptyList());

                    // 创建班组成员的唯一标识
                    // 将成员按userName排序后拼接成字符串，确保相同成员的组合得到相同的key
                    String memberKey = createMemberKey(members);

                    // 如果没有班组成员，使用"无成员"作为key
                    if (memberKey.isEmpty()) {
                        memberKey = "无成员";
                    }

                    // 记录该报工属于哪个组
                    feedbackGroupMap.computeIfAbsent(memberKey, k -> new ArrayList<>()).add(feedbackDO);

                    // 如果该组还没有创建，则创建班组信息
                    if (!teamMemberGroupMap.containsKey(memberKey)) {
                        JSONObject teamInfo = new JSONObject();

                        // 获取班组编码（如果可能的话）
                        String teamCode = feedbackDO.getTeamCode();
                        teamInfo.put("teamCode", StringUtils.isNotBlank(teamCode) ? teamCode : "未知班组");
                        teamInfo.put("teamName", StringUtils.isNotBlank(teamCode) ? teamCode : "未知班组");

                        // 保存班组成员信息
                        List<JSONObject> memberList = new ArrayList<>();
                        for (FeedbackMemberDO member : members) {
                            JSONObject memberInfo = new JSONObject();
                            memberInfo.put("nickName", member.getNickName());
                            memberInfo.put("userName", member.getUserName());
                            memberInfo.put("quantity", member.getQuantity());
                            memberInfo.put("userId", member.getUserId());
                            memberList.add(memberInfo);
                        }
                        teamInfo.put("memberList", memberList);

                        // 生成班组显示名称（包含成员信息）
                        String displayName = generateTeamDisplayName(teamCode, members);
                        teamInfo.put("displayName", displayName);

                        // 生成成员key（用于识别）
                        teamInfo.put("memberKey", memberKey);

                        teamMemberGroupMap.put(memberKey, teamInfo);
                    }
                }

                // 构建班组级别的数据
                List<JSONObject> teamResult = new ArrayList<>();

                for (Map.Entry<String, JSONObject> teamEntry : teamMemberGroupMap.entrySet()) {
                    String memberKey = teamEntry.getKey();
                    JSONObject teamInfo = teamEntry.getValue();
                    List<FeedbackDO> teamFeedbacks = feedbackGroupMap.get(memberKey);

                    // 计算班组总合格数
                    double teamTotalQualified = teamFeedbacks.stream()
                            .mapToDouble(FeedbackDO::getQuantityQualified)
                            .sum();
                    teamInfo.put("teamTotalQualified", teamTotalQualified);

                    double teamTotalUnQualified = teamFeedbacks.stream()
                            .mapToDouble(FeedbackDO::getQuantityUnquanlified)
                            .sum();
                    teamInfo.put("teamTotalUnQualified", teamTotalUnQualified);

                    // 计算班组合格率（班组总合格数/任务总投入）
                    double teamPassRate = totalInput.doubleValue() > 0
                            ? (teamTotalQualified / totalInput.doubleValue()) * 100
                            : 0;
                    teamInfo.put("teamPassRate", String.format("%.2f%%", teamPassRate));

                    // 构建班组下的报工明细
                    List<JSONObject> feedbackResult = new ArrayList<>();
                    AtomicReference<Double> teamMemberTotal = new AtomicReference<>((double) 0); // 班组成员总产量

                    for (FeedbackDO feedbackDO : teamFeedbacks) {
                        JSONObject feedbackInfo = new JSONObject();
                        feedbackInfo.put("feedbackCode", feedbackDO.getFeedbackCode());
                        feedbackInfo.put("taskCode", feedbackDO.getTaskCode());
                        feedbackInfo.put("batchCode", feedbackDO.getBatchCode());
                        feedbackInfo.put("workorderCode", feedbackDO.getWorkstationCode());
                        feedbackInfo.put("teamCode", feedbackDO.getTeamCode());
                        feedbackInfo.put("quantityQualified", feedbackDO.getQuantityQualified());
                        feedbackInfo.put("quantityUnQualified", feedbackDO.getQuantityUnquanlified());
                        feedbackInfo.put("machineryName", feedbackDO.getMachineryName());
                        feedbackInfo.put("machineryCode", feedbackDO.getMachineryCode());

                        // 计算报工合格率（合格数/任务总投入）
                        double feedbackPassRate = totalInput.doubleValue() > 0
                                ? (feedbackDO.getQuantityQualified() / totalInput.doubleValue()) * 100
                                : 0;
                        feedbackInfo.put("feedbackPassRate", String.format("%.2f%%", feedbackPassRate));

                        // 获取班组成员数据
                        List<JSONObject> teamMemberResult = new ArrayList<>();
                        List<FeedbackMemberDO> members = feedbackMemberMap.getOrDefault(feedbackDO.getId(),
                                Collections.emptyList());

                        members.forEach(member -> {
                            JSONObject teamMemberResultItem = new JSONObject();
                            teamMemberResultItem.put("feedbackId", feedbackDO.getId());
                            teamMemberResultItem.put("nickName", member.getNickName());
                            teamMemberResultItem.put("userName", member.getUserName());
                            teamMemberResultItem.put("quantity", member.getQuantity());
                            teamMemberResult.add(teamMemberResultItem);

                            // 累加班组成员总产量
                            teamMemberTotal.updateAndGet(v -> new Double( (new BigDecimal(v).add(member.getQuantity()) ).doubleValue()));
                        });

                        feedbackInfo.put("teamMemberResult", teamMemberResult);

                        // 获取缺陷明细数据
                        List<JSONObject> defectResult = new ArrayList<>();
                        List<FeedbackDefectDO> defects = feedbackDefectMap.getOrDefault(feedbackDO.getId(),
                                Collections.emptyList());

                        defects.forEach(defect -> {
                            JSONObject defectItem = new JSONObject();
                            defectItem.put("defectName", defect.getDefectName());
                            defectItem.put("defectQuantity", defect.getDefectMeter());
                            defectItem.put("defectReason", defect.getDefectName());
                            defectItem.put("processName", defect.getProcessName());
                            defectItem.put("originTeamCode", defect.getOriginTeamCode());
                            defectItem.put("originBatchCode", defect.getOriginBatchCode());
                            defectItem.put("originFeedbackCode", defect.getOriginFeedbackCode());
                            defectResult.add(defectItem);
                        });

                        feedbackInfo.put("defectResult", defectResult);
                        feedbackResult.add(feedbackInfo);
                    }

                    // 计算班组成员总产量占班组总合格数的比例（用于验证数据一致性）
                    teamInfo.put("teamMemberTotal", teamMemberTotal);
                    teamInfo.put("feedbackResult", feedbackResult);
                    teamResult.add(teamInfo);
                }

                taskObject.put("teamResult", teamResult);
                dataList.add(taskObject);
            }
        }

        return success(dataList);
    }

    @Operation(summary = "结案班组合格率报表")
    @GetMapping("/closeTeamQualityReport")
    public CommonResult<List<JSONObject>> getCloseTeamQualityReport(
            @RequestParam(value = "beginTime", required = false) String beginTimeStr,
            @RequestParam(value = "endTime", required = false) String endTimeStr,
            @RequestParam(value = "workorderCode", required = false) String workorderCodeStr,
            @RequestParam(value = "processCode", required = false) String processCodeStr) {

        // 初始化时间范围
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // 处理开始时间
        LocalDateTime beginDateTime = StringUtils.isBlank(beginTimeStr) ? LocalDate.now().withDayOfMonth(1).atStartOfDay() : parseDateTime(beginTimeStr, true);

        // 处理结束时间
        LocalDateTime endDateTime = StringUtils.isBlank(endTimeStr) ? LocalDateTime.now() : parseDateTime(endTimeStr, false);

        String workorderCode = Optional.ofNullable(workorderCodeStr).orElse(null);

        WorkorderListVO reqVO = new WorkorderListVO();
        reqVO.setUpdateTime(new LocalDateTime[]{beginDateTime, endDateTime});
        reqVO.setWorkorderCode(workorderCode);
        // 只查找结案的工单
        reqVO.setStatus("FINISHED");
        reqVO.setCloseFlag("Y");

        List<Long> workorderIds = new ArrayList<>();

        // 获取工单ID列表
        if(processCodeStr != null){
            workorderIds = getWorkorderIdsByCriteria(reqVO , processCodeStr);
        }else {
            workorderIds = getWorkorderIdsByCriteria(reqVO);
        }


        // 批量计算工单合格率
        Map<Long, Map<String, Map<String, Object>>> qualityData = calculateQualityBatchForIds(workorderIds);

        // 提前收集所有需要查询的数据，批量查询
        Set<String> taskCodes = new HashSet<>();
        Set<Long> feedbackIds = new HashSet<>();

        // 收集所有需要查询的taskCode
        for (Map.Entry<Long, Map<String, Map<String, Object>>> workorderEntry : qualityData.entrySet()) {
            Map<String, Map<String, Object>> processMap = workorderEntry.getValue();
            for (Map.Entry<String, Map<String, Object>> processEntry : processMap.entrySet()) {
                Map<String, Object> processInfo = processEntry.getValue();
                // 测试工序过滤
                if (processCodeStr != null && !processInfo.get("processCode").equals(processCodeStr) ) {
                    continue;
                }

                taskCodes.add(String.valueOf(processInfo.get("taskCode")));
            }
        }

        // 批量查询所有任务单对应的报工单
        Map<String, List<FeedbackDO>> taskFeedbackMap = new HashMap<>();
        if (!taskCodes.isEmpty()) {
            List<FeedbackDO> allFeedbackList = feedbackService.getFeedbackListByTaskCodes(
                    new ArrayList<>(taskCodes), beginDateTime, endDateTime);

            // 过滤并分组
            allFeedbackList.stream()
                    .filter(feedback -> feedback.getMergeFlag() == null)
                    .forEach(feedback -> {
                        taskFeedbackMap.computeIfAbsent(feedback.getTaskCode(), k -> new ArrayList<>())
                                .add(feedback);
                        feedbackIds.add(feedback.getId());
                    });
        }

        // 批量查询所有报工单的班组成员
        Map<Long, List<FeedbackMemberDO>> feedbackMemberMap = new HashMap<>();
        if (!feedbackIds.isEmpty()) {
            List<FeedbackMemberDO> allMembers = feedBackMemberService.getFeedbackMembersByFeedbackIds(
                    new ArrayList<>(feedbackIds));

            allMembers.forEach(member -> {
                feedbackMemberMap.computeIfAbsent(Long.valueOf(member.getFeedbackId()), k -> new ArrayList<>())
                        .add(member);
            });
        }

        // 批量查询所有报工单的缺陷明细
        Map<Long, List<FeedbackDefectDO>> feedbackDefectMap = new HashMap<>();
        if (!feedbackIds.isEmpty()) {
            List<FeedbackDefectDO> allDefects = feedbackDefectService.getFeedbackDefectsByFeedbackIds(
                    new ArrayList<>(feedbackIds));

            allDefects.forEach(defect -> {
                feedbackDefectMap.computeIfAbsent(Long.valueOf(defect.getFeedbackId()), k -> new ArrayList<>())
                        .add(defect);
            });
        }

        // 构建响应数据 - 按照班组成员进行分组
        List<JSONObject> dataList = new ArrayList<>();

        for (Map.Entry<Long, Map<String, Map<String, Object>>> workorderEntry : qualityData.entrySet()) {
            Long workorderId = workorderEntry.getKey();
            WorkorderDO workorder = workorderService.getWorkorder(workorderEntry.getKey());
            if (workorder == null) continue;

            Map<String, Map<String, Object>> processMap = workorderEntry.getValue();

            for (Map.Entry<String, Map<String, Object>> processEntry : processMap.entrySet()) {
                Map<String, Object> processInfo = processEntry.getValue();

                // 测试工序过滤
                if (processCodeStr != null && !processInfo.get("processCode").equals(processCodeStr) ) {
                    continue;
                }

                String taskCode = String.valueOf(processInfo.get("taskCode"));

                JSONObject taskObject = new JSONObject();
                taskObject.put("workorderCode", workorder.getWorkorderCode());
                taskObject.put("taskCode", processInfo.get("taskCode"));
                taskObject.put("processCode", processInfo.get("processCode"));
                taskObject.put("processName", processInfo.get("processName"));

                // 处理数值格式
                Object totalInputObj = processInfo.get("totalInput");
                BigDecimal totalInput = BigDecimal.ZERO;

                if (totalInputObj != null) {
                    totalInput = new BigDecimal(totalInputObj.toString());
                    taskObject.put("totalInput", totalInput.setScale(2, RoundingMode.HALF_UP).doubleValue());
                } else {
                    taskObject.put("totalInput", 0.00);
                }

                Object totalQualifiedObj = processInfo.get("totalQualified");
                if (totalQualifiedObj != null) {
                    BigDecimal totalQualified = new BigDecimal(totalQualifiedObj.toString());
                    taskObject.put("totalQualified", totalQualified.setScale(2, RoundingMode.HALF_UP).doubleValue());
                } else {
                    taskObject.put("totalQualified", 0.00);
                }

                Object totalUnQualifiedObj = processInfo.get("totalUnQualified");
                if (totalUnQualifiedObj != null) {
                    BigDecimal totalUnQualified = new BigDecimal(totalUnQualifiedObj.toString());
                    taskObject.put("totalUnQualified", totalUnQualified.setScale(2, RoundingMode.HALF_UP).doubleValue());
                } else {
                    taskObject.put("totalUnQualified", 0.00);
                }

                Object passRateObj = processInfo.get("passRate");
                if (passRateObj != null) {
                    BigDecimal passRate = new BigDecimal(passRateObj.toString());
                    taskObject.put("passRate", passRate.setScale(2, RoundingMode.HALF_UP).doubleValue() + "%");
                } else {
                    taskObject.put("passRate", "0.00%");
                }

                // 获取该任务单下的所有报工
                List<FeedbackDO> feedbackList = taskFeedbackMap.getOrDefault(taskCode, Collections.emptyList());

                // 按照班组成员进行分组
                // 使用成员组合的字符串作为分组key
                Map<String, JSONObject> teamMemberGroupMap = new HashMap<>();
                Map<String, List<FeedbackDO>> feedbackGroupMap = new HashMap<>();

                for (FeedbackDO feedbackDO : feedbackList) {
                    List<FeedbackMemberDO> members = feedbackMemberMap.getOrDefault(feedbackDO.getId(),
                            Collections.emptyList());

                    // 创建班组成员的唯一标识
                    // 将成员按userName排序后拼接成字符串，确保相同成员的组合得到相同的key
                    String memberKey = createMemberKey(members);

                    // 如果没有班组成员，使用"无成员"作为key
                    if (memberKey.isEmpty()) {
                        memberKey = "无成员";
                    }

                    // 记录该报工属于哪个组
                    feedbackGroupMap.computeIfAbsent(memberKey, k -> new ArrayList<>()).add(feedbackDO);

                    // 如果该组还没有创建，则创建班组信息
                    if (!teamMemberGroupMap.containsKey(memberKey)) {
                        JSONObject teamInfo = new JSONObject();

                        // 获取班组编码（如果可能的话）
                        String teamCode = feedbackDO.getTeamCode();
                        teamInfo.put("teamCode", StringUtils.isNotBlank(teamCode) ? teamCode : "未知班组");
                        teamInfo.put("teamName", StringUtils.isNotBlank(teamCode) ? teamCode : "未知班组");

                        // 保存班组成员信息
                        List<JSONObject> memberList = new ArrayList<>();
                        for (FeedbackMemberDO member : members) {
                            JSONObject memberInfo = new JSONObject();
                            memberInfo.put("nickName", member.getNickName());
                            memberInfo.put("userName", member.getUserName());
                            memberInfo.put("quantity", member.getQuantity());
                            memberInfo.put("userId", member.getUserId());
                            memberList.add(memberInfo);
                        }
                        teamInfo.put("memberList", memberList);

                        // 生成班组显示名称（包含成员信息）
                        String displayName = generateTeamDisplayName(teamCode, members);
                        teamInfo.put("displayName", displayName);

                        // 生成成员key（用于识别）
                        teamInfo.put("memberKey", memberKey);

                        teamMemberGroupMap.put(memberKey, teamInfo);
                    }
                }

                // 构建班组级别的数据
                List<JSONObject> teamResult = new ArrayList<>();

                for (Map.Entry<String, JSONObject> teamEntry : teamMemberGroupMap.entrySet()) {
                    String memberKey = teamEntry.getKey();
                    JSONObject teamInfo = teamEntry.getValue();
                    List<FeedbackDO> teamFeedbacks = feedbackGroupMap.get(memberKey);

                    // 计算班组总合格数
                    double teamTotalQualified = teamFeedbacks.stream()
                            .mapToDouble(FeedbackDO::getQuantityQualified)
                            .sum();
                    teamInfo.put("teamTotalQualified", teamTotalQualified);

                    double teamTotalUnQualified = teamFeedbacks.stream()
                            .mapToDouble(FeedbackDO::getQuantityUnquanlified)
                            .sum();
                    teamInfo.put("teamTotalUnQualified", teamTotalUnQualified);

                    // 计算班组合格率（班组总合格数/任务总投入）
                    double teamPassRate = totalInput.doubleValue() > 0 ? (teamTotalQualified / totalInput.doubleValue()) * 100 : 0;
                    teamInfo.put("teamPassRate", String.format("%.2f%%", teamPassRate));

                    // 构建班组下的报工明细
                    List<JSONObject> feedbackResult = new ArrayList<>();
                    AtomicReference<Double> teamMemberTotal = new AtomicReference<>((double) 0); // 班组成员总产量

                    for (FeedbackDO feedbackDO : teamFeedbacks) {
                        JSONObject feedbackInfo = new JSONObject();
                        feedbackInfo.put("feedbackCode", feedbackDO.getFeedbackCode());
                        feedbackInfo.put("taskCode", feedbackDO.getTaskCode());
                        feedbackInfo.put("batchCode", feedbackDO.getBatchCode());
                        feedbackInfo.put("workorderCode", feedbackDO.getWorkstationCode());
                        feedbackInfo.put("teamCode", feedbackDO.getTeamCode());
                        feedbackInfo.put("quantityQualified", feedbackDO.getQuantityQualified());
                        feedbackInfo.put("quantityUnQualified", feedbackDO.getQuantityUnquanlified());
                        feedbackInfo.put("machineryName", feedbackDO.getMachineryName());
                        feedbackInfo.put("machineryCode", feedbackDO.getMachineryCode());

                        // 计算报工合格率（合格数/任务总投入）
                        double feedbackPassRate = totalInput.doubleValue() > 0
                                ? (feedbackDO.getQuantityQualified() / totalInput.doubleValue()) * 100
                                : 0;
                        feedbackInfo.put("feedbackPassRate", String.format("%.2f%%", feedbackPassRate));

                        // 获取班组成员数据
                        List<JSONObject> teamMemberResult = new ArrayList<>();
                        List<FeedbackMemberDO> members = feedbackMemberMap.getOrDefault(feedbackDO.getId(),
                                Collections.emptyList());

                        members.forEach(member -> {
                            JSONObject teamMemberResultItem = new JSONObject();
                            teamMemberResultItem.put("feedbackId", feedbackDO.getId());
                            teamMemberResultItem.put("nickName", member.getNickName());
                            teamMemberResultItem.put("userName", member.getUserName());
                            teamMemberResultItem.put("quantity", member.getQuantity());
                            teamMemberResult.add(teamMemberResultItem);

                            // 累加班组成员总产量
                            teamMemberTotal.updateAndGet(v -> new Double( (new BigDecimal(v).add(member.getQuantity()) ).doubleValue()));
                        });

                        feedbackInfo.put("teamMemberResult", teamMemberResult);

                        // 获取缺陷明细数据
                        List<JSONObject> defectResult = new ArrayList<>();
                        List<FeedbackDefectDO> defects = feedbackDefectMap.getOrDefault(feedbackDO.getId(),
                                Collections.emptyList());

                        defects.forEach(defect -> {
                            JSONObject defectItem = new JSONObject();
                            defectItem.put("defectName", defect.getDefectName());
                            defectItem.put("defectQuantity", defect.getDefectMeter());
                            defectItem.put("defectReason", defect.getDefectName());
                            defectItem.put("processName", defect.getProcessName());
                            defectItem.put("originTeamCode", defect.getOriginTeamCode());
                            defectItem.put("originBatchCode", defect.getOriginBatchCode());
                            defectItem.put("originFeedbackCode", defect.getOriginFeedbackCode());
                            defectResult.add(defectItem);
                        });

                        feedbackInfo.put("defectResult", defectResult);
                        feedbackResult.add(feedbackInfo);
                    }

                    // 计算班组成员总产量占班组总合格数的比例（用于验证数据一致性）
                    teamInfo.put("teamMemberTotal", teamMemberTotal);
                    teamInfo.put("feedbackResult", feedbackResult);
                    teamResult.add(teamInfo);
                }

                taskObject.put("teamResult", teamResult);
                dataList.add(taskObject);
            }
        }

        return success(dataList);
    }


    // 创建班组成员的唯一标识
    private String createMemberKey(List<FeedbackMemberDO> members) {
        if (members == null || members.isEmpty()) {
            return "";
        }

        // 按userName排序后拼接，确保相同成员的组合得到相同的key
        List<String> userNames = members.stream()
                .map(FeedbackMemberDO::getUserName)
                .sorted()
                .collect(Collectors.toList());

        return String.join(",", userNames);
    }

    // 生成班组显示名称
    private String generateTeamDisplayName(String teamCode, List<FeedbackMemberDO> members) {
        StringBuilder displayName = new StringBuilder();

        if (StringUtils.isNotBlank(teamCode)) {
            displayName.append(teamCode);
        }

        if (members != null && !members.isEmpty()) {
            displayName.append(" (");
            List<String> memberNames = members.stream()
                    .map(FeedbackMemberDO::getNickName)
                    .collect(Collectors.toList());
            displayName.append(String.join(", ", memberNames));
            displayName.append(")");
        }

        return displayName.toString();
    }


    // 辅助方法：解析日期时间
    private LocalDateTime parseDateTime(String dateTimeStr, boolean isBeginTime) {
        if (dateTimeStr.length() == 10) { // 只有日期的情况
            dateTimeStr = dateTimeStr + (isBeginTime ? " 00:00:00" : " 23:59:59");
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
