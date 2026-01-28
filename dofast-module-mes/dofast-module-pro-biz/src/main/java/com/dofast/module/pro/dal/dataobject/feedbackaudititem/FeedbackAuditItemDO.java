package com.dofast.module.pro.dal.dataobject.feedbackaudititem;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 报工审批汇总 DO
 *
 * @author 惠智造
 */
@TableName("pro_feedback_audit_item")
@KeySequence("pro_feedback_audit_item_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAuditItemDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 审批单ID
     */
    private String auditId;
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
     * 本次报工数量
     */
    private BigDecimal sumQuantityFeedback;
    /**
     * 合格品数量
     */
    private BigDecimal sumQuantityQualified;
    /**
     * 不良品数量
     */
    private BigDecimal sumQuantityUnquanlified;

    /**
     * 工艺损耗数量
     */
    private BigDecimal sumQuantityExcess;

    /**
     * 报工用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 备注
     */
    private String remark;
    /**
     * 参与班组成员名称
     */
    private String allPrincipal;

    /**
     * 设备Id
     */
    private Long machineryId;

    /**
     * 设备名称
     */
    private String machineryName;

    /**
     * 设备编码
     */
    private String machineryCode;
}
