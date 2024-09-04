package com.dofast.module.cmms.controller.admin.dvrepair.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备维修单 Excel 导出 Request VO，参数和 DvRepairPageReqVO 是一致的")
@Data
public class DvRepairExportReqVO {

    @Schema(description = "维修单编号")
    private String repairCode;

    @Schema(description = "维修单名称", example = "李四")
    private String repairName;

    @Schema(description = "设备ID", example = "24555")
    private Long machineryId;

    @Schema(description = "设备编码")
    private String machineryCode;

    @Schema(description = "设备名称", example = "赵六")
    private String machineryName;

    @Schema(description = "品牌")
    private String machineryBrand;

    @Schema(description = "规格型号")
    private String machinerySpec;

    @Schema(description = "设备类型ID", example = "30071")
    private Long machineryTypeId;

    @Schema(description = "报修日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] requireDate;

    @Schema(description = "维修完成日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] finishDate;

    @Schema(description = "验收日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] confirmDate;

    @Schema(description = "维修结果")
    private String repairResult;

    @Schema(description = "维修人员")
    private String acceptedBy;

    @Schema(description = "验收人员")
    private String confirmBy;

    @Schema(description = "单据状态", example = "2")
    private String status;

    @Schema(description = "备注", example = "你猜")
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
