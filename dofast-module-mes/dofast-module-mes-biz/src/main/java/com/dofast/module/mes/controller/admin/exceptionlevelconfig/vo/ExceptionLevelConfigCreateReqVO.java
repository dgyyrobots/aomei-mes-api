package com.dofast.module.mes.controller.admin.exceptionlevelconfig.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 异常等级配置创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ExceptionLevelConfigCreateReqVO extends ExceptionLevelConfigBaseVO {

}
