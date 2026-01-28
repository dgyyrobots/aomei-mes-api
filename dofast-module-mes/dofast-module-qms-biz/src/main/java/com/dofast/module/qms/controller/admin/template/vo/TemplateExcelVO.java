package com.dofast.module.qms.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 检测模板 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class TemplateExcelVO {

    @ExcelProperty("检测模板ID")
    private Long id;

    @ExcelProperty("检测模板编号")
    private String templateCode;

    @ExcelProperty("检测模板名称")
    private String templateName;

    @ExcelProperty("检测种类")
    private String qcTypes;

    @ExcelProperty("是否启用")
    private String enableFlag;

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
