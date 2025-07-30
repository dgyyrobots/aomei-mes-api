package com.dofast.module.mes.dal.dataobject.registration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 计时登记 DO
 *
 * @author 惠智造
 */
@TableName("mes_registration")
@KeySequence("mes_registration_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDO extends BaseDO {

    /**
     * 计时登记ID
     */
    @TableId
    private Long id;
    /**
     * 登记编码
     */
    private String registrationCode;
    /**
     * 登记名称
     */
    private String registrationName;
    /**
     * 登记类型(数据字典)
     */
    private String registrationType;
    /**
     * 起始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 所属车间ID
     */
    private Long workshopId;
    /**
     * 所属车间名称
     */
    private String workshopName;
    /**
     * 所属车间编码
     */
    private String workshopCode;
    /**
     * 关联设备设备编码
     */
    private String relatedMachineryCode;
    /**
     * 关联设备名称
     */
    private String relatedMachineryName;
    /**
     * 关联ERP设备编码
     */
    private String relatedErpMachineryCode;
    /**
     * 关联工单
     */
    private String relatedWorkorder;

    /**
     * 关联任务编码
     */
    private String relatedTaskCode;

    /**
     * 关联任务ID
     */
    private Long relatedTaskId;

    /**
     * 关联物料编码
     */
    private String relatedMaterialCode;
    /**
     * 关联物料名称
     */
    private String relatedMaterialName;
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
     * 历经时间(分)
     */
    private Integer durationTime;

    /**
     * 计时状态
     */
    private String status;
}
