package com.dofast.module.pro.dal.dataobject.feedbackwarehousinglog;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.dofast.framework.mybatis.core.dataobject.BaseDO;

import javax.validation.constraints.NotNull;

/**
 * 报工入库日志 DO
 *
 * @author 惠智造
 */
@TableName("pro_feedback_warehousing_log")
@KeySequence("pro_feedback_warehousing_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackWarehousingLogDO extends BaseDO {

    /**
     * 记录ID
     */
    @TableId
    private Long id;
    /**
     * 报工单编号
     */
    private String feedbackCode;
    /**
     * 工作站ID
     */
    private Long workstationId;
    /**
     * 工作站编号
     */
    private String workstationCode;
    /**
     * 工作站名称
     */
    private String workstationName;
    /**
     * 生产工单ID
     */
    private Long workorderId;
    /**
     * 生产工单编号
     */
    private String workorderCode;
    /**
     * 生产工单名称
     */
    private String workorderName;
    /**
     * 工序ID
     */
    private Long processId;
    /**
     * 工序编码
     */
    private String processCode;
    /**
     * 工序名称
     */
    private String processName;
    /**
     * 生产任务ID
     */
    private Long taskId;
    /**
     * 生产任务编号
     */
    private String taskCode;
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
     * 单位
     */
    private String unitOfMeasure;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 本次报工数量
     */
    private Double quantityFeedback;
    /**
     * 合格品数量
     */
    private Double quantityQualified;
    /**
     * 不良品数量
     */
    private Double quantityUnquanlified;
    /**
     * 报工用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * ERP批次号
     */
    private String erpBatchCode;
    /**
     * 生产设备名称
     */
    private String machineryName;
    /**
     * 生产设备编码
     */
    private String machineryCode;
    /**
     * 生产设备ID
     */
    private Long machineryId;
    /**
     * ERP报工单(用于撤销)
     */
    private String erpFeedback;
    /**
     * ERP报工标识
     */
    private String erpFeedbackStatus;
    /**
     * ERP入库标识
     */
    private String erpWarehousingStatus;
    /**
     * 转换数量
     */
    private BigDecimal conversionQuantity;
    /**
     * 转换单位
     */
    private String conversionUnit;
    /**
     * 转换不合格数量
     */
    private BigDecimal conversionQuantityUnquanlified;
    /**
     * 状态
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


}
