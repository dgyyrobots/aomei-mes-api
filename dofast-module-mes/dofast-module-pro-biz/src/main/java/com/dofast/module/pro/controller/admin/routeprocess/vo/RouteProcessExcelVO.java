package com.dofast.module.pro.controller.admin.routeprocess.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 工艺组成 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class RouteProcessExcelVO {

    @ExcelProperty("记录ID")
    private Long id;

    @ExcelProperty("工艺路线ID")
    private Long routeId;

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("序号")
    private Integer orderNum;

    @ExcelProperty("工序编码")
    private String nextProcessCode;

    @ExcelProperty("工序名称")
    private String nextProcessName;

    @ExcelProperty("与下一道工序关系")
    private String linkType;

    @ExcelProperty("准备时间")
    private Integer defaultPreTime;

    @ExcelProperty("等待时间")
    private Integer defaultSufTime;

    @ExcelProperty("甘特图显示颜色")
    private String colorCode;

    @ExcelProperty("关键工序")
    private String keyFlag;

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

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("工序ID")
    private Long processId;

    @ExcelProperty("工序ID")
    private Long nextProcessId;

    @ExcelProperty("项次")
    private Long sequence;

    @ExcelProperty("工作序")
    private Long workorderSequence;

    @ExcelProperty("转入单位")
    private String receivingUnits;

    @ExcelProperty("转入单位分子")
    private BigDecimal receivingUnitsConversionNumerator;

    @ExcelProperty("转入单位分母")
    private BigDecimal receivingUnitsConversionDenominator;

    @ExcelProperty("转出单位")
    private String outUnits;

    @ExcelProperty("转出单位分子")
    private BigDecimal outUnitsConversionNumerator;

    @ExcelProperty("转出单位分母")
    private BigDecimal outUnitsConversionDenominator;

    @ExcelProperty("工序工作序")
    private Long processSequence;

}
