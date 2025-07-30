package com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 点检计划记录单头 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DvCheckPlanHeaderLogRespVO extends DvCheckPlanHeaderLogBaseVO {

    @Schema(description = "计划记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "807")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
