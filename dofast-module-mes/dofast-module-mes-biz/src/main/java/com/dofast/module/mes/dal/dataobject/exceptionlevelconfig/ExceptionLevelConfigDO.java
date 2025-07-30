package com.dofast.module.mes.dal.dataobject.exceptionlevelconfig;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 异常等级配置 DO
 *
 * @author 惠智造
 */
@TableName("mes_exception_level_config")
@KeySequence("mes_exception_level_config_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionLevelConfigDO extends BaseDO {

    /**
     * 异常等级ID
     */
    @TableId
    private Long id;
    /**
     * 等级代码
     */
    private String levelCode;
    /**
     * 等级名称
     */
    private String levelName;
    /**
     * 显示颜色
     */
    private String color;
    /**
     * 要求响应时间(分钟)
     */
    private Integer responseTime;
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
