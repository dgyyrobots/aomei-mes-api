package com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 点检计划记录单身更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DvCheckPlanLineLogUpdateReqVO extends DvCheckPlanLineLogBaseVO {

    @Schema(description = "计划记录单身ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31715")
    @NotNull(message = "计划记录单身ID不能为空")
    private Long id;

}
