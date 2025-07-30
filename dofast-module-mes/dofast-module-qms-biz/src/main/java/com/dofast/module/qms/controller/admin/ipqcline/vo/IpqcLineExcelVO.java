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

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 过程检验单行 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class IpqcLineExcelVO {

    @ExcelProperty("记录ID")
    private Long id;

    @ExcelProperty("检验单ID")
    private Long ipqcId;

    @ExcelProperty("检测项ID")
    private Long indexId;

    @ExcelProperty("检测项编码")
    private String indexCode;

    @ExcelProperty("检测项名称")
    private String indexName;

    @ExcelProperty("检测项类型")
    private String indexType;

    @ExcelProperty("检测工具")
    private String qcTool;

    @ExcelProperty("检测要求")
    private String checkMethod;

    @ExcelProperty("标准值")
    private BigDecimal standerVal;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("误差上限")
    private BigDecimal thresholdMax;

    @ExcelProperty("误差下限")
    private BigDecimal thresholdMin;

    @ExcelProperty("致命缺陷数量")
    private BigDecimal crQuantity;

    @ExcelProperty("严重缺陷数量")
    private BigDecimal majQuantity;

    @ExcelProperty("轻微缺陷数量")
    private BigDecimal minQuantity;

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

    @ExcelProperty("检验数值1")
    private BigDecimal inspectionValue1;

    @ExcelProperty("检验数值2")
    private BigDecimal inspectionValue2;

    @ExcelProperty("检验数值3")
    private BigDecimal inspectionValue3;

    @ExcelProperty("检验数值4")
    private BigDecimal inspectionValue4;

    @ExcelProperty("检验数值5")
    private BigDecimal inspectionValue5;

    @ExcelProperty("检验结果")
    private String inspectionResult;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
