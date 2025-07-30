package com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 点检计划记录单身 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DvCheckPlanLineLogRespVO extends DvCheckPlanLineLogBaseVO {

    @Schema(description = "计划记录单身ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31715")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
