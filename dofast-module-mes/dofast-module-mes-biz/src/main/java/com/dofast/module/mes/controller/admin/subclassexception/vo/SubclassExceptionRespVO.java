package com.dofast.module.mes.controller.admin.subclassexception.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 子类异常项配置 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SubclassExceptionRespVO extends SubclassExceptionBaseVO {

    @Schema(description = "子类异常项ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14360")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
