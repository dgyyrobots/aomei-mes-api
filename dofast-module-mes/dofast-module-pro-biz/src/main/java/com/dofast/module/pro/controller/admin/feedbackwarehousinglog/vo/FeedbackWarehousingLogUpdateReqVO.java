package com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 报工入库日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackWarehousingLogUpdateReqVO extends FeedbackWarehousingLogBaseVO {

    @Schema(description = "记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14480")
    @NotNull(message = "记录ID不能为空")
    private Long id;

}
