package com.dofast.module.qms.dal.dataobject.oqc;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 出货检验单 DO
 *
 * @author 芋道源码
 */
@TableName("qms_oqc")
@KeySequence("qms_oqc_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OqcDO extends BaseDO {

    /**
     * 出货检验单ID
     */
    @TableId
    private Long id;

    /**
     * 订单编号
     */
    private String sourceCode;
    /**
     * 出货检验单编号
     */
    private String oqcCode;
    /**
     * 出货检验单名称
     */
    private String oqcName;
    /**
     * 检验模板ID
     */
    private Long templateId;
    /**
     * 客户ID
     */
    private Long clientId;
    /**
     * 客户编码
     */
    private String clientCode;
    /**
     * 客户名称
     */
    private String clientName;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * 产品物料ID
     */
    private Long itemId;
    /**
     * 产品物料编码
     */
    private String itemCode;
    /**
     * 产品物料名称
     */
    private String itemName;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 最低检测数
     */
    private BigDecimal quantityMinCheck;
    /**
     * 最大不合格数
     */
    private BigDecimal quantityMaxUnqualified;
    /**
     * 发货数量
     */
    private BigDecimal quantityOut;
    /**
     * 本次检测数量
     */
    private BigDecimal quantityCheck;
    /**
     * 不合格数
     */
    private BigDecimal quantityUnqualified;
    /**
     * 合格数量
     */
    private BigDecimal quantityQuanlified;
    /**
     * 致命缺陷率
     */
    private BigDecimal crRate;
    /**
     * 严重缺陷率
     */
    private BigDecimal majRate;
    /**
     * 轻微缺陷率
     */
    private BigDecimal minRate;
    /**
     * 致命缺陷数量
     */
    private BigDecimal crQuantity;
    /**
     * 严重缺陷数量
     */
    private BigDecimal majQuantity;
    /**
     * 轻微缺陷数量
     */
    private BigDecimal minQuantity;
    /**
     * 检测结果
     */
    private String checkResult;
    /**
     * 出货日期
     */
    private LocalDateTime outDate;
    /**
     * 检测日期
     */
    private LocalDateTime inspectDate;
    /**
     * 检测人员
     */
    private String inspector;
    /**
     * 单据状态
     */
    private String status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 预留字段1
     */
    private String attr1;
    /**
     * 预留字段2
     */
    private String attr2;
    /**
     * 预留字段3
     */
    private Integer attr3;
    /**
     * 预留字段4
     */
    private Integer attr4;

    /**
     * 附件
     */
    private String adjuncts;
    
}
