package com.dofast.module.mes.controller.admin.mditemcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 物料类别 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MdItemCategoryRespVO extends MdItemCategoryBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24412")
    private Integer id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

}
