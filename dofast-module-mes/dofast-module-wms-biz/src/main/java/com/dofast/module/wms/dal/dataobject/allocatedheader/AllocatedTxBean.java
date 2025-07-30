package com.dofast.module.wms.dal.dataobject.allocatedheader;

import com.dofast.framework.mybatis.core.dataobject.BaseDO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AllocatedTxBean extends BaseDO {

    private Long materialStockId;

    /** 产品物料ID */
    private Long itemId;

    /** 产品物料编码 */
    private String itemCode;

    /** 产品物料名称 */
    private String itemName;

    /** 规格型号 */
    private String specification;

    /** 单位 */
    private String unitOfMeasure;

    /** 批次号 */
    private String batchCode;

    /** 供应商ID */
    private Long vendorId;

    /** 供应商编号 */
    private String vendorCode;

    /** 供应商名称 */
    private String vendorName;

    /** 供应商简称 */
    private String vendorNick;

    /** 仓库ID */
    private Long warehouseId;

    /** 仓库编码 */
    private String warehouseCode;

    /** 仓库名称 */
    private String warehouseName;

    /** 调拨仓库ID */
    private Long inWarehouseId;

    /** 调拨仓库编码 */
    private String inWarehouseCode;

    /** 调拨仓库名称 */
    private String inWarehouseName;

    /** 库区ID */
    private Long locationId;

    /** 库区编码 */
    private String locationCode;

    /** 库区名称 */
    private String locationName;

    /** 调拨库区ID */
    private Long inLocationId;

    /** 调拨库区编码 */
    private String inLocationCode;

    /** 调拨库区名称 */
    private String inLocationName;

    /** 库位ID */
    private Long areaId;

    /** 库位编码 */
    private String areaCode;

    /** 库位名称 */
    private String areaName;

    /** 调拨库位ID */
    private Long inAreaId;

    /** 调拨库位编码 */
    private String inAreaCode;

    /** 调拨库位名称 */
    private String inAreaName;

    /** 单据类型 */
    private String sourceDocType;

    /** 单据ID */
    private Long sourceDocId;

    /** 单据编号 */
    private String sourceDocCode;

    /** 单据行ID */
    private Long sourceDocLineId;

    /** 事务数量 */
    private BigDecimal transactionQuantity;

    /**
     * 生产工单ID
     */
    private Long workorderId;

    /**
     * 生产工单编号
     */
    private String workorderCode;

    /** 母批次号 */
    private String parentBatchCode;
}
