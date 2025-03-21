package com.dofast.module.hr.controller.admin.salarycommission.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 绩效工资创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalarycommissionCreateReqVO extends SalarycommissionBaseVO {

}
