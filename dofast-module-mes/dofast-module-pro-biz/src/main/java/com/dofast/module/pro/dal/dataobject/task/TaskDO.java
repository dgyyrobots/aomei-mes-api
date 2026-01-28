package com.dofast.module.pro.dal.dataobject.task;

import cn.hutool.core.date.DateTime;
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
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 生产任务 DO
 *
 * @author 芋道源码
 */
@TableName("pro_task")
@KeySequence("pro_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDO extends BaseDO {

    /**
     * 任务ID
     */
    @TableId
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
    private Double quantity;
    /**
     * 已生产数量
     */
    private Double quantityProduced;
    /**
     * 合格品数量
     */
    private Double quantityQuanlify;
    /**
     * 不良品数量
     */
    private Double quantityUnquanlify;
    /**
     * 调整数量
     */
    private Double quantityChanged;
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
     * 是否打印（0：未打印，1已打印）
     */
    private String isPrint;
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

    // 虚拟字段
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

    /**
     * 派工状态
     */
    private String taskStatus;

    /**
     * 派工设备列表
     */
    private String machineryCodes;

    /**
     * 报工单流水号
     */
    private String feedbackSerial;

    /**
     * 工序工作序
     */
    private Long processSequence;

    /**
     * 工单工作序
     */
    private Long workorderSequence;


}
