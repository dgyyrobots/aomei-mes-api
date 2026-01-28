package com.dofast.module.cal.controller.admin.teamschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import javax.validation.constraints.*;

/**
 * 班组排班 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TeamScheduleBaseVO {

    @Schema(description = "班组ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14089")
    @NotNull(message = "班组ID不能为空")
    private Long teamId;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10137")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "工作日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "工作日期不能为空")
    private LocalDate workDate;

    @Schema(description = "班次类型：0-白班，1-夜班", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "班次类型：0-白班，1-夜班不能为空")
    private String shiftType;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
