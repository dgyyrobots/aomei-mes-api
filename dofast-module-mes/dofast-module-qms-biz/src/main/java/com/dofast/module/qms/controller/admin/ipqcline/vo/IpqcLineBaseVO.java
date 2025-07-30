package com.dofast.module.qms.controller.admin.ipqcline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 过程检验单行 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class IpqcLineBaseVO {

    @Schema(description = "检验单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7936")
    @NotNull(message = "检验单ID不能为空")
    private Long ipqcId;

    @Schema(description = "检测项ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25241")
    private Long indexId;

    @Schema(description = "检测项编码")
    private String indexCode;

    @Schema(description = "检测项名称", example = "张三")
    private String indexName;

    @Schema(description = "检测项类型", example = "1")
    private String indexType;

    @Schema(description = "检测工具")
    private String qcTool;

    @Schema(description = "检测要求")
    private String checkMethod;

    @Schema(description = "标准值")
    private BigDecimal standerVal;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "误差上限")
    private BigDecimal thresholdMax;

    @Schema(description = "误差下限")
    private BigDecimal thresholdMin;

    @Schema(description = "致命缺陷数量")
    private BigDecimal crQuantity;

    @Schema(description = "严重缺陷数量")
    private BigDecimal majQuantity;

    @Schema(description = "轻微缺陷数量")
    private BigDecimal minQuantity;

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

    @Schema(description = "检验数值1")
    private BigDecimal inspectionValue1;

    @Schema(description = "检验数值2")
    private BigDecimal inspectionValue2;

    @Schema(description = "检验数值3")
    private BigDecimal inspectionValue3;

    @Schema(description = "检验数值4")
    private BigDecimal inspectionValue4;

    @Schema(description = "检验数值5")
    private BigDecimal inspectionValue5;

    @Schema(description = "检验结果")
    private String inspectionResult;

}
