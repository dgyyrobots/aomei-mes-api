package com.dofast.module.wms.controller.admin.issueline.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class IssueLineListVO {
    private Long id;

    private Long issueId;

    private Long materialStockId;

    private Long itemId;

    private String itemCode;

    private String itemName;

    private String specification;

    private String unitOfMeasure;

    private BigDecimal quantityIssued;

    private String batchCode;

    private Long warehouseId;

    private String warehouseCode;

    private String warehouseName;

    private Long locationId;

    private String locationCode;

    private String locationName;

    private Long areaId;

    private String areaCode;

    private String areaName;

    private String remark;

    private String attr1;

    private String attr2;

    private Integer attr3;

    private Integer attr4;

    private LocalDateTime createTime;

    private String status;

    private String vendorCode;

    private String feedbackStatus;

    private String feedbackCode;

    private String machineryName;

    private String machineryCode;

    private String machineryId;

    private Long barcodeNumber;

    private String enableFlag;

    private Long sequence;

    private Long sequenceOrder;

    private String erpBatchCode;

    private String erpEnable;

    private String parentBatchCode;
}
