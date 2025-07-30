package com.dofast.module.pro.controller.admin.task.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 生产任务分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaskPageReqVO extends PageParam {

    @Schema(description = "任务编号")
    private String taskCode;

    @Schema(description = "任务名称", example = "李四")
    private String taskName;

    @Schema(description = "生产工单ID", example = "11436")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "工单名称", example = "王五")
    private String workorderName;

    @Schema(description = "工作站ID", example = "1598")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "张三")
    private String workstationName;

    @Schema(description = "工序ID", example = "4513")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "芋艿")
    private String processName;

    @Schema(description = "产品物料ID", example = "19780")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "李四")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "排产数量")
    private BigDecimal quantity;

    @Schema(description = "已生产数量")
    private BigDecimal quantityProduced;

    @Schema(description = "合格品数量")
    private BigDecimal quantityQuanlify;

    @Schema(description = "不良品数量")
    private BigDecimal quantityUnquanlify;

    @Schema(description = "调整数量")
    private BigDecimal quantityChanged;

    @Schema(description = "客户ID", example = "28858")
    private Long clientId;

    @Schema(description = "客户编码")
    private String clientCode;

    @Schema(description = "客户名称", example = "李四")
    private String clientName;

    @Schema(description = "客户简称")
    private String clientNick;

    @Schema(description = "开始生产时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "生产时长")
    private Integer duration;

    @Schema(description = "完成生产时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "甘特图显示颜色")
    private String colorCode;

    @Schema(description = "需求日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] requestDate;

    @Schema(description = "生产状态", example = "1")
    private String status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "是否打印（0：未打印，1已打印）")
    private String isPrint;

    @Schema(description = "实际开始操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] actualStartTime;

    @Schema(description = "实际结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] actualEndTime;

    @Schema(description = "是否已完工 （0：未完工 1：已完工）")
    private String isOver;

    @Schema(description = "销售单编码")
    private String sourceCode;

    @Schema(description = "母批次号")
    private String parentBatchCode;

    @Schema(description = "流水号")
    private String serial;


    @Schema(description = "报工状态")
    private String feedbackStatus;

    @Schema(description = "设备名称")
    private String machineryName;

    @Schema(description = "设备编码")
    private String machineryCode;

    @Schema(description = "设备Id")
    private String machineryId;

    @Schema(description = "派工状态")
    private String taskStatus;

    @Schema(description = "派工设备列表")
    private String machineryCodes;

    @Schema(description = "报工流水号")
    private String feedbackSerial;
}
