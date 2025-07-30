package com.dofast.module.qms.controller.admin.iqc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 来料检验单 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class IqcExcelVO {

    @ExcelProperty("来料检验单ID")
    private Long id;

    @ExcelProperty("来料检验单编号")
    private String iqcCode;

    @ExcelProperty("来料检验单名称")
    private String iqcName;

    @ExcelProperty("检验模板ID")
    private Long templateId;

    @ExcelProperty("供应商ID")
    private Long vendorId;

    @ExcelProperty("供应商编码")
    private String vendorCode;

    @ExcelProperty("供应商名称")
    private String vendorName;

    @ExcelProperty("供应商简称")
    private String vendorNick;

    @ExcelProperty("供应商批次号")
    private String vendorBatch;

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

    @ExcelProperty("最低检测数")
    private BigDecimal quantityMinCheck;

    @ExcelProperty("最大不合格数")
    private BigDecimal quantityMaxUnqualified;

    @ExcelProperty("本次接收数量")
    private BigDecimal quantityRecived;

    @ExcelProperty("本次检测数量")
    private BigDecimal quantityCheck;

    @ExcelProperty("不合格数")
    private BigDecimal quantityUnqualified;

    @ExcelProperty("致命缺陷率")
    private BigDecimal crRate;

    @ExcelProperty("严重缺陷率")
    private BigDecimal majRate;

    @ExcelProperty("轻微缺陷率")
    private BigDecimal minRate;

    @ExcelProperty("致命缺陷数量")
    private BigDecimal crQuantity;

    @ExcelProperty("严重缺陷数量")
    private BigDecimal majQuantity;

    @ExcelProperty("轻微缺陷数量")
    private BigDecimal minQuantity;

    @ExcelProperty("检测结果")
    private String checkResult;

    @ExcelProperty("来料日期")
    private LocalDateTime reciveDate;

    @ExcelProperty("检测日期")
    private LocalDateTime inspectDate;

    @ExcelProperty("检测人员")
    private String inspector;

    @ExcelProperty("单据状态")
    private String status;

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

    @ExcelProperty("附件")
    private String adjuncts;

    @ExcelProperty( "采购单号")
    private String poNo;

}
