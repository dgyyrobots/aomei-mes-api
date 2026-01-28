package com.dofast.module.qms.controller.admin.templateproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 检测模板-产品 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TemplateProductBaseVO {

    @Schema(description = "检测模板ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15949")
    @NotNull(message = "检测模板ID不能为空")
    private Long templateId;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8851")
    @NotNull(message = "产品物料ID不能为空")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "赵六")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "最低检测数")
    private Integer quantityCheck;

    @Schema(description = "最大不合格数")
    private Integer quantityUnqualified;

    @Schema(description = "最大致命缺陷率")
    private BigDecimal crRate;

    @Schema(description = "最大严重缺陷率")
    private BigDecimal majRate;

    @Schema(description = "最大轻微缺陷率")
    private BigDecimal minRate;

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

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称")
    private String processName;

    public Long getId(){
        return null;
    }
}
