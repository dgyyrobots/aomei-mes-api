package com.dofast.module.qms.controller.admin.ipqc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 过程检验单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class IpqcBaseVO {

    @Schema(description = "检验单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String ipqcCode;

    @Schema(description = "检验单名称", example = "张三")
    private String ipqcName;

    @Schema(description = "检验类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "检验类型不能为空")
    private String ipqcType;

    @Schema(description = "检验模板ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27300")
    private Long templateId;

    @Schema(description = "工单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "8284")
    private Long workorderId;

    @Schema(description = "工单编码")
    private String workorderCode;

    @Schema(description = "工单名称", example = "王五")
    private String workorderName;

    @Schema(description = "任务ID", example = "26269")
    private Long taskId;

    @Schema(description = "任务编号")
    private String taskCode;

    @Schema(description = "任务名称", example = "王五")
    private String taskName;

    @Schema(description = "工作站ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4377")
    private Long workstationId;

    @Schema(description = "工作站编号")
    private String workstationCode;

    @Schema(description = "工作站名称", example = "赵六")
    private String workstationName;

    @Schema(description = "工序ID", example = "26922")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "芋艿")
    private String processName;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "27915")
    @NotNull(message = "产品物料ID不能为空")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "芋艿")
    private String itemName;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "检测数量")
    private BigDecimal quantityCheck;

    @Schema(description = "不合格数")
    private BigDecimal quantityUnqualified;

    @Schema(description = "合格品数量")
    private BigDecimal quantityQualified;

    @Schema(description = "致命缺陷率")
    private BigDecimal crRate;

    @Schema(description = "严重缺陷率")
    private BigDecimal majRate;

    @Schema(description = "轻微缺陷率")
    private BigDecimal minRate;

    @Schema(description = "致命缺陷数量")
    private BigDecimal crQuantity;

    @Schema(description = "严重缺陷数量")
    private BigDecimal majQuantity;

    @Schema(description = "轻微缺陷数量")
    private BigDecimal minQuantity;

    @Schema(description = "检测结果")
    private String checkResult;

    @Schema(description = "检测日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime inspectDate;

    @Schema(description = "检测人员")
    private String inspector;

    @Schema(description = "单据状态", example = "2")
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

    @Schema(description = "附件")
    private String adjuncts;

    public Long getId(){return null;}
}
