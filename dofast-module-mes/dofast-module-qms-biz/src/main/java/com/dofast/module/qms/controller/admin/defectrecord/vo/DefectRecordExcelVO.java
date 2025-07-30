package com.dofast.module.qms.controller.admin.defectrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 检验单缺陷记录 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class DefectRecordExcelVO {

    @ExcelProperty("缺陷ID")
    private Long id;

    @ExcelProperty("检验单类型")
    private String qcType;

    @ExcelProperty("检验单ID")
    private Long qcId;

    @ExcelProperty("检验单行ID")
    private Long lineId;

    @ExcelProperty("缺陷描述")
    private String defectName;

    @ExcelProperty("缺陷等级")
    private String defectLevel;

    @ExcelProperty("缺陷数量")
    private BigDecimal defectQuantity;

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

}
