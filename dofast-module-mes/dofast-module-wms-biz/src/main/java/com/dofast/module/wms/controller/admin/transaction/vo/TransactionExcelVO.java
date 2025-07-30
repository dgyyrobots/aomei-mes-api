package com.dofast.module.wms.controller.admin.transaction.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 库存事务 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class TransactionExcelVO {
    /**
     * 是否检查库存量
     * 如果设置为True则库存不允许为负
     */
    private boolean storageCheckFlag;
    @ExcelProperty("事务ID")
    private Long id;

    @ExcelProperty("事务类型")
    private String transactionType;

    @ExcelProperty("产品物料ID")
    private Long itemId;

    @ExcelProperty("产品物料编码")
    private String itemCode;

    @ExcelProperty("产品物料名称")
    private String itemName;

    @ExcelProperty("规格型号")
    private String specification;

    @ExcelProperty("单位")
    private String unitOfMeasure;

    @ExcelProperty("入库批次号")
    private String batchCode;

    @ExcelProperty("仓库ID")
    private Long warehouseId;

    @ExcelProperty("仓库编码")
    private String warehouseCode;

    @ExcelProperty("仓库名称")
    private String warehouseName;

    @ExcelProperty("库区ID")
    private Long locationId;

    @ExcelProperty("库区编码")
    private String locationCode;

    @ExcelProperty("库区名称")
    private String locationName;

    @ExcelProperty("库位ID")
    private Long areaId;

    @ExcelProperty("库位编码")
    private String areaCode;

    @ExcelProperty("库位名称")
    private String areaName;

    @ExcelProperty("供应商ID")
    private Long vendorId;

    @ExcelProperty("供应商编号")
    private String vendorCode;

    @ExcelProperty("供应商名称")
    private String vendorName;

    @ExcelProperty("供应商简称")
    private String vendorNick;

    @ExcelProperty("单据类型")
    private String sourceDocType;

    @ExcelProperty("单据ID")
    private Long sourceDocId;

    @ExcelProperty("单据编号")
    private String sourceDocCode;

    @ExcelProperty("单据行ID")
    private Long sourceDocLineId;

    @ExcelProperty("库存记录ID")
    private Long materialStockId;

    @ExcelProperty("库存方向")
    private Integer transactionFlag;

    @ExcelProperty("事务数量")
    private BigDecimal transactionQuantity;

    @ExcelProperty("事务日期")
    private LocalDateTime transactionDate;

    @ExcelProperty("关联的事务ID")
    private Long relatedTransactionId;

    @ExcelProperty("ERP账期")
    private LocalDateTime erpDate;

    @ExcelProperty("生产工单ID")
    private Long workorderId;

    @ExcelProperty("生产工单编号")
    private String workorderCode;

    @ExcelProperty("接收日期")
    private LocalDateTime recptDate;

    @ExcelProperty("库存有效期")
    private LocalDateTime expireDate;

    @ExcelProperty("预留字段1")
    private String attr1;

    @ExcelProperty("预留字段2")
    private String attr2;

    @ExcelProperty("预留字段3")
    private Integer attr3;

    @ExcelProperty("预留字段4")
    private Integer attr4;

    @ExcelProperty("入库母批次号")
    private String parentBatchCode;

    @ExcelProperty("仓库确认状态")
    private String confirmStatus;

    @ExcelProperty("来源单据ID")
    private Long originId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
