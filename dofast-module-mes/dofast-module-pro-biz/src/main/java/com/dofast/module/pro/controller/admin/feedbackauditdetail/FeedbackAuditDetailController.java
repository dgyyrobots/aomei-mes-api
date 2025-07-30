package com.dofast.module.pro.controller.admin.feedbackauditdetail;

import com.dofast.module.pro.controller.admin.feedbackaudititem.vo.FeedbackAuditItemRespVO;
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

import com.dofast.module.pro.controller.admin.feedbackauditdetail.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackauditdetail.FeedbackAuditDetailDO;
import com.dofast.module.pro.convert.feedbackauditdetail.FeedbackAuditDetailConvert;
import com.dofast.module.pro.service.feedbackauditdetail.FeedbackAuditDetailService;

@Tag(name = "管理后台 - 报工审批明细")
@RestController
@RequestMapping("/mes/pro/feedback-audit-detail")
@Validated
public class FeedbackAuditDetailController {

    @Resource
    private FeedbackAuditDetailService feedbackAuditDetailService;

    @PostMapping("/create")
    @Operation(summary = "创建报工审批明细")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:create')")
    public CommonResult<Long> createFeedbackAuditDetail(@Valid @RequestBody FeedbackAuditDetailCreateReqVO createReqVO) {
        return success(feedbackAuditDetailService.createFeedbackAuditDetail(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报工审批明细")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:update')")
    public CommonResult<Boolean> updateFeedbackAuditDetail(@Valid @RequestBody FeedbackAuditDetailUpdateReqVO updateReqVO) {
        feedbackAuditDetailService.updateFeedbackAuditDetail(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报工审批明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:delete')")
    public CommonResult<Boolean> deleteFeedbackAuditDetail(@RequestParam("id") Long id) {
        feedbackAuditDetailService.deleteFeedbackAuditDetail(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报工审批明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:query')")
    public CommonResult<FeedbackAuditDetailRespVO> getFeedbackAuditDetail(@RequestParam("id") Long id) {
        FeedbackAuditDetailDO feedbackAuditDetail = feedbackAuditDetailService.getFeedbackAuditDetail(id);
        return success(FeedbackAuditDetailConvert.INSTANCE.convert(feedbackAuditDetail));
    }

    @GetMapping("/list")
    @Operation(summary = "获得报工审批明细列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:query')")
    public CommonResult<List<FeedbackAuditDetailRespVO>> getFeedbackAuditDetailList(@RequestParam("ids") Collection<Long> ids) {
        List<FeedbackAuditDetailDO> list = feedbackAuditDetailService.getFeedbackAuditDetailList(ids);
        return success(FeedbackAuditDetailConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报工审批明细分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:query')")
    public CommonResult<PageResult<FeedbackAuditDetailRespVO>> getFeedbackAuditDetailPage(@Valid FeedbackAuditDetailPageReqVO pageVO) {
        PageResult<FeedbackAuditDetailDO> pageResult = feedbackAuditDetailService.getFeedbackAuditDetailPage(pageVO);
        return success(FeedbackAuditDetailConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报工审批明细 Excel")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedbackAuditDetailExcel(@Valid FeedbackAuditDetailExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FeedbackAuditDetailDO> list = feedbackAuditDetailService.getFeedbackAuditDetailList(exportReqVO);
        // 导出 Excel
        List<FeedbackAuditDetailExcelVO> datas = FeedbackAuditDetailConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "报工审批明细.xls", "数据", FeedbackAuditDetailExcelVO.class, datas);
    }


    @GetMapping("/getAuditDetails")
    @Operation(summary = "导出报工审批明细 Excel")
    @PreAuthorize("@ss.hasPermission('pro:feedback-audit-detail:export')")
    @OperateLog(type = EXPORT)
    public CommonResult<PageResult<FeedbackAuditDetailRespVO>> getAuditDetails(@Valid FeedbackAuditDetailPageReqVO feedbackAuditDetailDO) {
        PageResult<FeedbackAuditDetailDO> pageResult = feedbackAuditDetailService.getFeedbackAuditDetailPage(feedbackAuditDetailDO );
        return success(FeedbackAuditDetailConvert.INSTANCE.convertPage(pageResult));
    }

}
