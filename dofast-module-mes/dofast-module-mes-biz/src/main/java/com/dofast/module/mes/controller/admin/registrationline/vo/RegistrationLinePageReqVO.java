package com.dofast.module.mes.controller.admin.registrationline.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 计时登记记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistrationLinePageReqVO extends PageParam {

    @Schema(description = "登记编码")
    private String registrationCode;

    @Schema(description = "登记编码", example = "20751")
    private Long registrationId;

    @Schema(description = "登记类型(数据字典)", example = "2")
    private String registrationType;

    @Schema(description = "起始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "关联设备设备编码")
    private String relatedMachineryCode;

    @Schema(description = "关联设备名称", example = "李四")
    private String relatedMachineryName;

    @Schema(description = "关联ERP设备编码")
    private String relatedErpMachineryCode;

    @Schema(description = "所属车间ID", example = "28061")
    private Long workshopId;

    @Schema(description = "所属车间名称", example = "赵六")
    private String workshopName;

    @Schema(description = "所属车间编码")
    private String workshopCode;

    @Schema(description = "关联工单")
    private String relatedWorkorder;

    @Schema(description = "关联任务编码")
    private String relatedTaskCode;

    @Schema(description = "关联任务Id", example = "26035")
    private Long relatedTaskId;

    @Schema(description = "历经时间(分钟)")
    private Integer durationTime;

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
