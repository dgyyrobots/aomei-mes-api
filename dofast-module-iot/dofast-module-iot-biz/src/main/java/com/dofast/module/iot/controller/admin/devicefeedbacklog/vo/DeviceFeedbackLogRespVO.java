package com.dofast.module.iot.controller.admin.devicefeedbacklog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备产量日志 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceFeedbackLogRespVO extends DeviceFeedbackLogBaseVO {

    @Schema(description = "设备产量日志ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27625")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
