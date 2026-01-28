package com.dofast.module.pro.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

import org.apache.poi.hpsf.Decimal;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 生产报工记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackBaseVO {

    @Schema(description = "报工类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "报工类型不能为空")
    private String feedbackType;

    @Schema(description = "报工单编号")
    private String feedbackCode;

    @Schema(description = "工作站ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30439")
    @NotNull(message = "工作站ID不能为空")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "张三")
    private String workstationName;

    @Schema(description = "生产工单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5531")
    @NotNull(message = "生产工单ID不能为空")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "生产工单名称", example = "芋艿")
    private String workorderName;

    @Schema(description = "工序ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16712")
    @NotNull(message = "工序ID不能为空")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "李四")
    private String processName;

    @Schema(description = "生产任务ID", example = "1831")
    private Long taskId;

    @Schema(description = "生产任务编号")
    private String taskCode;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15790")
    @NotNull(message = "产品物料ID不能为空")
    private Long itemId;

    @Schema(description = "产品物料编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "产品物料编码不能为空")
    private String itemCode;

    @Schema(description = "产品物料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "产品物料名称不能为空")
    private String itemName;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "排产数量")
    private Double quantity;

    @Schema(description = "本次报工数量")
    private Double quantityFeedback;

    @Schema(description = "合格品数量")
    private Double quantityQualified;

    @Schema(description = "不良品数量")
    private Double quantityUnquanlified;

    @Schema(description = "报工用户名", example = "芋艿")
    private String userName;

    @Schema(description = "昵称", example = "王五")
    private String nickName;

    @Schema(description = "报工途径")
    private String feedbackChannel;

    @Schema(description = "报工时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime feedbackTime;

    @Schema(description = "记录人")
    private String recordUser;

    @Schema(description = "记录人名称")
    private String recordNick;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

    @Schema(description = "报工价格", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double reportFee;

    @Schema(description = "实付价格", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Double payFee;

    @Schema(description = "批次号")
    private String batchCode;

    @Schema(description = "ERP批次号")
    private String erpBatchCode;

    @Schema(description = "班组编码")
    private String teamCode;

    @Schema(description = "缺陷Id")
    private String defectId;

    @Schema(description = "班组负责人")
    private String principalName;

    @Schema(description = "班组负责人Id")
    private Long principalId;

    @Schema(description = "班组类型")
    private String shiftInfo;

    @Schema(description = "来源编码")
    private String originCode;

    @Schema(description = "设备Id")
    private Long machineryId;

    @Schema(description = "设备名称")
    private String machineryName;

    @Schema(description = "设备编码")
    private String machineryCode;

    @Schema(description = "ERP报工单")
    private String erpFeedback;

    @Schema(description = "任务单状态")
    private String taskStatus;

    @Schema(description = "ERP报工状态")
    private String erpFeedbackStatus;

    @Schema(description = "ERP入库状态")
    private String erpWarehousingStatus;

    @Schema(description = "合并需求")
    private String mergeStatus;

    @Schema(description = "转换数量")
    private BigDecimal conversionQuantity;

    @Schema(description = "转换单位")
    private String conversionUnit;

    @Schema(description = "转换不合格数量")
    private BigDecimal conversionQuantityUnquanlified;

    @Schema(description = "卷数编号")
    private String volumesNumber;

    @Schema(description = "数采标识")
    private String iotFlag;

    @Schema(description = "合并标识")
    private String mergeFlag;

    @Schema(description = "余料数量")
    private Double quantityExcess;

    @Schema(description = "工序工作序")
    private Long processSequence;

}
