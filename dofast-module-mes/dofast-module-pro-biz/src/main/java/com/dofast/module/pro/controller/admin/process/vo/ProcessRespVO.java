package com.dofast.module.pro.controller.admin.process.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 生产工序 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcessRespVO extends ProcessBaseVO {

    @Schema(description = "工序ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "16302")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
