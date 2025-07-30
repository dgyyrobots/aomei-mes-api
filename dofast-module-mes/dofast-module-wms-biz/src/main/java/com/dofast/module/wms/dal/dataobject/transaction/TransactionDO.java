package com.dofast.module.wms.dal.dataobject.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 库存事务 DO
 *
 * @author 芋道源码
 */
@TableName("wms_transaction")
@KeySequence("wms_transaction_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDO extends BaseDO {

    /**
     * 事务ID
     */
    @TableId
    private Long id;
    /**
     * 事务类型
     */
    private String transactionType;
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
     * 入库批次号
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
     * 供应商ID
     */
    private Long vendorId;
    /**
     * 供应商编号
     */
    private String vendorCode;
    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 供应商简称
     */
    private String vendorNick;
    /**
     * 单据类型
     */
    private String sourceDocType;
    /**
     * 单据ID
     */
    private Long sourceDocId;
    /**
     * 单据编号
     */
    private String sourceDocCode;
    /**
     * 单据行ID
     */
    private Long sourceDocLineId;
    /**
     * 库存记录ID
     */
    private Long materialStockId;
    /**
     * 库存方向
     */
    private Integer transactionFlag;
    /**
     * 事务数量
     */
    private BigDecimal transactionQuantity;
    /**
     * 事务日期
     */
    private LocalDateTime transactionDate;
    /**
     * 关联的事务ID
     */
    private Long relatedTransactionId;
    /**
     * ERP账期
     */
    private LocalDateTime erpDate;
    /**
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * 生产工单编号
     */
    private String workorderCode;
    /**
     * 接收日期
     */
    private LocalDateTime recptDate;
    /**
     * 库存有效期
     */
    private LocalDateTime expireDate;
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
     * 是否检查库存量
     * 如果设置为True则库存不允许为负
     */
    private boolean storageCheckFlag;

    private Long originId;

    private String confirmStatus;

    private String parentBatchCode;
}
