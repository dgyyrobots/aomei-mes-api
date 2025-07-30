package com.dofast.module.wms.controller.admin.materialstock.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 库存记录 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class MaterialStockExcelVO {

    @ExcelProperty("事务ID")
    private Long id;

    @ExcelProperty("物料类型ID")
    private Long itemTypeId;

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

    @ExcelProperty("在库数量")
    private BigDecimal quantityOnhand;

    @ExcelProperty("生产工单ID")
    private Long workorderId;

    @ExcelProperty("生产工单编号")
    private String workorderCode;

    @ExcelProperty("入库时间")
    private LocalDateTime recptDate;

    @ExcelProperty("库存有效期")
    private LocalDateTime expireDate;

    /*@ExcelProperty("预留字段1")
    private String attr1;

    @ExcelProperty("预留字段2")
    private String attr2;

    @ExcelProperty("预留字段3")
    private Integer attr3;

    @ExcelProperty("预留字段4")
    private Integer attr4;*/

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("入库状态(Y/N)")
    private String recptStatus;

    @ExcelProperty("ERP批次")
    private String erpBatchCode;

    /*@ExcelProperty("来源Id")
    private Long originId;*/

    @ExcelProperty("母批次号")
    private String parentBatchCode;

    /*@ExcelProperty("仓库确认状态")
    private String confirmStatus;*/

}
