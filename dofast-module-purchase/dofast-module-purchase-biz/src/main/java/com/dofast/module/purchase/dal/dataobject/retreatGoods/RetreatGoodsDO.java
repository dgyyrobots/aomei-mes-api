package com.dofast.module.purchase.dal.dataobject.retreatGoods;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * ERP仓退单单身 DO
 *
 * @author 惠智造
 */
@TableName("retreat_goods")
@KeySequence("retreat_goods_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetreatGoodsDO extends BaseDO {

    /**
     * id
     */
    @TableId
    private Integer id;
    /**
     * 仓退单ID
     */
    private Integer retreatId;
    /**
     * 商品编号
     */
    private String goodsNumber;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品规格
     */
    private String goodsSpecs;
    /**
     * 商品单位
     */
    private String company;
    /**
     * 采购单价
     */
    private Double monovalent;
    /**
     * 采购数量
     */
    private BigDecimal quantity;
    /**
     * 入库数量
     */
    private BigDecimal receiveNum;
    /**
     * 入库单位
     */
    private String unitOfMeasure;
    /**
     * 批次
     */
    private String batchCode;
    /**
     * 项次
     */
    private String consequence;
    /**
     * 状态（0-未收货 , 1-未打印，2-已打印，3-已入库）
     */
    private Integer status;
    /**
     * 仓库ID
     */
    private Long warehouseId;
    /**
     * 仓库编码
     */
    private String warehouseCode;
    /**
     * 仓库名称
     */
    private String warehouseName;
    /**
     * 库区ID
     */
    private Long locationId;
    /**
     * 库区编码
     */
    private String locationCode;
    /**
     * 库区名称
     */
    private String locationName;
    /**
     * 库位ID
     */
    private Long areaId;
    /**
     * 库位编码
     */
    private String areaCode;
    /**
     * 库位名称
     */
    private String areaName;

    /**
     * ERP采购单号
     */
    private String poNo;

    /**
     * ERP采购批次
     */
    private Integer purchaseBatch;

    /**
     * ERP采购批序
     */
    private Integer purchaseConsequence;

    /**
     * ERP采购分批序
     */
    private Integer purchaseBatchConsequence;

    /**
     * ERP收货单号
     */
    private String erpReceiveCode;

    /**
     * ERP收货项次
     */
    private Integer receiveSeq;

    /**
     * ERP理由码
     */
    private String reasonCode;

}
