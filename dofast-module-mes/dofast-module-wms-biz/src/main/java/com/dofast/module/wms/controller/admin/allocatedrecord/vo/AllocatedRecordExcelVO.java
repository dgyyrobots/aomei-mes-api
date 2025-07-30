package com.dofast.module.wms.controller.admin.allocatedrecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 调拨单身记录 Excel VO
 *
 * @author 惠智造
 */
@Data
public class AllocatedRecordExcelVO {

    @ExcelProperty("行ID")
    private Long id;

    @ExcelProperty("调拨单ID")
    private Long allocatedId;

    @ExcelProperty("库存ID")
    private Long materialStockId;

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

    @ExcelProperty("调拨数量")
    private Double quantityAllocated;

    @ExcelProperty("调拨批次号")
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

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("调拨标识")
    private String allocatedFlag;

    @ExcelProperty("供应商编码")
    private String vendorCode;

    @ExcelProperty("母批次")
    private String parentBatchCode;

    @ExcelProperty("ERP状态")
    private String erpStatus;

}
