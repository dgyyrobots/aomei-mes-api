package com.dofast.module.cmms.dal.dataobject.dvmachinery;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备台账 DO
 *
 * @author 芋道源码
 */
@TableName("cmms_dv_machinery")
@KeySequence("cmms_dv_machinery_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DvMachineryDO extends BaseDO {

    /**
     * 设备类型ID
     */
    @TableId
    private Long id;
    /**
     * 设备类型编码
     */
    private String machineryCode;
    /**
     * 设备类型名称
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
     * 设备类型编码
     */
    private String machineryTypeCode;
    /**
     * 设备类型名称
     */
    private String machineryTypeName;
    /**
     * 所属车间ID
     */
    private Long workshopId;
    /**
     * 所属车间编码
     */
    private String workshopCode;
    /**
     * 所属车间名称
     */
    private String workshopName;
    /**
     * 设备状态
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
     * ERP设备编码
     */
    private String erpMachineryCode;

    /**
     * 产品ID
     */
    private Long productId;

}
