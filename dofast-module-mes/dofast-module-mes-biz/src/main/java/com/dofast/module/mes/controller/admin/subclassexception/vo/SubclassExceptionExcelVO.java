package com.dofast.module.mes.controller.admin.subclassexception.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 子类异常项配置 Excel VO
 *
 * @author 惠智造
 */
@Data
public class SubclassExceptionExcelVO {

    @ExcelProperty("子类异常项ID")
    private Long id;

    @ExcelProperty("异常类型(数据字典)")
    private String exceptionType;

    @ExcelProperty("子类异常项编号")
    private String subclassExceptionCode;

    @ExcelProperty("子类异常项名称")
    private String subclassExceptionName;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
