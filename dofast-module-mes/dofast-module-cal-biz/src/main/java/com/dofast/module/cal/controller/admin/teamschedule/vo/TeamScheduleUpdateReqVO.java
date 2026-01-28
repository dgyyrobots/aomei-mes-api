package com.dofast.module.cal.controller.admin.teamschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 班组排班更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeamScheduleUpdateReqVO extends TeamScheduleBaseVO {

    @Schema(description = "排班ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16000")
    @NotNull(message = "排班ID不能为空")
    private Long id;

}
