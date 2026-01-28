package com.dofast.module.pro.controller.admin.workorder.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WorkorderListVO {
    private Long id;

    private String workorderCode;

    private String workorderName;

    private String orderSource;

    private String sourceCode;

    private Long productId;

    private String productCode;

    private String productName;

    private String productSpc;

    private String unitOfMeasure;

    private Double quantity;

    private Double quantityProduced;

    private Double quantityChanged;

    private Double quantityScheduled;

    private Long clientId;

    private String clientCode;

    private String clientName;

    private String batchCode;

    private LocalDateTime requestDate;

    private Long parentId;

    private String ancestors;

    private String status;

    private String remark;

    private String attr1;

    private String attr2;

    private Integer attr3;

    private Integer attr4;

    private String adjuncts;

    private Boolean isOut;

    private String closeFlag;

    private LocalDateTime[] createTime;

    private LocalDateTime[] updateTime;

}
