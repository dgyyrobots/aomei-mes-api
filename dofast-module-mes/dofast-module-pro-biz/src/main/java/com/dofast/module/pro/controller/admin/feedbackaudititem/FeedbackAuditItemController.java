package com.dofast.module.pro.controller.admin.feedbackaudititem;

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
import java.util.*;
import java.io.IOException;

import com.dofast.framework.common.pojo.PageResult;
import com.dofast.framework.common.pojo.CommonResult;
import static com.dofast.framework.common.pojo.CommonResult.success;

import com.dofast.framework.excel.core.util.ExcelUtils;

import com.dofast.framework.operatelog.core.annotations.OperateLog;
import static com.dofast.framework.operatelog.core.enums.OperateTypeEnum.*;

import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackaudititem.FeedbackAuditItemDO;
import com.dofast.module.pro.convert.feedbackaudititem.FeedbackAuditItemConvert;
import com.dofast.module.pro.service.feedbackaudititem.FeedbackAuditItemService;

@Tag(name = "管理后台 - 报工审批汇总")
@RestController
@RequestMapping("/mes/pro/feedback-audit-item")
@Validated
public class FeedbackAuditItemController {

    @Resource
    private FeedbackAuditItemService feedbackAuditItemService;

    @PostMapping("/create")
    @Operation(summary = "创建报工审批汇总")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:create')")
    public CommonResult<Long> createFeedbackAuditItem(@Valid @RequestBody FeedbackAuditItemCreateReqVO createReqVO) {
        return success(feedbackAuditItemService.createFeedbackAuditItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报工审批汇总")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:update')")
    public CommonResult<Boolean> updateFeedbackAuditItem(@Valid @RequestBody FeedbackAuditItemUpdateReqVO updateReqVO) {
        feedbackAuditItemService.updateFeedbackAuditItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报工审批汇总")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:delete')")
    public CommonResult<Boolean> deleteFeedbackAuditItem(@RequestParam("id") Long id) {
        feedbackAuditItemService.deleteFeedbackAuditItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报工审批汇总")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:query')")
    public CommonResult<FeedbackAuditItemRespVO> getFeedbackAuditItem(@RequestParam("id") Long id) {
        FeedbackAuditItemDO feedbackAuditItem = feedbackAuditItemService.getFeedbackAuditItem(id);
        return success(FeedbackAuditItemConvert.INSTANCE.convert(feedbackAuditItem));
    }

    @GetMapping("/list")
    @Operation(summary = "获得报工审批汇总列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:query')")
    public CommonResult<List<FeedbackAuditItemRespVO>> getFeedbackAuditItemList(@RequestParam("ids") Collection<Long> ids) {
        List<FeedbackAuditItemDO> list = feedbackAuditItemService.getFeedbackAuditItemList(ids);
        return success(FeedbackAuditItemConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报工审批汇总分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:query')")
    public CommonResult<PageResult<FeedbackAuditItemRespVO>> getFeedbackAuditItemPage(@Valid FeedbackAuditItemPageReqVO pageVO) {
        PageResult<FeedbackAuditItemDO> pageResult = feedbackAuditItemService.getFeedbackAuditItemPage(pageVO);
        return success(FeedbackAuditItemConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报工审批汇总 Excel")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedbackAuditItemExcel(@Valid FeedbackAuditItemExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FeedbackAuditItemDO> list = feedbackAuditItemService.getFeedbackAuditItemList(exportReqVO);
        // 导出 Excel
        List<FeedbackAuditItemExcelVO> datas = FeedbackAuditItemConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "报工审批汇总.xls", "数据", FeedbackAuditItemExcelVO.class, datas);
    }

    @GetMapping("/getAuditSummary")
    @Operation(summary = "获得报工审批汇总")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-item:query')")
    public CommonResult<List<FeedbackAuditItemRespVO>> getAuditSummary(@RequestParam("auditId") Long auditId) {
        List<FeedbackAuditItemDO> auditItemList = feedbackAuditItemService.getFeedbackAuditItemList(new FeedbackAuditItemExportReqVO().setAuditId(String.valueOf(auditId)));
        return success(FeedbackAuditItemConvert.INSTANCE.convertList(auditItemList));
    }

}
