package com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 点检计划记录单头 Excel 导出 Request VO，参数和 DvCheckPlanHeaderLogPageReqVO 是一致的")
@Data
public class DvCheckPlanHeaderLogExportReqVO {

    @Schema(description = "计划编码")
    private String planCode;

    @Schema(description = "计划名称", example = "芋艿")
    private String planName;

    @Schema(description = "计划类型", example = "2")
    private String planType;

    @Schema(description = "设备ID", example = "3066")
    private Long machineryId;

    @Schema(description = "设备编码")
    private String machineryCode;

    @Schema(description = "设备名称", example = "张三")
    private String machineryName;

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
