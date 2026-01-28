package com.dofast.module.cal.controller.admin.teamschedule.vo;

import lombok.*;

import java.time.LocalDate;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 班组排班 Excel 导出 Request VO，参数和 TeamSchedulePageReqVO 是一致的")
@Data
public class TeamScheduleExportReqVO {

    @Schema(description = "班组ID", example = "14089")
    private Long teamId;

    @Schema(description = "用户ID", example = "10137")
    private Long userId;

    @Schema(description = "工作日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDate workDate;

    @Schema(description = "班次类型：0-白班，1-夜班", example = "1")
    private String shiftType;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
