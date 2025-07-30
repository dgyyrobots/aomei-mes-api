package com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 点检计划记录单头 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DvCheckPlanHeaderLogBaseVO {

    @Schema(description = "计划编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "计划编码不能为空")
    private String planCode;

    @Schema(description = "计划名称", example = "芋艿")
    private String planName;

    @Schema(description = "计划类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "计划类型不能为空")
    private String planType;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3066")
    @NotNull(message = "设备ID不能为空")
    private Long machineryId;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备编码不能为空")
    private String machineryCode;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "设备名称不能为空")
    private String machineryName;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

}
