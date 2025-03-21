package com.dofast.module.wiki.controller.admin.category.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 首页的分类 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class WikiCategoryBaseVO {

    @Schema(description = "父id", example = "16854")
    private Long pid;

    @Schema(description = "类别等级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "类别等级不能为空")
    private Boolean categoryLevel;

    @Schema(description = "种类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "种类名称不能为空")
    private String name;

}
