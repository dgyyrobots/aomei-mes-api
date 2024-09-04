package com.dofast.module.report.controller.admin.PrintLog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 打印日志 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrintLogRespVO extends PrintLogBaseVO {

    @Schema(description = "打印记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9229")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
