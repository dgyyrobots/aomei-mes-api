package com.dofast.module.pro.controller.admin.feedbackauditdetail.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 报工审批明细创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackAuditDetailCreateReqVO extends FeedbackAuditDetailBaseVO {

}
