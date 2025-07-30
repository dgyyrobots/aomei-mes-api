package com.dofast.module.mes.controller.admin.exception.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 异常登记 Excel 导出 Request VO，参数和 ExceptionPageReqVO 是一致的")
@Data
public class ExceptionExportReqVO {

    @Schema(description = "异常编号")
    private String exceptionCode;

    @Schema(description = "异常类型", example = "1")
    private String exceptionType;

    @Schema(description = "异常子类型")
    private String subclassExceptionCode;

    @Schema(description = "异常子类型")
    private String subclassExceptionName;

    @Schema(description = "异常等级编码")
    private String exceptionLevelCode;

    @Schema(description = "异常等级名称")
    private String exceptionLevelName;

    @Schema(description = "异常等级")
    private String exceptionLevelColor;

    @Schema(description = "异常标题")
    private String title;

    @Schema(description = "详细描述", example = "随便")
    private String description;

    @Schema(description = "登记时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] registerTime;

    @Schema(description = "登记人")
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
    private LocalDateTime[] closeTime;

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
