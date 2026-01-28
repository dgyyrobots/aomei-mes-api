package com.dofast.module.pro.controller.admin.feedbackaudit;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dofast.framework.common.exception.ServiceException;
import com.dofast.framework.common.util.bean.BeanUtils;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.pro.controller.admin.feedbackauditdetail.vo.FeedbackAuditDetailExportReqVO;
import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.FeedbackAuditItemExportReqVO;
import com.dofast.module.pro.convert.feedback.FeedbackConvert;
import com.dofast.module.pro.convert.feedbackauditdetail.FeedbackAuditDetailConvert;
import com.dofast.module.pro.convert.feedbackaudititem.FeedbackAuditItemConvert;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import com.dofast.module.pro.enums.ErrorCodeConstants;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.feedbackauditdetail.FeedbackAuditDetailService;
import com.dofast.module.pro.service.feedbackaudititem.FeedbackAuditItemService;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLinePageReqVO;
import com.dofast.module.wms.controller.admin.feedline.vo.FeedLineSummaryVO;
import org.apache.poi.ss.formula.constant.ErrorConstant;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.LocalDateTime;
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

import com.dofast.module.pro.controller.admin.feedbackaudit.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;
import com.dofast.module.pro.convert.feedbackaudit.FeedbackAuditConvert;
import com.dofast.module.pro.service.feedbackaudit.FeedbackAuditService;
import org.springframework.web.servlet.View;

@Tag(name = "管理后台 - 报工审批主表")
@RestController
@RequestMapping("/mes/pro/feedback-audit")
@Validated
public class FeedbackAuditController {

    @Resource
    private FeedbackAuditService feedbackAuditService;

    @Resource
    private AutoCodeApi autoCodeApi;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private FeedbackAuditItemService feedbackAuditItemService;

    @Resource
    private FeedbackAuditDetailService feedbackAuditDetailService;

    @Resource
    private FeedbackService feedbackService;



