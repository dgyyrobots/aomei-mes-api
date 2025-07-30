package com.dofast.module.cmms.controller.admin.dvmachinery.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备台账 Excel 导出 Request VO，参数和 DvMachineryPageReqVO 是一致的")
@Data
public class DvMachineryExportReqVO {

    @Schema(description = "设备类型编码")
    private String machineryCode;

    @Schema(description = "设备类型名称", example = "王五")
    private String machineryName;

    @Schema(description = "品牌")
    private String machineryBrand;

    @Schema(description = "规格型号")
    private String machinerySpec;

    @Schema(description = "设备类型ID", example = "14987")
    private Long machineryTypeId;

    @Schema(description = "设备类型编码")
    private String machineryTypeCode;

    @Schema(description = "设备类型名称", example = "王五")
    private String machineryTypeName;

    @Schema(description = "所属车间ID", example = "23710")
    private Long workshopId;

    @Schema(description = "所属车间编码")
    private String workshopCode;

    @Schema(description = "所属车间名称", example = "李四")
    private String workshopName;

    @Schema(description = "设备状态", example = "1")
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

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
