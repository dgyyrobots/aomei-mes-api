package com.dofast.module.pro.dal.dataobject.feedbackauditdetail;

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
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 报工审批明细 DO
 *
 * @author 惠智造
 */
@TableName("pro_feedback_audit_detail")
@KeySequence("pro_feedback_audit_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAuditDetailDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 审批主表ID
     */
    private Long auditId;

    /**
     * 审批汇总表ID
     */
    private Long auditItemId;

    /**
     * 报工记录ID
     */
    private Long feedbackId;
    /**
     * 报工类型
     */
    private String feedbackType;
    /**
     * 报工单编号
     */
    private String feedbackCode;
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
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * 生产工单编号
     */
    private String workorderCode;
    /**
     * 生产工单名称
     */
    private String workorderName;
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
     * 生产任务ID
     */
    private Long taskId;
    /**
     * 生产任务编号
     */
    private String taskCode;
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
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 排产数量
     */
    private BigDecimal quantity;
    /**
     * 本次报工数量
     */
    private BigDecimal quantityFeedback;
    /**
     * 合格品数量
     */
    private BigDecimal quantityQualified;
    /**
     * 不良品数量
     */
    private BigDecimal quantityUnquanlified;

    /**
     * 工艺损耗数量
     */
    private BigDecimal quantityExcess;

    /**
     * 报工用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 报工途径
     */
    private String feedbackChannel;
    /**
     * 报工时间
     */
    private LocalDateTime feedbackTime;
    /**
     * 记录人
     */
    private String recordUser;
    /**
     * 记录人名称
     */
    private String recordNick;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * ERP批次号
     */
    private String erpBatchCode;
    /**
     * 班组编号
     */
    private String teamCode;
    /**
     * 班组负责人
     */
    private String principalName;
    /**
     * 班组负责人Id
     */
    private Long principalId;
    /**
     * 班组信息(0白班1晚班)
     */
    private String shiftInfo;
    /**
     * 来源编码
     */
    private String originCode;
    /**
     * 生产设备名称
     */
    private String machineryName;
    /**
     * 生产设备编码
     */
    private String machineryCode;
    /**
     * 生产设备ID
     */
    private Long machineryId;
    /**
     * ERP报工标识
     */
    private String erpFeedbackStatus;
    /**
     * ERP入库标识
     */
    private String erpWarehousingStatus;
    /**
     * 合并需求
     */
    private String mergeStatus;
    /**
     * 转换数量
     */
    private BigDecimal conversionQuantity;
    /**
     * 转换单位
     */
    private String conversionUnit;
    /**
     * 转换不合格数量
     */
    private BigDecimal conversionQuantityUnquanlified;

}
