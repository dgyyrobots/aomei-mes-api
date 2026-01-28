package com.dofast.module.qms.controller.admin.defect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 常见缺陷 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DefectBaseVO {

    @Schema(description = "缺陷编码", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String defectCode;

    @Schema(description = "缺陷描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "缺陷描述不能为空")
    private String defectName;

    @Schema(description = "检测项类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "检测项类型不能为空")
    private String indexType;

    @Schema(description = "缺陷等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "缺陷等级不能为空")
    private String defectLevel;

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

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称")
    private String processName;

}
