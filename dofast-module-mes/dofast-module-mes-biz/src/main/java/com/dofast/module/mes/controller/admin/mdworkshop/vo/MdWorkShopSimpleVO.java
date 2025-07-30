package com.dofast.module.mes.controller.admin.mdworkshop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 车间信息简单列表 Simple VO")
@Data
@ToString(callSuper = true)
public class MdWorkShopSimpleVO {
    @Schema(description = "车间ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "车间名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "车间编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;
}
