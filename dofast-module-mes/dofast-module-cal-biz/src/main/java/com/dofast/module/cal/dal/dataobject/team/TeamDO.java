package com.dofast.module.cal.dal.dataobject.team;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 班组 DO
 *
 * @author 惠智造
 */
@TableName("cal_team")
@KeySequence("cal_team_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamDO extends BaseDO {

    /**
     * 班组ID
     */
    @TableId
    private Long id;
    /**
     * 班组编号
     */
    private String teamCode;
    /**
     * 班组名称
     */
    private String teamName;
    /**
     * 班组类型
     */
    private String calendarType;
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
     * 负责人Id
     */
    private Long principalId;
    /**
     * 负责人名称
     */
    private String principalName;
    /**
     * 班组人数
     */
    private Long personCount;

    /**
     * 设备名称
     */
    private String machineryName;

    /**
     * 设备编码
     */
    private String machineryCode;
    /**
     * 设备ID
     */
    private Long machineryId;

    /**
     * 夜班负责人Id
     */
    private Long nightPrincipalId;

    /**
     * 夜班负责人名称
     */
    private String nightPrincipalName;

}
