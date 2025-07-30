package com.dofast.module.cmms.controller.admin.dvcheckplanheaderlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 点检计划记录单头创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DvCheckPlanHeaderLogCreateReqVO extends DvCheckPlanHeaderLogBaseVO {

}
