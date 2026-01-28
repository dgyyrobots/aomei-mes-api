package com.dofast.module.pro.controller.admin.feedbackaudit.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报工审批主表 Excel 导出 Request VO，参数和 FeedbackAuditPageReqVO 是一致的")
@Data
public class FeedbackAuditExportReqVO {

    @Schema(description = "审批单号")
    private String auditCode;

    @Schema(description = "提交人ID", example = "29522")
    private Long submitUserId;

    @Schema(description = "提交人姓名", example = "李四")
    private String submitUserName;

    @Schema(description = "提交人昵称")
    private String submitNickName;

    @Schema(description = "提交时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] submitTime;

    @Schema(description = "审批人ID", example = "21328")
    private Long auditUserId;

    @Schema(description = "审批人姓名", example = "张三")
    private String auditUserName;

    @Schema(description = "审批人昵称")
    private String auditNickName;

    @Schema(description = "审批时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] auditTime;

    @Schema(description = "状态(PENDING:待审批 APPROVED:已审批 REJECTED:已驳回)", example = "2")
    private String status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "原始审批人ID")
    private Long originalAuditUserId;

    @Schema(description = "转审历史记录")
    private String transferHistory;

    @Schema(description = "来源审批单ID")
    private Long sourceAuditId;

    @Schema(description = "请求参数")
    private String methodArgs;
}
