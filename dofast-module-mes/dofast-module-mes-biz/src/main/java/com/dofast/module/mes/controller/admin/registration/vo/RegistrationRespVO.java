package com.dofast.module.mes.controller.admin.registration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 计时登记 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistrationRespVO extends RegistrationBaseVO {

    @Schema(description = "计时登记ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31277")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
