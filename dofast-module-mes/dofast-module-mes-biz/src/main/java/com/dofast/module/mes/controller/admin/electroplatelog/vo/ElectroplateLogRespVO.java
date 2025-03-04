package com.dofast.module.mes.controller.admin.electroplatelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 制版房记录 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ElectroplateLogRespVO extends ElectroplateLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "17965")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
