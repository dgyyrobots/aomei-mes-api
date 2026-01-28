package com.dofast.module.qms.dal.dataobject.defect;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 常见缺陷 DO
 *
 * @author 芋道源码
 */
@TableName("qms_defect")
@KeySequence("qms_defect_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DefectDO extends BaseDO {

    /**
     * 缺陷ID
     */
    @TableId
    private Long id;
    /**
     * 缺陷编码
     */
    private String defectCode;
    /**
     * 缺陷描述
     */
    private String defectName;
    /**
     * 检测项类型
     */
    private String indexType;
    /**
     * 缺陷等级
     */
    private String defectLevel;
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
     * 工序编码
     */
    private String processCode;

    /**
     * 工序名称
     */
    private String processName;

}
