package com.dofast.module.pro.controller.admin.feedbackwarehousinglog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 报工入库日志创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FeedbackWarehousingLogCreateReqVO extends FeedbackWarehousingLogBaseVO {

}
