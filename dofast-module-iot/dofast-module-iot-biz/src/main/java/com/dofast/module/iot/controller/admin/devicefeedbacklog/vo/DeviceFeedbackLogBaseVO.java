package com.dofast.module.iot.controller.admin.devicefeedbacklog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 设备产量日志 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DeviceFeedbackLogBaseVO {

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2817")
    @NotNull(message = "设备ID不能为空")
    private Long deviceId;

    @Schema(description = "设备名称", example = "赵六")
    private String deviceName;

    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "设备产量")
    private BigDecimal quantity;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "报工状态")
    private String  enableStatus;

    @Schema(description = "租户ID")
    private Long tenantId;
}
