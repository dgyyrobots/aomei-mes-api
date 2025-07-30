package com.dofast.module.cmms.dal.dataobject.dvcheckplanheaderlog;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 点检计划记录单头 DO
 *
 * @author 惠智造
 */
@TableName("cmms_dv_check_plan_header_log")
@KeySequence("cmms_dv_check_plan_header_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvCheckPlanHeaderLogDO extends BaseDO {

    /**
     * 计划记录ID
     */
    @TableId
    private Long id;
    /**
     * 计划编码
     */
    private String planCode;
    /**
     * 计划名称
     */
    private String planName;
    /**
     * 计划类型
     */
    private String planType;
    /**
     * 设备ID
     */
    private Long machineryId;
    /**
     * 设备编码
     */
    private String machineryCode;
    /**
     * 设备名称
     */
    private String machineryName;
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

}
