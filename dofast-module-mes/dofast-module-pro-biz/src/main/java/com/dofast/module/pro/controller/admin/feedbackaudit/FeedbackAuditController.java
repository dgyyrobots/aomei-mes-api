package com.dofast.module.pro.controller.admin.feedbackaudit;

import com.dofast.framework.common.util.bean.BeanUtils;
import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.mes.api.autocode.AutoCodeApi;
import com.dofast.module.pro.convert.feedback.FeedbackConvert;
import com.dofast.module.pro.convert.feedbackauditdetail.FeedbackAuditDetailConvert;
import com.dofast.module.pro.convert.feedbackaudititem.FeedbackAuditItemConvert;
import com.dofast.module.pro.dal.dataobject.feedback.FeedbackDO;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import com.dofast.module.pro.service.feedback.FeedbackService;
import com.dofast.module.pro.service.feedbackauditdetail.FeedbackAuditDetailService;
import com.dofast.module.pro.service.feedbackaudititem.FeedbackAuditItemService;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
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

import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;

import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.pro.controller.admin.feedbackaudit.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudit.FeedbackAuditDO;
import com.dofast.module.pro.convert.feedbackaudit.FeedbackAuditConvert;
import com.dofast.module.pro.service.feedbackaudit.FeedbackAuditService;

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
        PageResult<FeedbackAuditDO> pageResult = feedbackAuditService.getFeedbackAuditPage(pageVO);
        return success(FeedbackAuditConvert.INSTANCE.convertPage(pageResult));
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
    public CommonResult<String> auditFeedback(@RequestBody Map<String, Object> params) {
        List<Integer> feedbackIds = (List<Integer>) params.get("feedbackIds");
        List<Map<String, Object>> summaryList = (List<Map<String, Object>>) params.get("summaryList");
        FeedbackAuditCreateReqVO createReqVO = new FeedbackAuditCreateReqVO();
        String code = autoCodeApi.genSerialCode("FEEDBACK_AUDIT_CODE", null);
        createReqVO.setAuditCode(code);
        AdminUserRespDTO adminUserRespDTO = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        createReqVO.setSubmitUserId(adminUserRespDTO.getId());
        createReqVO.setSubmitUserName(adminUserRespDTO.getUsername());
        createReqVO.setSubmitNickName(adminUserRespDTO.getNickname());
        createReqVO.setSubmitTime(LocalDateTime.now());
        createReqVO.setStatus("PREPARE");
        Long auditId = feedbackAuditService.createFeedbackAudit(createReqVO);

        // 开始追加报工汇总
        for (Map<String, Object> summary : summaryList) {
            String workorderCode = (String) summary.get("workorderCode");
            Integer workorderId = (Integer) summary.get("workorderId");

            Integer taskId = (Integer) summary.get("taskId");
            String taskCode = (String) summary.get("taskCode");

            Integer processId = (Integer) summary.get("processId");
            String processName = (String) summary.get("processName");
            String processCode = (String) summary.get("processCode");

            Integer itemId = (Integer) summary.get("itemId");
            String itemName = (String) summary.get("itemName");
            String itemCode = (String) summary.get("itemCode");

            String unitOfMeasure = (String) summary.get("unitOfMeasure");
            String specification = (String) summary.get("specification");
            String nickName = (String) summary.get("nickName");
            String userName = (String) summary.get("userName");

            List<Integer> feedbackIdList = (List<Integer>) summary.get("feedbackIds");

            Number totalQuantityFeedback = (Number) summary.get("totalQuantityFeedback");
            Number totalQuantityQualified = (Number) summary.get("totalQuantityQualified");
            Number totalQuantityUnqualified = (Number) summary.get("totalQuantityUnquanlified");

            BigDecimal quantityFeedback = new BigDecimal(totalQuantityFeedback.doubleValue());
            BigDecimal quantityQualified = new BigDecimal(totalQuantityQualified.doubleValue());
            BigDecimal quantityUnqualified = new BigDecimal(totalQuantityUnqualified.doubleValue());

            List<Map<String, Object>> teamMembers = (List<Map<String, Object>>) summary.get("teamMembers");
            StringBuffer sb = new StringBuffer(teamMembers.stream()
                    .map(teamMember -> String.format("[%s](%s)%s",
                            teamMember.get("teamCode"),
                            teamMember.get("userName"),
                            teamMember.get("nickName")))
                    .collect(Collectors.joining(" , ")));

            FeedbackAuditItemDO feedbackAuditItemDO = new FeedbackAuditItemDO();
            feedbackAuditItemDO.setWorkorderId(Long.valueOf(workorderId));
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
            feedbackAuditItemDO.setAuditId(String.valueOf(auditId));
            feedbackAuditItemDO.setAllPrincipal(sb.toString());
            Long auditItemId = feedbackAuditItemService.createFeedbackAuditItem(FeedbackAuditItemConvert.INSTANCE.convert01(feedbackAuditItemDO));
            for (Integer feedbackId : feedbackIdList) {
                // 开始追加明细
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
                feedbackAuditDetailService.createFeedbackAuditDetail(FeedbackAuditDetailConvert.INSTANCE.convert01(detailDO));
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

}
