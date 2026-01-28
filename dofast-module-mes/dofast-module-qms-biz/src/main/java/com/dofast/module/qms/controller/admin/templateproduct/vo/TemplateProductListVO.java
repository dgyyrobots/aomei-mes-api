package com.dofast.module.qms.controller.admin.templateproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 检测模板-产品 Excel VO
 *
 * @author 芋道源码
 */
@Data
public class TemplateProductListVO{

    private Long id;

    private Long templateId;

    private Long itemId;

    private String itemCode;

    private String itemName;

    private String specification;

    private String unitOfMeasure;

    private Integer quantityCheck;

    private Integer quantityUnqualified;

    private BigDecimal crRate;

    private BigDecimal majRate;

    private BigDecimal minRate;

    private String remark;

    private String attr1;

    private String attr2;

    private Integer attr3;

    private Integer attr4;

    private String processCode;

    private String processName;

    private LocalDateTime createTime;

}
