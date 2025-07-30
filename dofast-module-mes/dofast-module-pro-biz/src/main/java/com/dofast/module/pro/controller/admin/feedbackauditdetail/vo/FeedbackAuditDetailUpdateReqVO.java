package com.dofast.module.pro.controller.admin.feedbackauditdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 报工审批明细更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditDetailUpdateReqVO extends FeedbackAuditDetailBaseVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17346")
    @NotNull(message = "主键ID不能为空")
    private Long id;

}
