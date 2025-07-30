package com.dofast.module.pro.dal.dataobject.feedbackaudit;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 报工审批主表 DO
 *
 * @author 惠智造
 */
@TableName("pro_feedback_audit")
@KeySequence("pro_feedback_audit_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAuditDO extends BaseDO {

    /**
     * 主键ID
     */
    @TableId
    private Long id;
    /**
     * 审批单号
     */
    private String auditCode;
    /**
     * 提交人ID
     */
    private Long submitUserId;
    /**
     * 提交人姓名
     */
    private String submitUserName;

    /**
     * 提交人昵称
     */
    private String submitNickName;

    /**
     * 提交时间
     */
    private LocalDateTime submitTime;
    /**
     * 审批人ID
     */
    private Long auditUserId;
    /**
     * 审批人姓名
     */
    private String auditUserName;

    /**
     * 审批人昵称
     */
    private String auditNickName;

    /**
     * 审批时间
     */
    private LocalDateTime auditTime;
    /**
     * 状态(PENDING:待审批 APPROVED:已审批 REJECTED:已驳回)
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

}
