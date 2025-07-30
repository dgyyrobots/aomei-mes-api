package com.dofast.module.mes.controller.admin.subclassexception.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 子类异常项配置更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubclassExceptionUpdateReqVO extends SubclassExceptionBaseVO {

    @Schema(description = "子类异常项ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14360")
    @NotNull(message = "子类异常项ID不能为空")
    private Long id;

}