    @PostMapping("/create")
    @Operation(summary = "创建报工审批主表")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:create')")
    public CommonResult<Long> createFeedbackAudit(@Valid @RequestBody FeedbackAuditCreateReqVO createReqVO) {
        return success(feedbackAuditService.createFeedbackAudit(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报工审批主表")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:update')")
    public CommonResult<Boolean> updateFeedbackAudit(@Valid @RequestBody FeedbackAuditUpdateReqVO updateReqVO) {
        feedbackAuditService.updateFeedbackAudit(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报工审批主表")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:delete')")
    public CommonResult<Boolean> deleteFeedbackAudit(@RequestParam("id") Long id) {
        feedbackAuditService.deleteFeedbackAudit(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报工审批主表")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:query')")
    public CommonResult<FeedbackAuditRespVO> getFeedbackAudit(@RequestParam("id") Long id) {
        FeedbackAuditDO feedbackAudit = feedbackAuditService.getFeedbackAudit(id);
        return success(FeedbackAuditConvert.INSTANCE.convert(feedbackAudit));
    }

    @GetMapping("/list")
    @Operation(summary = "获得报工审批主表列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:query')")
    public CommonResult<List<FeedbackAuditRespVO>> getFeedbackAuditList(@RequestParam("ids") Collection<Long> ids) {
        List<FeedbackAuditDO> list = feedbackAuditService.getFeedbackAuditList(ids);
        return success(FeedbackAuditConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报工审批主表分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:query')")
    public CommonResult<PageResult<FeedbackAuditRespVO>> getFeedbackAuditPage(@Valid FeedbackAuditPageReqVO pageVO) {
        // PageResult<FeedbackAuditDO> pageResult = feedbackAuditService.getFeedbackAuditPage(pageVO);
        IPage<FeedbackAuditRespVO> pageResult = feedbackAuditService.selectAuditList(pageVO);
        PageResult<FeedbackAuditRespVO> result = new PageResult<>();

        result.setList(pageResult.getRecords());
        result.setTotal(pageResult.getTotal());

        // 2025-8-4 审批单仅对应一个工序
        List<FeedbackAuditRespVO> resultList = result.getList().stream().peek(feedbackAuditDO -> {
            List<FeedbackAuditItemDO> feedbackAuditItemDOList = feedbackAuditItemService.getFeedbackAuditItemList(
                    new FeedbackAuditItemExportReqVO().setAuditId(String.valueOf(feedbackAuditDO.getId()))
            );
            if (!feedbackAuditItemDOList.isEmpty()) {
                FeedbackAuditItemDO firstItem = feedbackAuditItemDOList.get(0);
                feedbackAuditDO.setProcessCode(firstItem.getProcessCode());
                feedbackAuditDO.setProcessName(firstItem.getProcessName());
                feedbackAuditDO.setMachineryCode(firstItem.getMachineryCode());
                feedbackAuditDO.setMachineryName(firstItem.getMachineryName());
            }

            // 汇总所有工序项的数量
            BigDecimal totalQuantity = feedbackAuditItemDOList.stream()
                    .map(FeedbackAuditItemDO::getSumQuantityFeedback)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalQuantityQuality = feedbackAuditItemDOList.stream()
                    .map(FeedbackAuditItemDO::getSumQuantityQualified)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalQuantityExcess = feedbackAuditItemDOList.stream()
                    .map(item -> item.getSumQuantityExcess() != null ? item.getSumQuantityExcess() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            feedbackAuditDO.setQuantity(totalQuantity);
            feedbackAuditDO.setQuantityQuality(totalQuantityQuality);
            feedbackAuditDO.setQuantityExcess(totalQuantityExcess);

        }).collect(Collectors.toList());
        result.setList(resultList);

        return success(result);
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报工审批主表 Excel")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedbackAuditExcel(@Valid FeedbackAuditExportReqVO exportReqVO,
                                         HttpServletResponse response) throws IOException {
        List<FeedbackAuditDO> list = feedbackAuditService.getFeedbackAuditList(exportReqVO);
        // 导出 Excel
        List<FeedbackAuditExcelVO> datas = FeedbackAuditConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "报工审批主表.xls", "数据", FeedbackAuditExcelVO.class, datas);
    }

    @PostMapping("/auditFeedback")
    @Operation(summary = "审批报工")
    @Transactional
    public CommonResult<String> auditFeedback(@RequestBody Map<String, Object> params) {
        //2025-10-14 追加: 审批报工的请求头参数需保存
        Map<String, Object> methodArgs = (Map<String, Object>)params.get("queryParams");
        System.out.println(methodArgs);

        List<Map<String, Object>> summaryList = (List<Map<String, Object>>) params.get("summaryList");

        Map<String, Map<String, List<Map<String, Object>>>> processEquipmentGroup = summaryList.stream()
                .collect(Collectors.groupingBy(
                        summary -> (String) summary.get("processCode"),
                        Collectors.groupingBy(summary -> (String) summary.get("machineryCode"))
                ));

        for (Map.Entry<String, Map<String, List<Map<String, Object>>>> processEntry : processEquipmentGroup.entrySet()) {
            String processCode = processEntry.getKey();
            Map<String, List<Map<String, Object>>> equipmentGroups = processEntry.getValue();

            // 遍历每个设备组
            for (List<Map<String, Object>> groupSummaries : equipmentGroups.values()) {
                // 每个工序创建独立审核单
                FeedbackAuditCreateReqVO createReqVO = new FeedbackAuditCreateReqVO();
                String code = autoCodeApi.genSerialCode("FEEDBACK_AUDIT_CODE", null);
                createReqVO.setAuditCode(code);
                AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
                createReqVO.setSubmitUserId(adminUserRespDTO.getId());
                createReqVO.setSubmitUserName(adminUserRespDTO.getUsername());
                createReqVO.setSubmitNickName(adminUserRespDTO.getNickname());
                createReqVO.setSubmitTime(LocalDateTime.now());
                createReqVO.setStatus("PREPARE");
                createReqVO.setMethodArgs(methodArgs.toString());
                Long auditId = feedbackAuditService.createFeedbackAudit(createReqVO);

                // 处理当前工序的报工汇总项
                for (Map<String, Object> summary : groupSummaries) {
                    // 提取数据（与原逻辑相同）
                    String workorderCode = (String) summary.get("workorderCode");
                    Integer workorderId = (Integer) summary.get("workorderId");

                    Integer taskId = (Integer) summary.get("taskId");
                    String taskCode = (String) summary.get("taskCode");

                    Integer processId = (Integer) summary.get("processId");
                    String processName = (String) summary.get("processName");
                    // String processCode = (String) summary.get("processCode");

                    Integer itemId = (Integer) summary.get("itemId");
                    String itemName = (String) summary.get("itemName");
                    String itemCode = (String) summary.get("itemCode");

                    String unitOfMeasure = (String) summary.get("unitOfMeasure");
                    String specification = (String) summary.get("specification");
                    String nickName = (String) summary.get("nickName");
                    String userName = (String) summary.get("userName");

                    Integer machineryId = (Integer) summary.get("machineryId");
                    String machineryName = (String) summary.get("machineryName");
                    String machineryCode = (String) summary.get("machineryCode");


                    List<Integer> feedbackIdList = (List<Integer>) summary.get("feedbackIds");

                    Number totalQuantityFeedback = Optional.ofNullable(summary.get("totalQuantityFeedback")).map(Number.class::cast).orElse(0);
                    Number totalQuantityQualified = Optional.ofNullable(summary.get("totalQuantityQualified")).map(Number.class::cast).orElse(0);
                    Number totalQuantityUnqualified = Optional.ofNullable(summary.get("totalQuantityUnqualified")).map(Number.class::cast).orElse(0);
                    Number totalQuantityExcess = Optional.ofNullable(summary.get("totalQuantityExcess")).map(Number.class::cast).orElse(0);


                    BigDecimal quantityFeedback = new BigDecimal(totalQuantityFeedback.doubleValue());
                    BigDecimal quantityQualified = new BigDecimal(totalQuantityQualified.doubleValue());
                    BigDecimal quantityUnqualified = new BigDecimal(totalQuantityUnqualified.doubleValue());
                    BigDecimal quantityExcess = new BigDecimal(totalQuantityExcess.doubleValue());

                    List<Map<String, Object>> teamMembers = (List<Map<String, Object>>) summary.get("teamMembers");
                    StringBuffer sb = new StringBuffer(teamMembers.stream()
                            .map(teamMember -> String.format("[%s](%s)%s",
                                    teamMember.get("teamCode"),
                                    teamMember.get("userName"),
                                    teamMember.get("nickName")))
                            .collect(Collectors.joining(" , ")));

                    // 创建审核项
                    FeedbackAuditItemDO feedbackAuditItemDO = new FeedbackAuditItemDO();
                    feedbackAuditItemDO.setWorkorderId(Long.valueOf(workorderId));
                    feedbackAuditItemDO.setWorkorderCode(workorderCode);
                    feedbackAuditItemDO.setWorkorderCode(workorderCode);
                    feedbackAuditItemDO.setWorkorderName(workorderCode);

                    feedbackAuditItemDO.setItemId(Long.valueOf(itemId));
                    feedbackAuditItemDO.setItemCode(itemCode);
                    feedbackAuditItemDO.setItemName(itemName);

                    feedbackAuditItemDO.setUnitOfMeasure(unitOfMeasure);
                    feedbackAuditItemDO.setSpecification(specification);

                    feedbackAuditItemDO.setNickName(nickName);
                    feedbackAuditItemDO.setUserName(userName);

                    feedbackAuditItemDO.setTaskId(Long.valueOf(taskId));
                    feedbackAuditItemDO.setTaskCode(taskCode);

                    feedbackAuditItemDO.setProcessId(Long.valueOf(processId));
                    feedbackAuditItemDO.setProcessName(processName);
                    feedbackAuditItemDO.setProcessCode(processCode);

                    feedbackAuditItemDO.setSumQuantityFeedback(quantityFeedback);
                    feedbackAuditItemDO.setSumQuantityQualified(quantityQualified);
                    feedbackAuditItemDO.setSumQuantityUnquanlified(quantityUnqualified);
                    feedbackAuditItemDO.setSumQuantityExcess(quantityExcess);

                    feedbackAuditItemDO.setAuditId(String.valueOf(auditId));
                    feedbackAuditItemDO.setAllPrincipal(sb.toString());
                    // 设置当前工序的审核单ID
                    feedbackAuditItemDO.setAuditId(String.valueOf(auditId));
                    feedbackAuditItemDO.setMachineryName(machineryName);
                    feedbackAuditItemDO.setMachineryCode(machineryCode);

                    Long auditItemId = feedbackAuditItemService.createFeedbackAuditItem(FeedbackAuditItemConvert.INSTANCE.convert01(feedbackAuditItemDO));

                    // 处理报工明细（与原逻辑相同）
                    for (Integer feedbackId : feedbackIdList) {
                        FeedbackAuditDetailDO detailDO = new FeedbackAuditDetailDO();
                        FeedbackDO feedbackDO = feedbackService.getFeedback(Long.valueOf(feedbackId));
                        BeanUtils.copyBeanProp(detailDO, feedbackDO);
                        detailDO.setAuditId(auditId);
                        detailDO.setAuditItemId(auditItemId);
                        detailDO.setId(null);
                        detailDO.setFeedbackId(Long.valueOf(feedbackId));
                        detailDO.setQuantityFeedback(BigDecimal.valueOf(feedbackDO.getQuantityFeedback()));
                        detailDO.setQuantityQualified(BigDecimal.valueOf(feedbackDO.getQuantityQualified()));
                        detailDO.setQuantityUnquanlified(BigDecimal.valueOf(feedbackDO.getQuantityUnquanlified()));
                        detailDO.setQuantityExcess( feedbackDO.getQuantityExcess());
                        feedbackAuditDetailService.createFeedbackAuditDetail(FeedbackAuditDetailConvert.INSTANCE.convert01(detailDO));
                    }
                }
            }
        }

        return success("success");
    }



    @PutMapping("/update-feedbackAudit-status")
    @Operation(summary = "更新生产报工审批状态")
    @Transactional
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit:update')")
    public CommonResult updateFeedbackStatus(@RequestParam("id") Long id, @RequestParam("status") String status) {
        FeedbackAuditDO auditDO = feedbackAuditService.getFeedbackAudit(id);
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        auditDO.setAuditUserId(Long.valueOf(adminUserRespDTO.getId()));
        auditDO.setAuditNickName(adminUserRespDTO.getNickname());
        auditDO.setAuditUserName(adminUserRespDTO.getUsername());
        auditDO.setAuditTime(LocalDateTime.now());
        auditDO.setStatus(status);
        feedbackAuditService.updateFeedbackAudit(FeedbackAuditConvert.INSTANCE.convert02(auditDO));
        return success("success");
    }

    @PostMapping("/transferFeedbackAuditDetails")
    @Operation(summary = "审批报工转审")
    public CommonResult<String> transferFeedbackAuditDetails(@RequestBody FeedbackAuditRespVO respVO) {
        FeedbackAuditDO sourceAudit = feedbackAuditService.getFeedbackAudit(respVO.getSourceAuditId());
        if (sourceAudit == null) {
            return error(ErrorCodeConstants.FEEDBACK_AUDIT_NOT_EXISTS);
        }

        // 2. 校验目标审批人
        AdminUserRespDTO targetUser = adminUserApi.getUser(respVO.getTargetAuditUserId());
        if (targetUser == null) {
            return error(ErrorCodeConstants.USER_NOT_EXIST);
        }

        // 3. 校验明细是否属于源审批单
        List<FeedbackAuditDetailDO> detailsToTransfer = feedbackAuditDetailService.selectBatchIds(respVO.getDetailIds());
        if (detailsToTransfer.size() != respVO.getDetailIds().size()) {
            return  error(ErrorCodeConstants.FEEDBACK_AUDIT_DETAIL_NOT_EXISTS);
        }

        for (FeedbackAuditDetailDO detail : detailsToTransfer) {
            if (!detail.getAuditId().equals(respVO.getSourceAuditId())) {
                return  error(ErrorCodeConstants.FEEDBACK_AUDIT_DETAIL_NOT_BELONG);
            }
        }

        String code = autoCodeApi.genSerialCode("FEEDBACK_AUDIT_CODE",null);

        // 4. 创建新的审批单
        FeedbackAuditDO newAudit = new FeedbackAuditDO();
        newAudit.setAuditCode(code);
        newAudit.setSubmitUserId(sourceAudit.getSubmitUserId());
        newAudit.setSubmitUserName(sourceAudit.getSubmitUserName());
        newAudit.setSubmitNickName(sourceAudit.getSubmitNickName());
        newAudit.setSubmitTime(LocalDateTime.now());
        newAudit.setAuditUserId(respVO.getTargetAuditUserId());
        newAudit.setAuditUserName(targetUser.getUsername());
        newAudit.setAuditNickName(targetUser.getNickname());
        newAudit.setStatus("PREPARE");
        newAudit.setRemark(respVO.getRemark());
        newAudit.setSourceAuditId(respVO.getSourceAuditId()); // 记录来源审批单
        Long newAuditId = feedbackAuditService.createFeedbackAudit(FeedbackAuditConvert.INSTANCE.convert01(newAudit));
        newAudit.setId(newAuditId);
        // 5. 处理明细和汇总数据
        processTransferDetails(newAuditId, detailsToTransfer, sourceAudit.getId());

        // 6. 记录转审历史
        recordTransferHistory(sourceAudit, newAudit, respVO);

        return success("success");
    }

    /**
     * 处理转审的明细和汇总数据
     */
    private void processTransferDetails(Long newAuditId, List<FeedbackAuditDetailDO> detailsToTransfer, Long sourceAuditId) {
        // 按工单+工序分组明细，用于创建汇总数据
        Map<String, List<FeedbackAuditDetailDO>> groupedDetails = detailsToTransfer.stream()
                .collect(Collectors.groupingBy(detail ->
                        detail.getWorkorderId() + "_" + detail.getProcessId()));

        List<FeedbackAuditItemDO> sourceItem = feedbackAuditItemService.getFeedbackAuditItemList(new FeedbackAuditItemExportReqVO().setAuditId(String.valueOf(sourceAuditId)));
        Long machineryId = null;
        String machineryCode = null;
        String machineryName = null;
        if(!sourceItem.isEmpty()){
            machineryId = sourceItem.get(0).getMachineryId();
            machineryCode = sourceItem.get(0).getMachineryCode();
            machineryName = sourceItem.get(0).getMachineryName();
        }
        // 创建新的汇总项
        for (List<FeedbackAuditDetailDO> group : groupedDetails.values()) {
            FeedbackAuditDetailDO firstDetail = group.get(0);

            // 计算汇总数量
            BigDecimal sumFeedback = group.stream()
                    .map(FeedbackAuditDetailDO::getQuantityFeedback)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sumQualified = group.stream()
                    .map(FeedbackAuditDetailDO::getQuantityQualified)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sumUnqualified = group.stream()
                    .map(FeedbackAuditDetailDO::getQuantityUnquanlified)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal sumExcess = group.stream()
                    .map(FeedbackAuditDetailDO::getQuantityExcess)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);


            // 创建新汇总项
            FeedbackAuditItemDO newItem = new FeedbackAuditItemDO();
            newItem.setAuditId(newAuditId.toString());
            newItem.setWorkorderId(firstDetail.getWorkorderId());
            newItem.setWorkorderCode(firstDetail.getWorkorderCode());
            newItem.setWorkorderName(firstDetail.getWorkorderName());
            newItem.setProcessId(firstDetail.getProcessId());
            newItem.setProcessCode(firstDetail.getProcessCode());
            newItem.setProcessName(firstDetail.getProcessName());
            newItem.setTaskId(firstDetail.getTaskId());
            newItem.setTaskCode(firstDetail.getTaskCode());
            newItem.setItemId(firstDetail.getItemId());
            newItem.setItemCode(firstDetail.getItemCode());
            newItem.setItemName(firstDetail.getItemName());
            newItem.setUnitOfMeasure(firstDetail.getUnitOfMeasure());
            newItem.setSpecification(firstDetail.getSpecification());
            newItem.setSumQuantityFeedback(sumFeedback);
            newItem.setSumQuantityQualified(sumQualified);
            newItem.setSumQuantityUnquanlified(sumUnqualified);
            newItem.setSumQuantityExcess(sumExcess);
            newItem.setUserName(firstDetail.getUserName());
            newItem.setNickName(firstDetail.getNickName());

            newItem.setMachineryId(machineryId);
            newItem.setMachineryCode(machineryCode);
            newItem.setMachineryName(machineryName);

            Long newItemId = feedbackAuditItemService.createFeedbackAuditItem(FeedbackAuditItemConvert.INSTANCE.convert01(newItem));
            newItem.setId(newItemId);

            // 更新明细的audit_id和audit_item_id
            for (FeedbackAuditDetailDO detail : group) {
                FeedbackAuditDetailDO updateDetail =  feedbackAuditDetailService.getFeedbackAuditDetail(detail.getId());
                updateDetail.setAuditId(newAuditId);
                updateDetail.setAuditItemId(newItemId);
                feedbackAuditDetailService.updateFeedbackAuditDetail(FeedbackAuditDetailConvert.INSTANCE.convert02(updateDetail));
            }
        }
        // 更新源审批单的汇总项
        updateSourceAuditItems(sourceAuditId);
    }

    /**
     * 更新源审批单的汇总项
     */
    private void updateSourceAuditItems(Long sourceAuditId) {
        // 获取源审批单的所有汇总项
        List<FeedbackAuditItemDO> sourceItems = feedbackAuditItemService.getFeedbackAuditItemList(new FeedbackAuditItemExportReqVO().setAuditId(sourceAuditId.toString()));

        for (FeedbackAuditItemDO item : sourceItems) {
            // 获取该汇总项下的所有明细
            List<FeedbackAuditDetailDO> itemDetails = feedbackAuditDetailService.getFeedbackAuditDetailList(new FeedbackAuditDetailExportReqVO().setAuditItemId(item.getId()));
            if (itemDetails.isEmpty()) {
                // 如果没有明细了，删除该汇总项
                feedbackAuditItemService.deleteFeedbackAuditItem(item.getId());
            } else {
                // 重新计算汇总数量
                BigDecimal sumFeedback = itemDetails.stream()
                        .map(FeedbackAuditDetailDO::getQuantityFeedback)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal sumQualified = itemDetails.stream()
                        .map(FeedbackAuditDetailDO::getQuantityQualified)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal sumUnqualified = itemDetails.stream()
                        .map(FeedbackAuditDetailDO::getQuantityUnquanlified)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                // 更新汇总项
                FeedbackAuditItemDO updateItem = feedbackAuditItemService.getFeedbackAuditItem(item.getId());
                updateItem.setSumQuantityFeedback(sumFeedback);
                updateItem.setSumQuantityQualified(sumQualified);
                updateItem.setSumQuantityUnquanlified(sumUnqualified);
                feedbackAuditItemService.updateFeedbackAuditItem(FeedbackAuditItemConvert.INSTANCE.convert02(updateItem));
            }
        }
    }


    /**
     * 记录转审历史
     * @param sourceAudit 源审批单
     * @param newAudit 新创建的审批单
     * @param reqDTO 转审请求参数
     */
    private void recordTransferHistory(FeedbackAuditDO sourceAudit, FeedbackAuditDO newAudit, FeedbackAuditRespVO reqDTO) {
        // 获取当前登录用户信息
        AdminUserRespDTO loginUser = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        Long operatorUserId = loginUser.getId();
        String operatorUserName = loginUser.getUsername();

        // 1. 在源审批单上记录转出历史
        JSONObject transferOutRecord = new JSONObject();
        transferOutRecord.put("type", "TRANSFER_OUT");
        transferOutRecord.put("time", LocalDateTime.now());
        transferOutRecord.put("operatorUserId", operatorUserId);
        transferOutRecord.put("operatorUserName", operatorUserName);
        transferOutRecord.put("targetAuditId", newAudit.getId());
        transferOutRecord.put("targetAuditCode", newAudit.getAuditCode());
        transferOutRecord.put("targetAuditUserId", reqDTO.getTargetAuditUserId());
        transferOutRecord.put("detailCount", reqDTO.getDetailIds().size());
        transferOutRecord.put("remark", reqDTO.getRemark());

        // 获取并更新源审批单的转审历史
        JSONArray sourceHistory = sourceAudit.getTransferHistory() != null ?
                JSON.parseArray(sourceAudit.getTransferHistory()) : new JSONArray();
        sourceHistory.add(transferOutRecord);

        FeedbackAuditDO sourceUpdate = feedbackAuditService.getFeedbackAudit(sourceAudit.getId());
        sourceUpdate.setTransferHistory(sourceHistory.toJSONString());
        feedbackAuditService.updateFeedbackAudit(FeedbackAuditConvert.INSTANCE.convert02(sourceUpdate));

        // 2. 在新审批单上记录转入历史
        JSONObject transferInRecord = new JSONObject();
        transferInRecord.put("type", "TRANSFER_IN");
        transferInRecord.put("time", LocalDateTime.now());
        transferInRecord.put("operatorUserId", operatorUserId);
        transferInRecord.put("operatorUserName", operatorUserName);
        transferInRecord.put("sourceAuditId", sourceAudit.getId());
        transferInRecord.put("sourceAuditCode", sourceAudit.getAuditCode());
        transferInRecord.put("detailCount", reqDTO.getDetailIds().size());
        transferInRecord.put("remark", reqDTO.getRemark());

        // 获取并更新新审批单的转审历史
        JSONArray targetHistory = newAudit.getTransferHistory() != null ?
                JSON.parseArray(newAudit.getTransferHistory()) : new JSONArray();
        targetHistory.add(transferInRecord);

        FeedbackAuditDO targetUpdate = feedbackAuditService.getFeedbackAudit(newAudit.getId());
        targetUpdate.setTransferHistory(targetHistory.toJSONString());
        feedbackAuditService.updateFeedbackAudit(FeedbackAuditConvert.INSTANCE.convert02(targetUpdate));

        // 3. 记录详细的转审操作日志
        String logContent = String.format(
                "用户 %s(%s) 将审批单 %s 中的 %d 条明细转审给用户 %s，创建了新审批单 %s",
                operatorUserName, operatorUserId,
                sourceAudit.getAuditCode(), reqDTO.getDetailIds().size(),
                reqDTO.getTargetAuditUserId(), newAudit.getAuditCode()
        );
    }
}
