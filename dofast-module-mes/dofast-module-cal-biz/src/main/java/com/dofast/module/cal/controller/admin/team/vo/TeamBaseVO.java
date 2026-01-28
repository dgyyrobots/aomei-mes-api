package com.dofast.module.cal.controller.admin.team.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 班组 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TeamBaseVO {

    @Schema(description = "班组编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "班组编号不能为空")
    private String teamCode;

    @Schema(description = "班组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "班组名称不能为空")
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
