package com.dofast.module.pro.controller.admin.feedbackmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 报工班组人员 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackMemberBaseVO {

    @Schema(description = "报工单编号")
    private String feedbackId;

    @Schema(description = "生产任务编号")
    private String taskCode;

    @Schema(description = "班组编号")
    private String teamCode;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "20173")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "用户名不能为空")
    private String userName;

    @Schema(description = "用户昵称", example = "赵六")
    private String nickName;

    @Schema(description = "岗位Id")
    private String postIds;

    @Schema(description = "生产产量")
    private BigDecimal quantity;




}
