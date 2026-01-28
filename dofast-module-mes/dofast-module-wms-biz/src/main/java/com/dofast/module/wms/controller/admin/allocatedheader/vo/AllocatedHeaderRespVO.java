package com.dofast.module.wms.controller.admin.allocatedheader.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 调拨单头 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
public class AllocatedHeaderRespVO extends AllocatedHeaderBaseVO {

    @Schema(description = "调拨单ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "15487")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "完成者")
    private String completer;

    private List<Map<String, Object>> bomList;
}
