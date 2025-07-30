package com.dofast.module.iot.controller.admin.devicefeedbacklog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 设备产量日志创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceFeedbackLogCreateReqVO extends DeviceFeedbackLogBaseVO {

}
