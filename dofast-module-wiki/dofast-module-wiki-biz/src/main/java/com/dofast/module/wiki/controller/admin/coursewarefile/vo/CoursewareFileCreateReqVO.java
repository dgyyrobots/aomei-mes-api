package com.dofast.module.wiki.controller.admin.coursewarefile.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 课件文件的保存地址创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CoursewareFileCreateReqVO extends CoursewareFileBaseVO {

}
