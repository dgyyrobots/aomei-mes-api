package com.dofast.module.mes.controller.admin.exception.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 异常登记 Excel VO
 *
 * @author 惠智造
 */
@Data
public class ExceptionExcelVO {

    @ExcelProperty("异常等级ID")
    private Long id;

    @ExcelProperty("异常编号")
    private String exceptionCode;

    @ExcelProperty("异常类型")
    private String exceptionType;

    @ExcelProperty("异常子类型")
    private String subclassExceptionCode;

    @ExcelProperty("异常子类型")
    private String subclassExceptionName;

    @ExcelProperty("异常等级")
    private String exceptionLevelCode;

    @ExcelProperty("异常等级")
    private String exceptionLevelName;

    @ExcelProperty("异常等级颜色")
    private String exceptionLevelColor;

    @ExcelProperty("异常标题")
    private String title;

    @ExcelProperty("详细描述")
    private String description;

    @ExcelProperty("登记时间")
    private LocalDateTime registerTime;

    @ExcelProperty("登记人")
    private String registerUser;

    @ExcelProperty("所属车间")
    private String workshop;

    @ExcelProperty("所属车间ID")
    private Long workshopId;

    @ExcelProperty("所属车间")
    private String workshopName;

    @ExcelProperty("所属车间编码")
    private String workshopCode;


    @ExcelProperty("所属工序ID")
    private Long processId;

    @ExcelProperty("所属工序")
    private String processCode;

    @ExcelProperty("所属工序名称")
    private String processName;

    @ExcelProperty("关联设备编码")
    private String relatedMachineryCode;

    @ExcelProperty("关联设备名称")
    private String relatedMachineryName;

    @ExcelProperty("关联ERP设备编码")
    private String relatedErpMachineryCode;


    @ExcelProperty("关联物料")
    private String relatedMaterial;

    @ExcelProperty("关联工单")
    private String relatedWorkorder;

    @ExcelProperty("关联任务编码")
    private String relatedTaskCode;

    @ExcelProperty("关联任务ID")
    private Long relatedTaskId;

    @ExcelProperty("状态")
    private String status;

    @ExcelProperty("预计影响")
    private String estimatedImpact;

    @ExcelProperty("解决方案")
    private String solution;

    @ExcelProperty("关闭时间")
    private LocalDateTime closeTime;

    @ExcelProperty("附件信息")
    private String attachments;

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
