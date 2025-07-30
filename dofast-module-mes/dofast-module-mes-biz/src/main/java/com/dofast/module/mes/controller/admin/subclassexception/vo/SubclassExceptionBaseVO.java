package com.dofast.module.mes.controller.admin.subclassexception.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 子类异常项配置 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SubclassExceptionBaseVO {

    @Schema(description = "异常类型(数据字典)", example = "2")
    private String exceptionType;

    @Schema(description = "子类异常项编号")
    private String subclassExceptionCode;

    @Schema(description = "子类异常项名称", example = "王五")
    private String subclassExceptionName;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

}
