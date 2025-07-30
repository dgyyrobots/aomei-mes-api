package com.dofast.module.mes.controller.admin.registrationline.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 计时登记记录创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RegistrationLineCreateReqVO extends RegistrationLineBaseVO {

}
