package com.dofast.module.qms.dal.dataobject.iqcline;

import io.swagger.v3.oas.annotations.media.Schema;
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
 * 来料检验单行 DO
 *
 * @author 芋道源码
 */
@TableName("qms_iqc_line")
@KeySequence("qms_iqc_line_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IqcLineDO extends BaseDO {

    /**
     * 记录ID
     */
    @TableId
    private Long id;
    /**
     * 检验单ID
     */
    private Long iqcId;
    /**
     * 检测项ID
     */
    private Long indexId;
    /**
     * 检测项编码
     */
    private String indexCode;
    /**
     * 检测项名称
     */
    private String indexName;
    /**
     * 检测项类型
     */
    private String indexType;
    /**
     * 检测工具
     */
    private String qcTool;
    /**
     * 检测要求
     */
    private String checkMethod;
    /**
     * 标准值
     */
    private BigDecimal standerVal;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 误差上限
     */
    private BigDecimal thresholdMax;
    /**
     * 误差下限
     */
    private BigDecimal thresholdMin;
    /**
     * 致命缺陷数量
     */
    private Integer crQuantity;
    /**
     * 严重缺陷数量
     */
    private Integer majQuantity;
    /**
     * 轻微缺陷数量
     */
    private Integer minQuantity;
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
     * 检验数值1
     */
    private BigDecimal inspectionValue1;

    /**
     * 检验数值2
     */
    private BigDecimal inspectionValue2;

    /**
     * 检验数值3
     */
    private BigDecimal inspectionValue3;

    /**
     * 检验数值4
     */
    private BigDecimal inspectionValue4;

    /**
     * 检验数值5
     */
    private BigDecimal inspectionValue5;

    /**
     * 检验结果
     */
    private String inspectionResult;

}
