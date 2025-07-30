package com.dofast.module.purchase.controller.admin.retreatGoods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * ERP仓退单单身 Excel VO
 *
 * @author 惠智造
 */
@Data
public class RetreatGoodsExcelVO {

    @ExcelProperty("id")
    private Integer id;

    @ExcelProperty("仓退单ID")
    private Integer retreatId;

    @ExcelProperty("商品编号")
    private String goodsNumber;

    @ExcelProperty("商品名称")
    private String goodsName;

    @ExcelProperty("商品规格")
    private String goodsSpecs;

    @ExcelProperty("商品单位")
    private String company;

    @ExcelProperty("采购单价")
    private Double monovalent;

    @ExcelProperty("采购数量")
    private BigDecimal quantity;

    @ExcelProperty("入库数量")
    private BigDecimal receiveNum;

    @ExcelProperty("入库单位")
    private String unitOfMeasure;

    @ExcelProperty("批次")
    private String batchCode;

    @ExcelProperty("项次")
    private String consequence;

    @ExcelProperty("状态（0-未收货 , 1-未打印，2-已打印，3-已入库）")
    private Integer status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

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

    @ExcelProperty("ERP采购单号")
    private String poNo;

    @ExcelProperty("ERP采购批次")
    private Integer purchaseBatch;

    @ExcelProperty("ERP采购批序")
    private Integer purchaseConsequence;

    @ExcelProperty("ERP采购分批序")
    private Integer purchaseBatchConsequence;

    @ExcelProperty("ERP采购单号")
    private String erpReceiveCode;

    @ExcelProperty("ERP收货项次")
    private Integer receiveSeq;

    @ExcelProperty("已收数量")
    private BigDecimal receivedNum;

    @ExcelProperty("ERP仓退理由码")
    private String reasonCode;

}
