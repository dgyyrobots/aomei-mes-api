package com.dofast.module.pro.controller.admin.feedbackdefect.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 报工缺陷 Excel 导出 Request VO，参数和 FeedbackDefectPageReqVO 是一致的")
@Data
public class FeedbackDefectExportReqVO {

    @Schema(description = "报工单ID", example = "27397")
    private String feedbackId;

    @Schema(description = "生产任务编号")
    private String taskCode;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "缺陷项名称", example = "张三")
    private String defectName;

    @Schema(description = "缺陷项id", example = "4811")
    private Long defectId;

    @Schema(description = "起始米数")
    private String startMeter;

    @Schema(description = "结束米数")
    private String endMeter;

    @Schema(description = "缺陷米数")
    private String defectMeter;

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "工序名称")
    private String processName;

    @Schema(description = "来源班组")
    private String originTeamCode;

    @Schema(description = "来源物料批次")
    private String originBatchCode;

    @Schema(description = "来源物料报工单")
    private String originFeedbackCode;

}
