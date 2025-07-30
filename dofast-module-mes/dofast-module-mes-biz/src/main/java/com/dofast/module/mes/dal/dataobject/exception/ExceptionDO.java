package com.dofast.module.mes.dal.dataobject.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

/**
 * 异常登记 DO
 *
 * @author 惠智造
 */
@TableName("mes_exception")
@KeySequence("mes_exception_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDO extends BaseDO {

    /**
     * 异常等级ID
     */
    @TableId
    private Long id;
    /**
     * 异常编号
     */
    private String exceptionCode;
    /**
     * 异常类型
     */
    private String exceptionType;
    /**
     * 异常子类型
     */
    private String subclassExceptionCode;
    /**
     * 异常子类型
     */
    private String subclassExceptionName;
    /**
     * 异常等级
     */
    private String exceptionLevelName;

    /**
     * 异常等级
     */
    private String exceptionLevelCode;

    /**
     * 异常等级颜色
     */
    private String exceptionLevelColor;
    /**
     * 异常标题
     */
    private String title;
    /**
     * 详细描述
     */
    private String description;
    /**
     * 登记时间
     */
    private LocalDateTime registerTime;
    /**
     * 登记人
     */
    private String registerUser;

    /**
     * 所属车间ID
     */
    private Long workshopId;

    /**
     * 所属车间
     */
    private String workshopName;

    /**
     * 所属车间编码
     */
    private String workshopCode;
    /**
     * 所属工序ID
     */
    private Long processId;
    /**
     * 所属工序
     */
    private String processCode;

    /**
     * 所属工序名称
     */
    private String processName;

    /**
     * 关联设备编码
     */
    private String relatedMachineryCode;

    /**
     * 关联设备名称
     */
    private String relatedMachineryName;

    /**
     * 关联ERP设备编码
     */
    private String relatedErpMachineryCode;
    /**
     * 关联物料
     */
    private String relatedMaterial;

    /**
     * 关联工单
     */
    private String relatedWorkorder;

    /**
     * 关联任务编码
     */
    private String relatedTaskCode;

    /**
     * 关联任务ID
     */
    private Long relatedTaskId;

    /**
     * 状态
     */
    private String status;
    /**
     * 预计影响
     */
    private String estimatedImpact;
    /**
     * 解决方案
     */
    private String solution;
    /**
     * 关闭时间
     */
    private LocalDateTime closeTime;
    /**
     * 附件信息
     */
    private String attachments;
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
