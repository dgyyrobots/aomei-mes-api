package com.dofast.module.pro.controller.admin.feedbackauditdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.constraints.NotNull;

/**
 * 报工审批明细 Excel VO
 *
 * @author 惠智造
 */
@Data
public class FeedbackAuditDetailExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("审批主表ID")
    private Long auditId;

    @ExcelProperty("审批汇总表ID")
    private Long auditItemId;

    @ExcelProperty("报工记录ID")
    private Long feedbackId;

    @ExcelProperty("报工类型")
    private String feedbackType;

    @ExcelProperty("报工单编号")
    private String feedbackCode;

    @ExcelProperty("工作站ID")
    private Long workstationId;

    @ExcelProperty("工作站编号")
    private String workstationCode;

    @ExcelProperty("工作站名称")
    private String workstationName;

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

    @ExcelProperty("排产数量")
    private BigDecimal quantity;

    @ExcelProperty("本次报工数量")
    private BigDecimal quantityFeedback;

    @ExcelProperty("合格品数量")
    private BigDecimal quantityQualified;

    @ExcelProperty("不良品数量")
    private BigDecimal quantityUnquanlified;

    @ExcelProperty("工艺损耗数量")
    private BigDecimal quantityExcess;

    @ExcelProperty("报工用户名")
    private String userName;

    @ExcelProperty("昵称")
    private String nickName;

    @ExcelProperty("报工途径")
    private String feedbackChannel;

    @ExcelProperty("报工时间")
    private LocalDateTime feedbackTime;

    @ExcelProperty("记录人")
    private String recordUser;

    @ExcelProperty("记录人名称")
    private String recordNick;

    @ExcelProperty("批次号")
    private String batchCode;

    @ExcelProperty("ERP批次号")
    private String erpBatchCode;

    @ExcelProperty("班组编号")
    private String teamCode;

    @ExcelProperty("班组负责人")
    private String principalName;

    @ExcelProperty("班组负责人Id")
    private Long principalId;

    @ExcelProperty("班组信息(0白班1晚班)")
    private String shiftInfo;

    @ExcelProperty("来源编码")
    private String originCode;

    @ExcelProperty("生产设备名称")
    private String machineryName;

    @ExcelProperty("生产设备编码")
    private String machineryCode;

    @ExcelProperty("生产设备ID")
    private Long machineryId;

    @ExcelProperty("ERP报工标识")
    private String erpFeedbackStatus;

    @ExcelProperty("ERP入库标识")
    private String erpWarehousingStatus;

    @ExcelProperty("合并需求")
    private String mergeStatus;

    @ExcelProperty("转换数量")
    private BigDecimal conversionQuantity;

    @ExcelProperty("转换单位")
    private String conversionUnit;

    @ExcelProperty("转换不合格数量")
    private BigDecimal conversionQuantityUnquanlified;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
