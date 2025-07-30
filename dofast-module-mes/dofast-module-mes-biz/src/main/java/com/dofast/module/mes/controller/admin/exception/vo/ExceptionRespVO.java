package com.dofast.module.mes.controller.admin.exception.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 异常登记 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExceptionRespVO extends ExceptionBaseVO {

    @Schema(description = "异常等级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "10187")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
