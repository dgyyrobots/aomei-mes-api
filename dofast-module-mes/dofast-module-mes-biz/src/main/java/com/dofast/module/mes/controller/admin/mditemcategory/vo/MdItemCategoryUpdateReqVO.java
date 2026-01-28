package com.dofast.module.mes.controller.admin.mditemcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 物料类别更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MdItemCategoryUpdateReqVO extends MdItemCategoryBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24412")
    @NotNull(message = "ID不能为空")
    private Integer id;

}
