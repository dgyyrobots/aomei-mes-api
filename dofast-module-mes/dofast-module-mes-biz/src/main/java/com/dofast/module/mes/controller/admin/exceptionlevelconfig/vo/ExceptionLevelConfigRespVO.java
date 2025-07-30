package com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 异常等级配置 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExceptionLevelConfigRespVO extends ExceptionLevelConfigBaseVO {

    @Schema(description = "异常等级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5314")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
