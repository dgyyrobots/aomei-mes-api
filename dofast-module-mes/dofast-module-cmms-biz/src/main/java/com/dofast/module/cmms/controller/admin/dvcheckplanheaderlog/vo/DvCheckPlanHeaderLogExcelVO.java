package com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 点检计划记录单头 Excel VO
 *
 * @author 惠智造
 */
@Data
public class DvCheckPlanHeaderLogExcelVO {

    @ExcelProperty("计划记录ID")
    private Long id;

    @ExcelProperty("计划编码")
    private String planCode;

    @ExcelProperty("计划名称")
    private String planName;

    @ExcelProperty("计划类型")
    private String planType;

    @ExcelProperty("设备ID")
    private Long machineryId;

    @ExcelProperty("设备编码")
    private String machineryCode;

    @ExcelProperty("设备名称")
    private String machineryName;

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
