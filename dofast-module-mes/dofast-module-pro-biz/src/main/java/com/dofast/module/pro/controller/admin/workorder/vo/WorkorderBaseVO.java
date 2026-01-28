package com.dofast.module.pro.controller.admin.workorder.vo;

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
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 生产工单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class WorkorderBaseVO {

    @Schema(description = "工单编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "工单编码不能为空")
    private String workorderCode;

    @Schema(description = "工单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "工单名称不能为空")
    private String workorderName;

    @Schema(description = "来源类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "来源类型不能为空")
    private String orderSource;

    @Schema(description = "来源单据")
    private String sourceCode;

    @Schema(description = "产品ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21865")
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    @Schema(description = "产品编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "产品编号不能为空")
    private String productCode;

    @Schema(description = "产品名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotNull(message = "产品名称不能为空")
    private String productName;

    @Schema(description = "规格型号")
    private String productSpc;

    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "单位不能为空")
    private String unitOfMeasure;

    @Schema(description = "生产数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "生产数量不能为空")
    private Double quantity;

    @Schema(description = "已生产数量")
    private Double quantityProduced;

    @Schema(description = "调整数量")
    private Double quantityChanged;

    @Schema(description = "已排产数量")
    private Double quantityScheduled;

    @Schema(description = "客户ID", example = "19177")
    private Long clientId;

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "赵六")
    private String clientName;

    @Schema(description = "批次号")
    private String batchCode;

    @Schema(description = "需求日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "需求日期不能为空")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime requestDate;

    @Schema(description = "父工单", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "17864")
    @NotNull(message = "父工单不可以为空")
    private Long parentId;

    @Schema(description = "所有父节点ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String ancestors;

    @Schema(description = "单据状态", example = "2")
    private String status;

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

    @Schema(description = "销售单ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long mixinOrderId;

    @Schema(description = "附件列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String adjuncts;

    @Schema(description = "是否外协", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean isOut;

    @Schema(description = "工序编码")
    private String routeCode;

    @Schema(description = "结案标识")
    private String closeFlag;

    public Long getId(){
        return null;
    }
}
