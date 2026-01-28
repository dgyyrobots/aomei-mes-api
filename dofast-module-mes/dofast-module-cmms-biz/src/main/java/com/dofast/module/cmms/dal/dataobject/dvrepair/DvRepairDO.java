package com.dofast.module.cmms.dal.dataobject.dvrepair;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备维修单 DO
 *
 * @author 芋道源码
 */
@TableName("cmms_dv_repair")
@KeySequence("cmms_dv_repair_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvRepairDO extends BaseDO {

    /**
     * 维修单ID
     */
    @TableId
    private Long id;
    /**
     * 维修单编号
     */
    private String repairCode;
    /**
     * 维修单名称
     */
    private String repairName;
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
     * 品牌
     */
    private String machineryBrand;
    /**
     * 规格型号
     */
    private String machinerySpec;
    /**
     * 设备类型ID
     */
    private Long machineryTypeId;
    /**
     * 报修日期
     */
    private LocalDateTime requireDate;
    /**
     * 维修完成日期
     */
    private LocalDateTime finishDate;
    /**
     * 验收日期
     */
    private LocalDateTime confirmDate;
    /**
     * 维修结果
     */
    private String repairResult;
    /**
     * 维修人员
     */
    private String acceptedBy;
    /**
     * 验收人员
     */
    private String confirmBy;
    /**
     * 单据状态
     */
    private String status;
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
     * 维修类型
     */
    private String repairType;

}
