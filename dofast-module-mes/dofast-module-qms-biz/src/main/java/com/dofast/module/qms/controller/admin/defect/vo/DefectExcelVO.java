package com.dofast.module.qms.controller.admin.defect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 常见缺陷 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class DefectExcelVO {

    @ExcelProperty("缺陷ID")
    private Long id;

    @ExcelProperty("缺陷编码")
    private String defectCode;

    @ExcelProperty("缺陷描述")
    private String defectName;

    @ExcelProperty("检测项类型")
    private String indexType;

    @ExcelProperty("缺陷等级")
    private String defectLevel;

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

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

}
