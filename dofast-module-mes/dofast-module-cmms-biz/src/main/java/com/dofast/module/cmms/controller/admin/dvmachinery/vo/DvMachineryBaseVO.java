package com.dofast.module.cmms.controller.admin.dvmachinery.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 设备台账 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DvMachineryBaseVO {

    @Schema(description = "设备类型编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备类型编码不能为空")
    private String machineryCode;

    @Schema(description = "设备类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "设备类型名称不能为空")
    private String machineryName;

    @Schema(description = "品牌")
    private String machineryBrand;

    @Schema(description = "规格型号")
    private String machinerySpec;

    @Schema(description = "设备类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "14987")
    @NotNull(message = "设备类型ID不能为空")
    private Long machineryTypeId;

    @Schema(description = "设备类型编码")
    private String machineryTypeCode;

    @Schema(description = "设备类型名称", example = "王五")
    private String machineryTypeName;

    @Schema(description = "所属车间ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "23710")
    @NotNull(message = "所属车间ID不能为空")
    private Long workshopId;

    @Schema(description = "所属车间编码")
    private String workshopCode;

    @Schema(description = "所属车间名称", example = "李四")
    private String workshopName;

    @Schema(description = "设备状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "设备状态不能为空")
    private String status;

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

    @Schema(description = "ERP设备编码")
    private String erpMachineryCode;

    @Schema(description = "产品ID")
    private Long productId;

}
