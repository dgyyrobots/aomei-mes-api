package com.dofast.module.pro.dal.dataobject.routeprocess;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 工艺组成 DO
 *
 * @author 芋道源码
 */
@TableName("pro_route_process")
@KeySequence("pro_route_process_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteProcessDO extends BaseDO {

    /**
     * 记录ID
     */
    @TableId
    private Long id;
    /**
     * 工艺路线ID
     */
    private Long routeId;
    /**
     * 工序编码
     */
    private String processCode;
    /**
     * 工序名称
     */
    private String processName;
    /**
     * 序号
     */
    private Integer orderNum;
    /**
     * 工序编码
     */
    private String nextProcessCode;
    /**
     * 工序名称
     */
    private String nextProcessName;
    /**
     * 与下一道工序关系
     */
    private String linkType;
    /**
     * 准备时间
     */
    private Integer defaultPreTime;
    /**
     * 等待时间
     */
    private Integer defaultSufTime;
    /**
     * 甘特图显示颜色
     */
    private String colorCode;
    /**
     * 关键工序
     */
    private String keyFlag;
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
     * 工序ID
     */
    private Long processId;
    /**
     * 工序ID
     */
    private Long nextProcessId;
    /**
     * 项次
     */
    private Long sequence;

    /**
     * 工作序
     */
    private Long workorderSequence;

    /**
     * 转入单位
     */
    private String receivingUnits;

    /**
     * 转入单位分子
     */
    private BigDecimal receivingUnitsConversionNumerator;

    /**
     * 转入单位分母
     */
    private BigDecimal receivingUnitsConversionDenominator;

    /**
     * 转出单位
     */
    private String outUnits;

    /**
     * 转出单位分子
     */
    private BigDecimal outUnitsConversionNumerator;

    /**
     * 转出单位分母
     */
    private BigDecimal outUnitsConversionDenominator;

    /**
     * 工序工作序
     */
    private Long processSequence;


}
