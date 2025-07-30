package com.dofast.module.iot.dal.dataobject.devicefeedbacklog;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 设备产量日志 DO
 *
 * @author 惠智造
 */
@TableName("iot_device_feedback_log")
@KeySequence("iot_device_feedback_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceFeedbackLogDO extends BaseDO {

    /**
     * 设备产量日志ID
     */
    @TableId
    private Long id;
    /**
     * 设备ID
     */
    private Long deviceId;
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备编码
     */
    private String deviceCode;
    /**
     * 设备产量
     */
    private BigDecimal quantity;
    /**
     * 备注
     */
    private String remark;

    /**
     * 报工状态
     */
    private String enableStatus;

}
