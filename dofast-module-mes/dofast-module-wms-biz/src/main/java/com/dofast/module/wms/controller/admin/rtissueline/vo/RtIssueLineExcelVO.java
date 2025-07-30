package com.dofast.module.wms.controller.admin.rtissueline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 生产退料单行 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class RtIssueLineExcelVO {

    @ExcelProperty("行ID")
    private Long id;

    @ExcelProperty("退料单ID")
    private Long rtId;

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

    @ExcelProperty("退料数量")
    private BigDecimal quantityRt;

    @ExcelProperty("领料批次号")
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

    @ExcelProperty("预留字段1")
    private String attr1;

    @ExcelProperty("预留字段2")
    private String attr2;

    @ExcelProperty("预留字段3")
    private Integer attr3;

    @ExcelProperty("预留字段4")
    private Integer attr4;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("供应商编码")
    private String vendorCode;

    @ExcelProperty("ERP项次")
    private Long sequence;

    @ExcelProperty("ERP项序")
    private Long sequenceOrder;

    @ExcelProperty("ERP批次")
    private String erpBatchCode;

    @ExcelProperty("母批次")
    private String parentBatchCode;

    @ExcelProperty("ERP状态")
    private String erpStatus;
}
