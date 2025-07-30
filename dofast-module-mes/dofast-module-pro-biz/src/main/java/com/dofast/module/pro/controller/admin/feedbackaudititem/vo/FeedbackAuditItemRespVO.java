package com.dofast.module.pro.controller.admin.feedbackaudititem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 报工审批汇总 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditItemRespVO extends FeedbackAuditItemBaseVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9155")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
