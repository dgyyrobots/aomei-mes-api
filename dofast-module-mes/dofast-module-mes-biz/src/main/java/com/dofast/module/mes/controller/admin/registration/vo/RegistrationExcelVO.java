package com.dofast.module.mes.controller.admin.registration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 计时登记 Excel VO
 *
 * @author 惠智造
 */
@Data
public class RegistrationExcelVO {

    @ExcelProperty("计时登记ID")
    private Long id;

    @ExcelProperty("登记编码")
    private String registrationCode;

    @ExcelProperty("登记名称")
    private String registrationName;

    @ExcelProperty("登记类型(数据字典)")
    private String registrationType;

    @ExcelProperty("起始时间")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @ExcelProperty("所属车间ID")
    private Long workshopId;

    @ExcelProperty("所属车间名称")
    private String workshopName;

    @ExcelProperty("所属车间编码")
    private String workshopCode;

    @ExcelProperty("关联设备设备编码")
    private String relatedMachineryCode;

    @ExcelProperty("关联设备名称")
    private String relatedMachineryName;

    @ExcelProperty("关联ERP设备编码")
    private String relatedErpMachineryCode;

    @ExcelProperty("关联工单")
    private String relatedWorkorder;

    @ExcelProperty("关联任务编码")
    private String relatedTaskCode;

    @ExcelProperty("关联任务ID")
    private Long relatedTaskId;

    @ExcelProperty("关联物料编码")
    private String relatedMaterialCode;

    @ExcelProperty("关联物料名称")
    private String relatedMaterialName;

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

    @ExcelProperty("历经时间(分)")
    private Integer durationTime;

    @ExcelProperty("计时状态")
    private String status;

}
