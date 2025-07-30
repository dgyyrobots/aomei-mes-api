package com.dofast.module.qms.controller.admin.defectrecord.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 检验单缺陷记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DefectRecordPageReqVO extends PageParam {

    @Schema(description = "检验单类型", example = "2")
    private String qcType;

    @Schema(description = "检验单ID", example = "7121")
    private Long qcId;

    @Schema(description = "检验单行ID", example = "2406")
    private Long lineId;

    @Schema(description = "缺陷描述", example = "芋艿")
    private String defectName;

    @Schema(description = "缺陷等级")
    private String defectLevel;

    @Schema(description = "缺陷数量")
    private BigDecimal defectQuantity;

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
