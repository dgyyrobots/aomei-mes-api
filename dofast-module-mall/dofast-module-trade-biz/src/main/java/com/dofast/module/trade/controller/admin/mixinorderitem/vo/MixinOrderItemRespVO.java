package com.dofast.module.trade.controller.admin.mixinorderitem.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 销售的物料明细 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MixinOrderItemRespVO extends MixinOrderItemBaseVO {

    @Schema(description = "销售的物料ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "818")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
