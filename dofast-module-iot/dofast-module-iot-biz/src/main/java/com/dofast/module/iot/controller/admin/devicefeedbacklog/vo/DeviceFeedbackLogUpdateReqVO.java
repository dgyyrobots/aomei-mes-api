package com.dofast.module.iot.controller.admin.devicefeedbacklog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 设备产量日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceFeedbackLogUpdateReqVO extends DeviceFeedbackLogBaseVO {

    @Schema(description = "设备产量日志ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27625")
    @NotNull(message = "设备产量日志ID不能为空")
    private Long id;

}
