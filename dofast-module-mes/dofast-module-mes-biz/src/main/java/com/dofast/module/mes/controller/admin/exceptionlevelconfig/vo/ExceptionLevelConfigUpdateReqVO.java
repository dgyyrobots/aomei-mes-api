package com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 异常等级配置更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExceptionLevelConfigUpdateReqVO extends ExceptionLevelConfigBaseVO {

    @Schema(description = "异常等级ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "5314")
    @NotNull(message = "异常等级ID不能为空")
    private Long id;

}
