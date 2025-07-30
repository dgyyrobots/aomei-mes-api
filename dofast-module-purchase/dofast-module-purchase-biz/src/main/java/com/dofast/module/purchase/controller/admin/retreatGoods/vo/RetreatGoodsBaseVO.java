package com.dofast.module.purchase.controller.admin.retreatGoods.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * ERP仓退单单身 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class RetreatGoodsBaseVO {

    @Schema(description = "仓退单ID", example = "20534")
    private Integer retreatId;

    @Schema(description = "商品编号")
    private String goodsNumber;

    @Schema(description = "商品名称", example = "赵六")
    private String goodsName;

    @Schema(description = "商品规格")
    private String goodsSpecs;

    @Schema(description = "商品单位")
    private String company;

    @Schema(description = "采购单价")
    private Double monovalent;

    @Schema(description = "采购数量")
    private BigDecimal quantity;

    @Schema(description = "入库数量")
    private BigDecimal receiveNum;

    @Schema(description = "入库单位")
    private String unitOfMeasure;

    @Schema(description = "批次")
    private String batchCode;

    @Schema(description = "项次")
    private String consequence;

    @Schema(description = "状态（0-未收货 , 1-未打印，2-已打印，3-已入库）", example = "1")
    private Integer status;

    @Schema(description = "仓库ID", example = "2737")
    private Long warehouseId;

    @Schema(description = "仓库编码")
    private String warehouseCode;

    @Schema(description = "仓库名称", example = "李四")
    private String warehouseName;

    @Schema(description = "库区ID", example = "12012")
    private Long locationId;

    @Schema(description = "库区编码")
    private String locationCode;

    @Schema(description = "库区名称", example = "赵六")
    private String locationName;

    @Schema(description = "库位ID", example = "23867")
    private Long areaId;

    @Schema(description = "库位编码")
    private String areaCode;

    @Schema(description = "库位名称", example = "赵六")
    private String areaName;

    @Schema(description = "ERP采购单号")
    private String poNo;

    @Schema(description = "ERP采购批次")
    private Integer purchaseBatch;

    @Schema(description = "ERP采购批序")
    private Integer purchaseConsequence;

    @Schema(description = "ERP采购分批序")
    private Integer purchaseBatchConsequence;

    @Schema(description = "ERP采购单号")
    private String erpReceiveCode;

    @Schema(description = "ERP收货项次")
    private Integer receiveSeq;

    @Schema(description = "ERP仓退理由码")
    private String reasonCode;

}
