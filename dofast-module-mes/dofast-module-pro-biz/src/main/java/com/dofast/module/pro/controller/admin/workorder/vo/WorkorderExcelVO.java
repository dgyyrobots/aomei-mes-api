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

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 生产工单 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class WorkorderExcelVO {

    @ExcelProperty("工单ID")
    private Long id;

    @ExcelProperty("工单编码")
    private String workorderCode;

    @ExcelProperty("工单名称")
    private String workorderName;

    @ExcelProperty("来源类型")
    private String orderSource;

    @ExcelProperty("来源单据")
    private String sourceCode;

    @ExcelProperty("产品ID")
    private Long productId;

    @ExcelProperty("产品编号")
    private String productCode;

    @ExcelProperty("产品名称")
    private String productName;

    @ExcelProperty("规格型号")
    private String productSpc;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("生产数量")
    private Double quantity;

    @ExcelProperty("已生产数量")
    private Double quantityProduced;

    @ExcelProperty("调整数量")
    private Double quantityChanged;

    @ExcelProperty("已排产数量")
    private Double quantityScheduled;

    @ExcelProperty("客户ID")
    private Long clientId;

    @ExcelProperty("客户编码")
    private String clientCode;

    @ExcelProperty("客户名称")
    private String clientName;

    @ExcelProperty("批次号")
    private String batchCode;

    @ExcelProperty("需求日期")
    private LocalDateTime requestDate;

    @ExcelProperty("父工单")
    private Long parentId;

    @ExcelProperty("所有父节点ID")
    private String ancestors;

    @ExcelProperty("单据状态")
    private String status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("预留字段1")
    private String attr1;

    @ExcelProperty("预留字段2")
    private String attr2;

    @ExcelProperty("预留字段3")
    private Integer attr3;

    @ExcelProperty("预留字段4")
    private Integer attr4;

    @ExcelProperty("附件列表")
    private String adjuncts;

    @ExcelProperty("是否外协")
    private Boolean isOut;

    @Schema(description = "结案标识")
    private String closeFlag;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


}
