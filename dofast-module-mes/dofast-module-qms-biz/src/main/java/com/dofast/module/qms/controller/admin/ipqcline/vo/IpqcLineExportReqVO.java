package com.dofast.module.qms.controller.admin.ipqcline.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 过程检验单行 Excel 导出 Request VO，参数和 IpqcLinePageReqVO 是一致的")
@Data
public class IpqcLineExportReqVO {

    @Schema(description = "检验单ID", example = "7936")
    private Long ipqcId;

    @Schema(description = "检测项ID", example = "25241")
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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
