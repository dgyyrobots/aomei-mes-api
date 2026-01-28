package com.dofast.module.pro.controller.admin.feedbackaudititem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 报工审批汇总 Excel VO
 *
 * @author 惠智造
 */
@Data
public class FeedbackAuditItemExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("审批单ID")
    private String auditId;

    @ExcelProperty("生产工单ID")
    private Long workorderId;

    @ExcelProperty("生产工单编号")
    private String workorderCode;

    @ExcelProperty("生产工单名称")
    private String workorderName;

    @ExcelProperty("工序ID")
    private Long processId;

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("生产任务ID")
    private Long taskId;

    @ExcelProperty("生产任务编号")
    private String taskCode;

    @ExcelProperty("产品物料ID")
    private Long itemId;

    @ExcelProperty("产品物料编码")
    private String itemCode;

    @ExcelProperty("产品物料名称")
    private String itemName;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("本次报工数量")
    private BigDecimal sumQuantityFeedback;

    @ExcelProperty("合格品数量")
    private BigDecimal sumQuantityQualified;

    @ExcelProperty("不良品数量")
    private BigDecimal sumQuantityUnquanlified;

    @ExcelProperty("工艺损耗数量")
    private BigDecimal sumQuantityExcess;

    @ExcelProperty("报工用户名")
    private String userName;

    @ExcelProperty("昵称")
    private String nickName;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("参与班组成员名称")
    private String allPrincipal;

    @ExcelProperty("设备Id")
    private Long machineryId;

    @ExcelProperty("设备名称")
    private String machineryName;

    @ExcelProperty("设备编码")
    private String machineryCode;



}
