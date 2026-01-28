package com.dofast.module.pro.controller.admin.feedbackdefect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 报工缺陷 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackDefectBaseVO {

    @Schema(description = "报工单ID", example = "27397")
    private String feedbackId;

    @Schema(description = "生产任务编号")
    private String taskCode;

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
