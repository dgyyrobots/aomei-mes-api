package com.dofast.module.mes.dal.dataobject.mditemcategory;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 物料类别 DO
 *
 * @author 惠智造
 */
@TableName("mes_md_item_category")
@KeySequence("mes_md_item_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MdItemCategoryDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Integer id;
    /**
     * 物料类别名称
     */
    private String itemCategoryName;
    /**
     * 物料类别编码
     */
    private String itemCategoryCode;
    /**
     * 物料工艺编码
     */
    private String itemRouteCode;
    /**
     * 物料工艺
     */
    private String itemRoute;
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

}
