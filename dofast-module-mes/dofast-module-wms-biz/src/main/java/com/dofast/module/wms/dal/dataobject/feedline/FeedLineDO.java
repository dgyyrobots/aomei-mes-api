package com.dofast.module.wms.dal.dataobject.feedline;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 上料详情 DO
 *
 * @author 惠智造
 */
@TableName("wms_feed_line")
@KeySequence("wms_feed_line_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedLineDO extends BaseDO {

    /**
     * 行ID
     */
    @TableId
    private Long id;
    /**
     * 领料单ID
     */
    private Long issueId;
    /**
     * 库存ID
     */
    private Long materialStockId;
    /**
     * 产品物料ID
     */
    private Long itemId;
    /**
     * 产品物料编码
     */
    private String itemCode;
    /**
     * 产品物料名称
     */
    private String itemName;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 上料数量
     */
    private Double quantity;
    /**
     * 上料批次号
     */
    private String batchCode;
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
     * 工单号
     */
    private String workorderCode;
    /**
     * 任务编号
     */
    private String taskCode;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 工作站编码
     */
    private String workstationCode;
    /**
     * 工作站名称
     */
    private String workstationName;

    private String status;

    /**
     * 供应商编码
     */
    private String vendorCode;

    /**
     * 报工状态
     */
    private String feedbackStatus;

    /**
     * 报工单号
     */
    private String feedbackCode;

    /**
     * 设备名称
     */
    private String machineryName;

    /**
     * 设备编码
     */
    private String machineryCode;

    /**
     * 设备Id
     */
    private String machineryId;

    /**
     * 条码编号
     */
    private Long barcodeNumber;

    /**
     * ERP项次
     */
    private Long sequence;

    /**
     * ERP项次
     */
    private Long sequenceOrder;

    /**
     * ERP批次
     */
    private String erpBatchCode;

    /**
     * 母批次
     */
    private String parentBatchCode;
}
