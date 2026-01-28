package com.dofast.module.pro.api.FeedbackApi.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ToString(callSuper = true)
public class FeedbackDTO {
    /**
     * 记录ID
     */
    private Long id;
    /**
     * 报工类型
     */
    private String feedbackType;
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
     * 排产数量
     */
    private Double quantity;
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
     * 报工途径
     */
    private String feedbackChannel;
    /**
     * 报工时间
     */
    private LocalDateTime feedbackTime;
    /**
     * 记录人
     */
    private String recordUser;
    /**
     * 记录人名称
     */
    private String recordNick;
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
     * 报工价格
     */
    private Double reportFee;
    /**
     * 实付价格
     */
    private Double payFee;
    /**
     *批次号
     */
    private String batchCode;
    /**
     * ERP批次号
     */
    private String erpBatchCode;

    /**
     * 班组编码
     */
    private String teamCode;

    /**
     * 缺陷Id
     */
    private String defectId;

    /**
     * 班组负责人
     */
    private String principalName;

    /**
     * 班组负责人Id
     */
    private Long principalId;

    /**
     * 班组类型
     */
    private String shiftInfo;

    /**
     * 来源编码
     */
    private String originCode;

    /**
     * 设备Id
     */
    private Long machineryId;

    /**
     * 设备名称
     */
    private String machineryName;

    /**
     * 设备编码
     */
    private String machineryCode;

    /**
     * 设备编码
     */
    private String erpFeedback;

    /**
     * 任务单状态
     */
    private String taskStatus;

    /**
     * "ERP报工状态"
     */
    private String erpFeedbackStatus;

    /**
     * "ERP入库状态"
     */
    private String erpWarehousingStatus;

    /**
     * 合并需求
     */
    private String mergeStatus;

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
     * 卷数编号
     */
    private String volumesNumber;

    /**
     * 数采标识
     */
    private String iotFlag;

    /**
     * 合并标识
     */
    private String mergeFlag;

    /**
     * 余料数量
     */
    private BigDecimal quantityExcess;

}
