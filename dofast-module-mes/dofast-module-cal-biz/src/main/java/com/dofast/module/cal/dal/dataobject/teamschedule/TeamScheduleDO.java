package com.dofast.module.cal.dal.dataobject.teamschedule;

import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;
import java.time.LocalDate;

/**
 * 班组排班 DO
 *
 * @author 惠智造
 */
@TableName("cal_team_schedule")
@KeySequence("cal_team_schedule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamScheduleDO extends BaseDO {

    /**
     * 排班ID
     */
    @TableId
    private Long id;
    /**
     * 班组ID
     */
    private Long teamId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 工作日期
     */
    private LocalDate workDate;
    /**
     * 班次类型：0-白班，1-夜班
     */
    private String shiftType;
    /**
     * 备注
     */
    private String remark;

}
