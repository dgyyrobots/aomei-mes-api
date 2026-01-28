package com.dofast.module.pro.controller.admin.feedbackaudititem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 报工审批汇总 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackAuditItemBaseVO {

    @Schema(description = "审批单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "3579")
    @NotNull(message = "审批单ID不能为空")
    private String auditId;

    @Schema(description = "生产工单ID", example = "29141")
    private Long workorderId;

    @Schema(description = "生产工单编号")
    private String workorderCode;

    @Schema(description = "生产工单名称", example = "李四")
    private String workorderName;

    @Schema(description = "工序ID", example = "6335")
    private Long processId;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称", example = "李四")
    private String processName;

    @Schema(description = "生产任务ID", example = "21118")
    private Long taskId;

    @Schema(description = "生产任务编号")
    private String taskCode;

    @Schema(description = "产品物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "13245")
    @NotNull(message = "产品物料ID不能为空")
    private Long itemId;

    @Schema(description = "产品物料编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "产品物料编码不能为空")
    private String itemCode;

    @Schema(description = "产品物料名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "产品物料名称不能为空")
    private String itemName;

    @Schema(description = "单位")
    private String unitOfMeasure;

    @Schema(description = "规格型号")
    private String specification;

    @Schema(description = "本次报工数量")
    private BigDecimal sumQuantityFeedback;

    @Schema(description = "合格品数量")
    private BigDecimal sumQuantityQualified;

    @Schema(description = "不良品数量")
    private BigDecimal sumQuantityUnquanlified;

    @Schema(description = "工艺损耗数量")
    private BigDecimal sumQuantityExcess;


    @Schema(description = "报工用户名", example = "芋艿")
    private String userName;

    @Schema(description = "昵称", example = "芋艿")
    private String nickName;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "参与班组成员名称")
    private String allPrincipal;

    @Schema(description = "设备Id")
    private Long machineryId;

    @Schema(description = "设备名称")
    private String machineryName;

    @Schema(description = "设备编码")
    private String machineryCode;


}
