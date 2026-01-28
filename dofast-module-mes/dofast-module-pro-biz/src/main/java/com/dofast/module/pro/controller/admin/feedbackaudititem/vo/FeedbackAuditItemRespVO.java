package com.dofast.module.pro.controller.admin.feedbackaudititem.vo;

import com.dofast.module.pro.controller.admin.feedbackdefect.vo.FeedbackDefectRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 报工审批汇总 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditItemRespVO extends FeedbackAuditItemBaseVO {

    @Schema(description = "主键ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9155")
    private Long id;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 虚拟字段
    @Schema(defaultValue = "缺陷集合")
    private List<FeedbackDefectRespVO> feedbackDefectList;

}
