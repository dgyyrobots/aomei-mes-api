package com.dofast.module.cal.controller.admin.team.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 班组分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeamPageReqVO extends PageParam {

    @Schema(description = "班组编号")
    private String teamCode;

    @Schema(description = "班组名称", example = "王五")
    private String teamName;

    @Schema(description = "班组类型", example = "1")
    private String calendarType;

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

    @Schema(description = "负责人Id", example = "216")
    private Long principalId;

    @Schema(description = "负责人名称", example = "张三")
    private String principalName;

    @Schema(description = "班组人数", example = "32463")
    private Long personCount;

    @Schema(description = "生产设备名称", example = "芋艿")
    private String machineryName;

    @Schema(description = "生产设备编码")
    private String machineryCode;

    @Schema(description = "生产设备ID", example = "14315")
    private Long machineryId;

    @Schema(description = "夜班负责人Id", example = "216")
    private Long nightPrincipalId;

    @Schema(description = "夜班负责人名称", example = "张三")
    private String nightPrincipalName;

}
