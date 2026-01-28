package com.dofast.module.cal.controller.admin.teamschedule.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 班组排班创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TeamScheduleCreateReqVO extends TeamScheduleBaseVO {

}
