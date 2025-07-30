package com.dofast.module.pro.api.TaskApi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TaskDTO {
    /**
     * 任务ID
     */
    private Long id;
    /**
     * 任务编号
     */
    private String taskCode;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * 生产工单编号
     */
    private String workorderCode;
    /**
     * 工单名称
     */
    private String workorderName;
    /**
     * 工作站ID
     */
    private Long workstationId;
    /**
     * 工作站编号
     */
    private String workstationCode;
    /**
     * 工作站名称
     */
    private String workstationName;
    /**
     * 工序ID
     */
    private Long processId;
    /**
     * 工序编码
     */
    private String processCode;
    /**
     * 工序名称
     */
    private String processName;
    /**
     * 产品物料ID
     */
    private Long itemId;
    /**
     * 产品物料编码
     */
    private String itemCode;
    /**
     * 产品物料名称
     */
    private String itemName;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 排产数量
     */
    private BigDecimal quantity;
    /**
     * 已生产数量
     */
    private BigDecimal quantityProduced;
    /**
     * 合格品数量
     */
    private BigDecimal quantityQuanlify;
    /**
     * 不良品数量
     */
    private BigDecimal quantityUnquanlify;
    /**
     * 调整数量
     */
    private BigDecimal quantityChanged;
    /**
     * 客户ID
     */
    private Long clientId;
    /**
     * 客户编码
     */
    private String clientCode;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 客户简称
     */
    private String clientNick;
    /**
     * 开始生产时间
     */
    private LocalDateTime startTime;
    /**
     * 生产时长
     */
    private Long duration;
    /**
     * 完成生产时间
     */
    private LocalDateTime endTime;
    /**
     * 甘特图显示颜色
     */
    private String colorCode;
    /**
     * 需求日期
     */
    private LocalDateTime requestDate;
    /**
     * 生产状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预留字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private String attr2;
    /**
     * 预留字段3
     */
    private Integer attr3;
    /**
     * 预留字段4
     */
    private Integer attr4;

    /**
     * 实际开始操作时间
     */
    private LocalDateTime actualStartTime;

    /**
     * 实际结束时间
     */
    private LocalDateTime actualEndTime;

    /**
     * 母批次号
     */
    private String parentBatchCode;

    /**
     * 流水号
     */
    private String serial;

    /**
     * 报工状态
     */
    private String feedbackStatus;

    /**
     * 设备名称
     */
    private String machineryName;

    /**
     * 设备编号
     */
    private String machineryCode;

    /**
     * 设备Id
     */
    private String machineryId;
}
