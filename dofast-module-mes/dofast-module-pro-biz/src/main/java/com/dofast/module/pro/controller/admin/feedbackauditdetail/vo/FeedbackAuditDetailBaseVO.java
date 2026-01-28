package com.dofast.module.pro.controller.admin.feedbackauditdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 报工审批明细 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackAuditDetailBaseVO {

    @Schema(description = "审批主表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9100")
    @NotNull(message = "审批主表ID不能为空")
    private Long auditId;

    @Schema(description = "审批汇总表ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9100")
    @NotNull(message = "审批汇总表ID不能为空")
    private Long auditItemId;

    @Schema(description = "报工记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "7315")
    @NotNull(message = "报工记录ID不能为空")
    private Long feedbackId;

    @Schema(description = "报工类型", example = "1")
    private String feedbackType;

    @Schema(description = "报工单编号")
    private String feedbackCode;

    @Schema(description = "工作站ID", example = "22917")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "王五")
    private String workstationName;

    @Schema(description = "生产工单ID", example = "3546")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "生产工单名称", example = "王五")
    private String workorderName;

    @Schema(description = "工序ID", example = "12231")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "赵六")
    private String processName;

    @Schema(description = "生产任务ID", example = "2388")
    private Long taskId;

    @Schema(description = "生产任务编号")
    private String taskCode;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3281")
    @NotNull(message = "产品物料ID不能为空")
    private Long itemId;

    @Schema(description = "产品物料编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "产品物料编码不能为空")
    private String itemCode;

    @Schema(description = "产品物料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "产品物料名称不能为空")
    private String itemName;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "排产数量")
    private BigDecimal quantity;

    @Schema(description = "本次报工数量")
    private BigDecimal quantityFeedback;

    @Schema(description = "合格品数量")
    private BigDecimal quantityQualified;

    @Schema(description = "不良品数量")
    private BigDecimal quantityUnquanlified;

    @Schema(description = "工艺损耗数量")
    private BigDecimal quantityExcess;

    @Schema(description = "报工用户名", example = "王五")
    private String userName;

    @Schema(description = "昵称", example = "张三")
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

    @Schema(description = "批次号")
    private String batchCode;

    @Schema(description = "ERP批次号")
    private String erpBatchCode;

    @Schema(description = "班组编号")
    private String teamCode;

    @Schema(description = "班组负责人", example = "王五")
    private String principalName;

    @Schema(description = "班组负责人Id", example = "4151")
    private Long principalId;

    @Schema(description = "班组信息(0白班1晚班)")
    private String shiftInfo;

    @Schema(description = "来源编码")
    private String originCode;

    @Schema(description = "生产设备名称", example = "王五")
    private String machineryName;

    @Schema(description = "生产设备编码")
    private String machineryCode;

    @Schema(description = "生产设备ID", example = "21637")
    private Long machineryId;

    @Schema(description = "ERP报工标识", example = "2")
    private String erpFeedbackStatus;

    @Schema(description = "ERP入库标识", example = "1")
    private String erpWarehousingStatus;

    @Schema(description = "合并需求", example = "1")
    private String mergeStatus;

    @Schema(description = "转换数量")
    private BigDecimal conversionQuantity;

    @Schema(description = "转换单位")
    private String conversionUnit;

    @Schema(description = "转换不合格数量")
    private BigDecimal conversionQuantityUnquanlified;

}
