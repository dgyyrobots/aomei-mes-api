package com.dofast.module.mes.dal.dataobject.subclassexception;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 子类异常项配置 DO
 *
 * @author 惠智造
 */
@TableName("mes_subclass_exception")
@KeySequence("mes_subclass_exception_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubclassExceptionDO extends BaseDO {

    /**
     * 子类异常项ID
     */
    @TableId
    private Long id;
    /**
     * 异常类型(数据字典)
     */
    private String exceptionType;
    /**
     * 子类异常项编号
     */
    private String subclassExceptionCode;
    /**
     * 子类异常项名称
     */
    private String subclassExceptionName;
    /**
     * 备注
     */
    private String remark;

}
