package com.dofast.module.pro.controller.admin.feedbackaudit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 报工审批主表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackAuditBaseVO {

    @Schema(description = "审批单号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "审批单号不能为空")
    private String auditCode;

    @Schema(description = "提交人ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29522")
    @NotNull(message = "提交人ID不能为空")
    private Long submitUserId;

    @Schema(description = "提交人姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "提交人姓名不能为空")
    private String submitUserName;

    @Schema(description = "提交人昵称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String submitNickName;

    @Schema(description = "提交时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "提交时间不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime submitTime;

    @Schema(description = "审批人ID", example = "21328")
    private Long auditUserId;

    @Schema(description = "审批人姓名", example = "张三")
    private String auditUserName;

    @Schema(description = "审批人昵称", example = "李四")
    private String auditNickName;

    @Schema(description = "审批时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime auditTime;

    @Schema(description = "状态(PENDING:待审批 APPROVED:已审批 REJECTED:已驳回)", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态(PENDING:待审批 APPROVED:已审批 REJECTED:已驳回)不能为空")
    private String status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "原始审批人ID")
    private Long originalAuditUserId;

    @Schema(description = "转审历史记录")
    private String transferHistory;

    @Schema(description = "来源审批单ID")
    private Long sourceAuditId;

    @Schema(description = "请求参数")
    private String methodArgs;





}
