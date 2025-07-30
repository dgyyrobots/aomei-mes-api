package com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 异常等级配置 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ExceptionLevelConfigBaseVO {

    @Schema(description = "等级代码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "等级代码不能为空")
    private String levelCode;

    @Schema(description = "等级名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "等级名称不能为空")
    private String levelName;

    @Schema(description = "显示颜色", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "显示颜色不能为空")
    private String color;

    @Schema(description = "要求响应时间(分钟)")
    private Integer responseTime;

    @Schema(description = "备注", example = "你说的对")
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
