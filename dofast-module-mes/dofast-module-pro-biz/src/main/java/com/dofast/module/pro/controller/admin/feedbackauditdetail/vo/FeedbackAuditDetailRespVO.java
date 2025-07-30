package com.dofast.module.pro.controller.admin.feedbackauditdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 报工审批明细 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditDetailRespVO extends FeedbackAuditDetailBaseVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17346")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
