package com.dofast.module.qms.controller.admin.defectrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 检验单缺陷记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DefectRecordBaseVO {

    @Schema(description = "检验单类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "检验单类型不能为空")
    private String qcType;

    @Schema(description = "检验单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7121")
    @NotNull(message = "检验单ID不能为空")
    private Long qcId;

    @Schema(description = "检验单行ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "2406")
    @NotNull(message = "检验单行ID不能为空")
    private Long lineId;

    @Schema(description = "缺陷描述", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "缺陷描述不能为空")
    private String defectName;

    @Schema(description = "缺陷等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "缺陷等级不能为空")
    private String defectLevel;

    @Schema(description = "缺陷数量")
    private BigDecimal defectQuantity;

    @Schema(description = "备注", example = "你猜")
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
