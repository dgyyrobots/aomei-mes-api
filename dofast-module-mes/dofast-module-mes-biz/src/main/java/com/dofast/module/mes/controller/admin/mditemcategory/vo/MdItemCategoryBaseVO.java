package com.dofast.module.mes.controller.admin.mditemcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 物料类别 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MdItemCategoryBaseVO {

    @Schema(description = "物料类别名称", example = "赵六")
    private String itemCategoryName;

    @Schema(description = "物料类别编码")
    private String itemCategoryCode;

    @Schema(description = "物料工艺")
    private String itemRoute;

    @Schema(description = "物料工艺编码")
    private String itemRouteCode;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "预留字段1")
    private String attr1;

    @Schema(description = "预留字段2")
    private String attr2;

    @Schema(description = "预留字段3")
    private Integer attr3;

    @Schema(description = "预留字段4")
    private Integer attr4;

}
