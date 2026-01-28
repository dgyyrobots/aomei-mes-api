package com.dofast.module.cal.controller.admin.teamschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 班组排班 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeamScheduleRespVO extends TeamScheduleBaseVO {

    @Schema(description = "排班ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16000")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
