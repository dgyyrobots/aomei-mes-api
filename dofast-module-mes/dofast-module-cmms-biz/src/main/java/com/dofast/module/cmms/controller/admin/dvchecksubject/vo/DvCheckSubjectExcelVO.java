package com.dofast.module.cmms.controller.admin.dvchecksubject.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 点检项目 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class DvCheckSubjectExcelVO {

    @ExcelProperty("流水号")
    private Long id;

    @ExcelProperty("计划ID")
    private Long planId;

    @ExcelProperty("项目ID")
    private Long subjectId;

    @ExcelProperty("项目编码")
    private String subjectCode;

    @ExcelProperty("项目名称")
    private String subjectName;

    @ExcelProperty("项目类型")
    private String subjectType;

    @ExcelProperty("项目内容")
    private String subjectContent;

    @ExcelProperty("标准")
    private String subjectStandard;

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
