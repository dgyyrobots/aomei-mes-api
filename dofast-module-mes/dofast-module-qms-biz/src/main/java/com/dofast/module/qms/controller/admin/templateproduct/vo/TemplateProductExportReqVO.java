package com.dofast.module.qms.controller.admin.templateproduct.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 检测模板-产品 Excel 导出 Request VO，参数和 TemplateProductPageReqVO 是一致的")
@Data
public class TemplateProductExportReqVO {

    @Schema(description = "检测模板ID", example = "15949")
    private Long templateId;

    @Schema(description = "产品物料ID", example = "8851")
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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
