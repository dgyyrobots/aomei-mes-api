package com.dofast.module.pro.controller.admin.feedbackwarehousinglog;

import com.dofast.framework.web.core.util.WebFrameworkUtils;
import com.dofast.module.system.api.user.AdminUserApi;
import com.dofast.module.system.api.user.dto.AdminUserRespDTO;
import com.mysql.cj.protocol.Security;
import com.thoughtworks.xstream.core.SecurityUtils;
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

import com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo.*;
import com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog.FeedbackWarehousingLogDO;
import com.dofast.module.pro.convert.feedbackwarehousinglog.FeedbackWarehousingLogConvert;
import com.dofast.module.pro.service.feedbackwarehousinglog.FeedbackWarehousingLogService;

@Tag(name = "管理后台 - 报工入库日志")
@RestController
@RequestMapping("/pro/feedback-warehousing-log")
@Validated
public class FeedbackWarehousingLogController {

    @Resource
    private FeedbackWarehousingLogService feedbackWarehousingLogService;

    @Resource
    private AdminUserApi adminUserApi;

    @PostMapping("/create")
    @Operation(summary = "创建报工入库日志")
    @PreAuthorize("@ss.hasPermission('pro:feedback-warehousing-log:create')")
    public CommonResult<Long> createFeedbackWarehousingLog(@Valid @RequestBody FeedbackWarehousingLogCreateReqVO createReqVO) {
        return success(feedbackWarehousingLogService.createFeedbackWarehousingLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报工入库日志")
    @PreAuthorize("@ss.hasPermission('pro:feedback-warehousing-log:update')")
    public CommonResult<Boolean> updateFeedbackWarehousingLog(@Valid @RequestBody FeedbackWarehousingLogUpdateReqVO updateReqVO) {
        feedbackWarehousingLogService.updateFeedbackWarehousingLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报工入库日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('pro:feedback-warehousing-log:delete')")
    public CommonResult<Boolean> deleteFeedbackWarehousingLog(@RequestParam("id") Long id) {
        feedbackWarehousingLogService.deleteFeedbackWarehousingLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报工入库日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('pro:feedback-warehousing-log:query')")
    public CommonResult<FeedbackWarehousingLogRespVO> getFeedbackWarehousingLog(@RequestParam("id") Long id) {
        FeedbackWarehousingLogDO feedbackWarehousingLog = feedbackWarehousingLogService.getFeedbackWarehousingLog(id);
        return success(FeedbackWarehousingLogConvert.INSTANCE.convert(feedbackWarehousingLog));
    }

    @GetMapping("/list")
    @Operation(summary = "获得报工入库日志列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('pro:feedback-warehousing-log:query')")
    public CommonResult<List<FeedbackWarehousingLogRespVO>> getFeedbackWarehousingLogList(@RequestParam("ids") Collection<Long> ids) {
        List<FeedbackWarehousingLogDO> list = feedbackWarehousingLogService.getFeedbackWarehousingLogList(ids);
        return success(FeedbackWarehousingLogConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报工入库日志分页")
    @PreAuthorize("@ss.hasPermission('pro:feedback-warehousing-log:query')")
    public CommonResult<PageResult<FeedbackWarehousingLogRespVO>> getFeedbackWarehousingLogPage(@Valid FeedbackWarehousingLogPageReqVO pageVO) {
        // 追加当前用户卡控
        AdminUserRespDTO admin = adminUserApi.getUser(WebFrameworkUtils.getLoginUserId());
        pageVO.setCreator(admin.getId());
        PageResult<FeedbackWarehousingLogDO> pageResult = feedbackWarehousingLogService.getFeedbackWarehousingLogPage(pageVO);
        return success(FeedbackWarehousingLogConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出报工入库日志 Excel")
    @PreAuthorize("@ss.hasPermission('pro:feedback-warehousing-log:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedbackWarehousingLogExcel(@Valid FeedbackWarehousingLogExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<FeedbackWarehousingLogDO> list = feedbackWarehousingLogService.getFeedbackWarehousingLogList(exportReqVO);
        // 导出 Excel
        List<FeedbackWarehousingLogExcelVO> datas = FeedbackWarehousingLogConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "报工入库日志.xls", "数据", FeedbackWarehousingLogExcelVO.class, datas);
    }

}
