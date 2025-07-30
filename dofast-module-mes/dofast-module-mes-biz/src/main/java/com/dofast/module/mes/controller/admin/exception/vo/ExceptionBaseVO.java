package com.dofast.module.mes.controller.admin.exception.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 异常登记 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ExceptionBaseVO {

    @Schema(description = "异常编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String exceptionCode;

    @Schema(description = "异常类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "异常类型不能为空")
    private String exceptionType;

    @Schema(description = "异常子类型")
    private String subclassExceptionCode;

    @Schema(description = "异常子类型")
    private String subclassExceptionName;

    @Schema(description = "异常等级编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "异常等级不能为空")
    private String exceptionLevelCode;

    @Schema(description = "异常等级名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "异常等级不能为空")
    private String exceptionLevelName;


    @Schema(description = "异常等级颜色", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "异常等级颜色不能为空")
    private String exceptionLevelColor;

    @Schema(description = "异常标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "详细描述", example = "随便")
    private String description;

    @Schema(description = "登记时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime registerTime;

    @Schema(description = "登记人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String registerUser;

    @Schema(description = "所属车间ID")
    private Long workshopId;

    @Schema(description = "所属车间")
    private String workshopName;

    @Schema(description = "所属车间编码")
    private String workshopCode;

    @Schema(description = "所属工序ID", example = "9192")
    private Long processId;

    @Schema(description = "所属工序")
    private String processCode;

    @Schema(description = "所属工序名称")
    private String processName;

    @Schema(description = "关联设备编码")
    private String relatedMachineryCode;

    @Schema(description = "关联设备名称")
    private String relatedMachineryName;

    @Schema(description = "关联ERP设备编码")
    private String relatedErpMachineryCode;

    @Schema(description = "关联物料")
    private String relatedMaterial;

    @Schema(description = "关联工单")
    private String relatedWorkorder;

    @Schema(description = "关联任务编码")
    private String relatedTaskCode;

    @Schema(description = "关联任务ID")
    private Long relatedTaskId;

    @Schema(description = "状态", example = "1")
    private String status;

    @Schema(description = "预计影响")
    private String estimatedImpact;

    @Schema(description = "解决方案")
    private String solution;

    @Schema(description = "关闭时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime closeTime;

    @Schema(description = "附件信息")
    private String attachments;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

}
