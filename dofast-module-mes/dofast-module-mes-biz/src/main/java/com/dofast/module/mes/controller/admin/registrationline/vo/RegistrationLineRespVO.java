package com.dofast.module.mes.controller.admin.registrationline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 计时登记记录 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistrationLineRespVO extends RegistrationLineBaseVO {

    @Schema(description = "计时登记记录Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18291")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
