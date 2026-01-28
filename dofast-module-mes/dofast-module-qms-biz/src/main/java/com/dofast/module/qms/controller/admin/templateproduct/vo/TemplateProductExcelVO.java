package com.dofast.module.qms.controller.admin.templateproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 检测模板-产品 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class TemplateProductExcelVO {

    @ExcelProperty("记录ID")
    private Long id;

    @ExcelProperty("检测模板ID")
    private Long templateId;

    @ExcelProperty("产品物料ID")
    private Long itemId;

    @ExcelProperty("产品物料编码")
    private String itemCode;

    @ExcelProperty("产品物料名称")
    private String itemName;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("最低检测数")
    private Integer quantityCheck;

    @ExcelProperty("最大不合格数")
    private Integer quantityUnqualified;

    @ExcelProperty("最大致命缺陷率")
    private BigDecimal crRate;

    @ExcelProperty("最大严重缺陷率")
    private BigDecimal majRate;

    @ExcelProperty("最大轻微缺陷率")
    private BigDecimal minRate;

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

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
