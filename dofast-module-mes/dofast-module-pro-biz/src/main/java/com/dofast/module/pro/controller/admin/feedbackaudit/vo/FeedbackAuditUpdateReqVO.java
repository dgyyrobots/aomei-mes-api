package com.dofast.module.pro.controller.admin.feedbackaudit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 报工审批主表更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditUpdateReqVO extends FeedbackAuditBaseVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19872")
    @NotNull(message = "主键ID不能为空")
    private Long id;

}
