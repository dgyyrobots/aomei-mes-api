package com.dofast.module.qms.controller.admin.iqc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 来料检验单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class IqcBaseVO {

    @Schema(description = "来料检验单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "来料检验单编号不能为空")
    private String iqcCode;

    @Schema(description = "来料检验单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "来料检验单名称不能为空")
    private String iqcName;

    @Schema(description = "检验模板ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7088")
    private Long templateId;

    @Schema(description = "供应商ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22368")
    private Long vendorId;

    @Schema(description = "供应商编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "供应商编码不能为空")
    private String vendorCode;

    @Schema(description = "供应商名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String vendorName;

    @Schema(description = "供应商简称")
    private String vendorNick;

    @Schema(description = "供应商批次号")
    private String vendorBatch;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "22383")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "张三")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "最低检测数")
    private BigDecimal quantityMinCheck;

    @Schema(description = "最大不合格数")
    private BigDecimal quantityMaxUnqualified;

    @Schema(description = "本次接收数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "本次接收数量不能为空")
    private BigDecimal quantityRecived;

    @Schema(description = "本次检测数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "本次检测数量不能为空")
    private BigDecimal quantityCheck;

    @Schema(description = "不合格数")
    private BigDecimal quantityUnqualified;

    @Schema(description = "致命缺陷率")
    private BigDecimal crRate;

    @Schema(description = "严重缺陷率")
    private BigDecimal majRate;

    @Schema(description = "轻微缺陷率")
    private BigDecimal minRate;

    @Schema(description = "致命缺陷数量")
    private BigDecimal crQuantity;

    @Schema(description = "严重缺陷数量")
    private BigDecimal majQuantity;

    @Schema(description = "轻微缺陷数量")
    private BigDecimal minQuantity;

    @Schema(description = "检测结果")
    private String checkResult;

    @Schema(description = "来料日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime reciveDate;

    @Schema(description = "检测日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime inspectDate;

    @Schema(description = "检测人员")
    private String inspector;

    @Schema(description = "单据状态", example = "1")
    private String status;

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

    @Schema(description = "附件")
    private String adjuncts;

    @Schema(description = "采购单号")
    private String poNo;



    public Long getId(){
        return null;
    }
}
