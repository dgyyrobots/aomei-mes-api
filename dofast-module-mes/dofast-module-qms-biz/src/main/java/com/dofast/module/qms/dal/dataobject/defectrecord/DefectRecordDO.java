package com.dofast.module.qms.dal.dataobject.defectrecord;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 检验单缺陷记录 DO
 *
 * @author 芋道源码
 */
@TableName("qms_defect_record")
@KeySequence("qms_defect_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefectRecordDO extends BaseDO {

    /**
     * 缺陷ID
     */
    @TableId
    private Long id;
    /**
     * 检验单类型
     */
    private String qcType;
    /**
     * 检验单ID
     */
    private Long qcId;
    /**
     * 检验单行ID
     */
    private Long lineId;
    /**
     * 缺陷描述
     */
    private String defectName;
    /**
     * 缺陷等级
     */
    private String defectLevel;
    /**
     * 缺陷数量
     */
    private BigDecimal defectQuantity;
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
