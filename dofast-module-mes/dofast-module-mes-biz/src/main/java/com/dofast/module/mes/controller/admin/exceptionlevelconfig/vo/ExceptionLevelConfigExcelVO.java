package com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 异常等级配置 Excel VO
 *
 * @author 惠智造
 */
@Data
public class ExceptionLevelConfigExcelVO {

    @ExcelProperty("异常等级ID")
    private Long id;

    @ExcelProperty("等级代码")
    private String levelCode;

    @ExcelProperty("等级名称")
    private String levelName;

    @ExcelProperty("显示颜色")
    private String color;

    @ExcelProperty("要求响应时间(分钟)")
    private Integer responseTime;

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
