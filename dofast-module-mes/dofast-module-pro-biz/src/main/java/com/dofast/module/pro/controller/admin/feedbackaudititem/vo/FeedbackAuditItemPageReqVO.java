package com.dofast.module.pro.controller.admin.feedbackaudititem.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报工审批汇总分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditItemPageReqVO extends PageParam {

    @Schema(description = "审批单ID", example = "3579")
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

    @Schema(description = "产品物料ID", example = "13245")
    private Long itemId;

    @Schema(description = "产品物料编码")
    private String itemCode;

    @Schema(description = "产品物料名称", example = "芋艿")
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

    @Schema(description = "报工用户名", example = "芋艿")
    private String userName;

    @Schema(description = "昵称", example = "芋艿")
    private String nickName;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "参与班组成员名称")
    private String allPrincipal;

}
