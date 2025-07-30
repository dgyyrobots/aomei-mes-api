package com.dofast.module.pro.controller.admin.workorder;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONObject;
import com.dofast.framework.common.util.io.MinioUtil;
import com.dofast.module.mes.api.ProductBomApi.ProductBomApi;
import com.dofast.module.mes.api.ProductBomApi.dto.MdProductBomDTO;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.api.TaskApi.TaskApi;
import com.dofast.module.pro.controller.admin.feedback.vo.FeedbackExportReqVO;
import com.dofast.module.pro.controller.admin.routeprocess.vo.RouteProcessExportReqVO;
import com.dofast.module.pro.controller.admin.task.vo.TaskExportReqVO;
import com.dofast.module.pro.controller.admin.task.vo.TaskRespVO;
import com.dofast.module.pro.controller.admin.workorderbom.vo.WorkorderBomBaseVO;
import com.dofast.module.pro.controller.admin.workorderbom.vo.WorkorderBomCreateReqVO;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import com.dofast.module.pro.dal.dataobject.task.TaskDO;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.route.RouteService;
import com.dofast.module.pro.service.routeprocess.RouteProcessService;
import com.dofast.module.pro.service.task.TaskService;
import com.dofast.module.pro.service.workorderbom.WorkorderBomService;
import com.dofast.module.report.api.PrintLog.PrintLogApi;
import com.dofast.module.wms.controller.admin.issueline.vo.IssueLineExportReqVO;
import com.dofast.module.wms.dal.dataobject.issueline.IssueLineDO;
import com.dofast.module.wms.service.issueline.IssueLineService;
import org.apache.commons.lang3.StringUtils;
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
import java.math.BigInteger;
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

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.pro.controller.admin.workorder.vo.*;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.convert.workorder.WorkorderConvert;
import com.dofast.module.pro.service.workorder.WorkorderService;

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
    private RouteService routeService;

    @Resource
    private RouteProcessService routeProcessService;

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
    public CommonResult<Boolean> updateAdjuncts( @RequestBody WorkorderUpdateReqVO updateReqVO) {
        WorkorderDO workorder = workorderService.getWorkorder(updateReqVO.getId());
        if(workorder == null){
            return success(true);
        }
        String url = updateReqVO.getAdjuncts();
        if(url==null){
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
            if( parts[parts.length - 1].contains("%")) {
                parts[parts.length - 1] = parts[parts.length - 1].substring(0, parts[parts.length - 1].indexOf("%"));
                if(parts[parts.length - 1].contains("?")){
                    parts[parts.length - 1] = parts[parts.length - 1].substring(0, parts[parts.length - 1].indexOf("?"));
                }
            }
            String finName = parts[parts.length - 1];
            System.out.println(finName);
            sb.append(finName);
            // 校验当前url是否为最后一个
            if (i != urls.length - 1) {
                sb.append(",");
            }
        }
        String adjuncts = Optional.ofNullable(sb.toString()).orElse("");
        if( adjuncts!="") {
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
        if (fileName != null) {
            String[] fileNames = fileName.split(",");
            if (fileNames.length > 0) {
                for (int i = 0; i < fileNames.length; i++) {
                    String currentUrl = fileNames[i].trim();
                    if (currentUrl.isEmpty()) {
                        continue;
                    }
                    String queryFileName = minioUtil.getUploadObjectUrl("ammes", fileNames[i], 60 * 60 * 24 * 7);
                    System.out.println(queryFileName);

                    sb.append(queryFileName);
                    if (i != fileNames.length - 1) {
                        sb.append(",");
                    }
                }
            }
            workorder.setAdjuncts(sb.toString());
        }
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

    /*@GetMapping("/page")
    @Operation(summary = "获得生产工单分页")
    @PreAuthorize("@ss.hasPermission('pro:workorder:query')")
    public CommonResult<PageResult<WorkorderRespVO>> getWorkorderPage(@Valid WorkorderPageReqVO pageVO) {
        PageResult<WorkorderDO> pageResult = workorderService.getWorkorderPage(pageVO);
        return success(WorkorderConvert.INSTANCE.convertPage(pageResult));
    }*/


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

    @GetMapping("/export-excel")
    @Operation(summary = "导出生产工单 Excel")
    @PreAuthorize("@ss.hasPermission('pro:workorder:export')")
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
    public CommonResult<String> wareHousing(@RequestBody WorkorderDO workorder) {
        workorder.setStatus("FINISHED"); // 完成的工单不允许进行调拨，领料与报工
        workorderService.updateWorkorder(WorkorderConvert.INSTANCE.convert1(workorder));
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







}
