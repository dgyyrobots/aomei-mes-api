package com.dofast.module.mes.controller.admin.mditemcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 物料类别 Excel 导出 Request VO，参数和 MdItemCategoryPageReqVO 是一致的")
@Data
public class MdItemCategoryExportReqVO {

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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
