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
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 生产任务 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TaskBaseVO {

    @Schema(description = "任务编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String taskCode;

    @Schema(description = "任务名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String taskName;

    @Schema(description = "生产工单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11436")
    private Long workorderId;

    @Schema(description = "生产工单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String workorderCode;

    @Schema(description = "工单名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    private String workorderName;

    @Schema(description = "工作站ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1598")
    private Long workstationId;

    @Schema(description = "工作站编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String workstationCode;

    @Schema(description = "工作站名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String workstationName;

    @Schema(description = "工序ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4513")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "芋艿")
    private String processName;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "19780")
    private Long itemId;

    @Schema(description = "产品物料编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String itemCode;

    @Schema(description = "产品物料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private String unitOfMeasure;

    @Schema(description = "排产数量", requiredMode = Schema.RequiredMode.REQUIRED)
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
    private LocalDateTime startTime;

    @Schema(description = "生产时长")
    private Integer duration;

    @Schema(description = "完成生产时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "甘特图显示颜色")
    private String colorCode;

    @Schema(description = "需求日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime requestDate;

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

    @Schema(description = "是否打印（0：未打印，1已打印）")
    private String isPrint;

    @Schema(description = "实际开始操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime actualStartTime;

    @Schema(description = "实际结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime actualEndTime;

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

    @Schema(description = "工序工作序")
    private Long processSequence;

    @Schema(description = "工单工作序")
    private Long workorderSequence;

}
