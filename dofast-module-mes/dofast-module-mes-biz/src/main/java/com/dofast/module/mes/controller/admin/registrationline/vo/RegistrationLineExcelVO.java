package com.dofast.module.mes.controller.admin.registrationline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 计时登记记录 Excel VO
 *
 * @author 惠智造
 */
@Data
public class RegistrationLineExcelVO {

    @ExcelProperty("计时登记记录Id")
    private Long id;

    @ExcelProperty("登记编码")
    private String registrationCode;

    @ExcelProperty("登记编码")
    private Long registrationId;

    @ExcelProperty("登记类型(数据字典)")
    private String registrationType;

    @ExcelProperty("起始时间")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @ExcelProperty("关联设备设备编码")
    private String relatedMachineryCode;

    @ExcelProperty("关联设备名称")
    private String relatedMachineryName;

    @ExcelProperty("关联ERP设备编码")
    private String relatedErpMachineryCode;

    @ExcelProperty("所属车间ID")
    private Long workshopId;

    @ExcelProperty("所属车间名称")
    private String workshopName;

    @ExcelProperty("所属车间编码")
    private String workshopCode;

    @ExcelProperty("关联工单")
    private String relatedWorkorder;

    @ExcelProperty("关联任务编码")
    private String relatedTaskCode;

    @ExcelProperty("关联任务Id")
    private Long relatedTaskId;

    @ExcelProperty("历经时间(分钟)")
    private Integer durationTime;

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
