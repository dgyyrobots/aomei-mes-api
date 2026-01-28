package com.dofast.module.wms.dal.dataobject.allocatedheader;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 调拨单头 DO
 *
 * @author 惠智造
 */
@TableName("wms_allocated_header")
@KeySequence("wms_allocated_header_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllocatedHeaderDO extends BaseDO {

    /**
     * 调拨单ID
     */
    @TableId
    private Long id;
    /**
     * 调拨单编号
     */
    private String allocatedCode;
    /**
     * 调拨单名称
     */
    private String allocatedName;
    /**
     * 工作站ID
     */
    private Long workstationId;
    /**
     * 工作站编号
     */
    private String workstationCode;
    /**
     * 工作站名称
     */
    private String workstationName;
    /**
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * 生产工单编码
     */
    private String workorderCode;
    /**
     * 生产任务ID
     */
    private Long taskId;
    /**
     * 生产任务编码
     */
    private String taskCode;
    /**
     * 客户ID
     */
    private Long clientId;
    /**
     * 客户编码
     */
    private String clientCode;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 客户简称
     */
    private String clientNick;
    /**
     * 仓库ID
     */
    private Long warehouseId;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 库区ID
     */
    private Long locationId;
    /**
     * 库区编码
     */
    private String locationCode;
    /**
     * 库区名称
     */
    private String locationName;
    /**
     * 库位ID
     */
    private Long areaId;
    /**
     * 库位编码
     */
    private String areaCode;
    /**
     * 库位名称
     */
    private String areaName;
    /**
     * 调拨日期
     */
    private LocalDateTime allocatedDate;
    /**
     * 单据状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;

    private  String taskName;

    private String bindWorkorder;

    private String completer;

}
