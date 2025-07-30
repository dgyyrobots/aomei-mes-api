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
public class RtIssueLineListVO {

    private Long id;

    private Long rtId;

    private Long materialStockId;

    private Long itemId;

    private String itemCode;

    private String itemName;

    private String specification;

    private String unitOfMeasure;

    private BigDecimal quantityRt;

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

    private String vendorCode;

    private Long sequence;

    private Long sequenceOrder;

    private String erpBatchCode;

    private String parentBatchCode;

    private String erpStatus;

    private LocalDateTime createTime;

}
