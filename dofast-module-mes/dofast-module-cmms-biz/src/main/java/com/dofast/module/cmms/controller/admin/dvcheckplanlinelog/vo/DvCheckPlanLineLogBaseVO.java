package com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 点检计划记录单身 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DvCheckPlanLineLogBaseVO {

    @Schema(description = "计划记录表头ID", example = "7775")
    private Integer headerId;

    @Schema(description = "项目ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "29579")
    @NotNull(message = "项目ID不能为空")
    private Long subjectId;

    @Schema(description = "项目编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "项目编码不能为空")
    private String subjectCode;

    @Schema(description = "项目名称", example = "芋艿")
    private String subjectName;

    @Schema(description = "项目类型", example = "1")
    private String subjectType;

    @Schema(description = "项目内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "项目内容不能为空")
    private String subjectContent;

    @Schema(description = "标准")
    private String subjectStandard;

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
