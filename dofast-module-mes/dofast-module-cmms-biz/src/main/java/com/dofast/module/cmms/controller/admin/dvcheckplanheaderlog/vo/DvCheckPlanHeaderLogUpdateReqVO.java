package com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 点检计划记录单头更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DvCheckPlanHeaderLogUpdateReqVO extends DvCheckPlanHeaderLogBaseVO {

    @Schema(description = "计划记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "807")
    @NotNull(message = "计划记录ID不能为空")
    private Long id;

}
