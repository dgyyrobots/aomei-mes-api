package com.dofast.module.mes.controller.admin.registrationline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 计时登记记录更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistrationLineUpdateReqVO extends RegistrationLineBaseVO {

    @Schema(description = "计时登记记录Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18291")
    @NotNull(message = "计时登记记录Id不能为空")
    private Long id;

}
