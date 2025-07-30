package com.dofast.module.pro.controller.admin.task.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 生产任务 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class TaskExcelVO {

    @ExcelProperty("任务ID")
    private Long id;

    @ExcelProperty("任务编号")
    private String taskCode;

    @ExcelProperty("任务名称")
    private String taskName;

    @ExcelProperty("生产工单ID")
    private Long workorderId;

    @ExcelProperty("生产工单编号")
    private String workorderCode;

    @ExcelProperty("工单名称")
    private String workorderName;

    @ExcelProperty("工作站ID")
    private Long workstationId;

    @ExcelProperty("工作站编号")
    private String workstationCode;

    @ExcelProperty("工作站名称")
    private String workstationName;

    @ExcelProperty("工序ID")
    private Long processId;

    @ExcelProperty("工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("产品物料ID")
    private Long itemId;

    @ExcelProperty("产品物料编码")
    private String itemCode;

    @ExcelProperty("产品物料名称")
    private String itemName;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("排产数量")
    private BigDecimal quantity;

    @ExcelProperty("已生产数量")
    private BigDecimal quantityProduced;

    @ExcelProperty("合格品数量")
    private BigDecimal quantityQuanlify;

    @ExcelProperty("不良品数量")
    private BigDecimal quantityUnquanlify;

    @ExcelProperty("调整数量")
    private BigDecimal quantityChanged;

    @ExcelProperty("客户ID")
    private Long clientId;

    @ExcelProperty("客户编码")
    private String clientCode;

    @ExcelProperty("客户名称")
    private String clientName;

    @ExcelProperty("客户简称")
    private String clientNick;

    @ExcelProperty("开始生产时间")
    private LocalDateTime startTime;

    @ExcelProperty("生产时长")
    private Integer duration;

    @ExcelProperty("完成生产时间")
    private LocalDateTime endTime;

    @ExcelProperty("甘特图显示颜色")
    private String colorCode;

    @ExcelProperty("需求日期")
    private LocalDateTime requestDate;

    @ExcelProperty("生产状态")
    private String status;

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

    @ExcelProperty("是否打印（0：未打印，1已打印）")
    private String isPrint;

    @ExcelProperty("实际开始操作时间")
    private LocalDateTime actualStartTime;

    @ExcelProperty("实际结束时间")
    private LocalDateTime actualEndTime;

    @ExcelProperty("母批次号")
    private String parentBatchCode;

    @ExcelProperty("流水号")
    private String serial;

    @ExcelProperty( "报工状态")
    private String feedbackStatus;

    @ExcelProperty("设备名称")
    private String machineryName;

    @ExcelProperty("设备编码")
    private String machineryCode;

    @ExcelProperty("设备Id")
    private String machineryId;

    @ExcelProperty("派工状态")
    private String taskStatus;

    @ExcelProperty("派工设备列表")
    private String machineryCodes;

    @ExcelProperty("报工流水号")
    private String feedbackSerial;
}
