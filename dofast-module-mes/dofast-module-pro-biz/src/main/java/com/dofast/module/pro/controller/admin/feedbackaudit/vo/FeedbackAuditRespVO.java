package com.dofast.module.pro.controller.admin.feedbackaudit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 报工审批主表 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditRespVO extends FeedbackAuditBaseVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19872")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "原始审批人ID")
    private Long originalAuditUserId;

    @Schema(description = "转审历史记录")
    private String transferHistory;

    @Schema(description = "来源审批单ID")
    private Long sourceAuditId;


    // 虚拟字段
    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称")
    private String processName;

    @Schema(description = "生产总量")
    private BigDecimal quantity;

    @Schema(description = "合格总量")
    private BigDecimal quantityQuality;

    @Schema(description = "工艺损耗总量")
    private BigDecimal quantityExcess;

    @Schema(description = "设备Id")
    private Long machineryId;

    @Schema(description = "设备名称")
    private String machineryName;

    @Schema(description = "设备编码")
    private String machineryCode;

    @Schema(description = "目标审批人")
    private Long targetAuditUserId;

    @Schema(description = "目标审批人")
    private List<Long> detailIds;

    @Schema(description = "请求参数")
    private String methodArgs;

}
