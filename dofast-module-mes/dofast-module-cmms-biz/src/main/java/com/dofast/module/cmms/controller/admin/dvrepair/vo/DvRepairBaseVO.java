package com.dofast.module.cmms.controller.admin.dvrepair.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 设备维修单 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DvRepairBaseVO {

    @Schema(description = "维修单编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "维修单编号不能为空")
    private String repairCode;

    @Schema(description = "维修单名称", example = "李四")
    private String repairName;

    @Schema(description = "设备ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24555")
    @NotNull(message = "设备ID不能为空")
    private Long machineryId;

    @Schema(description = "设备编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "设备编码不能为空")
    private String machineryCode;

    @Schema(description = "设备名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotNull(message = "设备名称不能为空")
    private String machineryName;

    @Schema(description = "品牌")
    private String machineryBrand;

    @Schema(description = "规格型号")
    private String machinerySpec;

    @Schema(description = "设备类型ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "30071")
    @NotNull(message = "设备类型ID不能为空")
    private Long machineryTypeId;

    @Schema(description = "报修日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime requireDate;

    @Schema(description = "维修完成日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime finishDate;

    @Schema(description = "验收日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime confirmDate;

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

    @Schema(description = "维修类型")
    private String repairType;


    public Long getId(){
        return null;
    }
}
