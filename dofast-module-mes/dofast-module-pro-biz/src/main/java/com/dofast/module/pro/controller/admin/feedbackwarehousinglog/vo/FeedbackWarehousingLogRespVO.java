package com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 报工入库日志 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackWarehousingLogRespVO extends FeedbackWarehousingLogBaseVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14480")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
