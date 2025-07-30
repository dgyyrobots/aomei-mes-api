package com.dofast.module.pro.dal.dataobject.feedbackmember;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 报工班组人员 DO
 *
 * @author 惠智造
 */
@TableName("pro_feedback_member")
@KeySequence("pro_feedback_member_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackMemberDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 报工单编号
     */
    private String feedbackId;
    /**
     * 生产任务编号
     */
    private String taskCode;
    /**
     * 班组编号
     */
    private String teamCode;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 岗位Id
     */
    private String postIds;

    /**
     * 生产数量
     */
    private BigDecimal quantity;

}
