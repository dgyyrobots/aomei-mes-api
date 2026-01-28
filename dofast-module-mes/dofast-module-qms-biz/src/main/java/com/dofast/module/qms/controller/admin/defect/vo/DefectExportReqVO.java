package com.dofast.module.qms.controller.admin.defect.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 常见缺陷 Excel 导出 Request VO，参数和 DefectPageReqVO 是一致的")
@Data
public class DefectExportReqVO {

    @Schema(description = "缺陷编码")
    private String defectCode;

    @Schema(description = "缺陷描述", example = "王五")
    private String defectName;

    @Schema(description = "检测项类型", example = "1")
    private String indexType;

    @Schema(description = "缺陷等级")
    private String defectLevel;

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

    @Schema(description = "工序编码")
    private String processCode;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "工序名称")
    private String processName;

}
