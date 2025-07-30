package com.dofast.module.purchase.dal.dataobject.retreatOrder;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * ERP仓退单 DO
 *
 * @author 惠智造
 */
@TableName("retreat_order")
@KeySequence("retreat_order_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetreatOrderDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 仓退单编号
     */
    private String retreatCode;
    /**
     * 仓退单名称
     */
    private String retreatName;

    /**
     * 供应商名称
     */
    private String vendorName;

    /**
     * 供应商编号
     */
    private String vendorCode;

    /**
     * 申请人员
     */
    private String retreatUser;

    /**
     * 申请人员
     */
    private String retreatNick;

    /**
     * 仓退原因
     */
    private String retreatType;

    /**
     * 状态
     */
    private String status;

}
