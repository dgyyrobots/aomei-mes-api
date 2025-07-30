package com.dofast.module.mes.controller.admin.registration.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 计时登记更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistrationUpdateReqVO extends RegistrationBaseVO {

    @Schema(description = "计时登记ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "31277")
    @NotNull(message = "计时登记ID不能为空")
    private Long id;

}
