package com.dofast.module.pro.controller.admin.feedbackaudititem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 报工审批汇总更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditItemUpdateReqVO extends FeedbackAuditItemBaseVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9155")
    @NotNull(message = "主键ID不能为空")
    private Long id;

}
