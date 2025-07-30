package com.dofast.module.cmms.controller.admin.dvcheckplanlinelog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 点检计划记录单身 Excel 导出 Request VO，参数和 DvCheckPlanLineLogPageReqVO 是一致的")
@Data
public class DvCheckPlanLineLogExportReqVO {

    @Schema(description = "计划记录表头ID", example = "7775")
    private Integer headerId;

    @Schema(description = "项目ID", example = "29579")
    private Long subjectId;

    @Schema(description = "项目编码")
    private String subjectCode;

    @Schema(description = "项目名称", example = "芋艿")
    private String subjectName;

    @Schema(description = "项目类型", example = "1")
    private String subjectType;

    @Schema(description = "项目内容")
    private String subjectContent;

    @Schema(description = "标准")
    private String subjectStandard;

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
