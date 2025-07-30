package com.dofast.module.mes.controller.admin.subclassexception.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 子类异常项配置 Excel 导出 Request VO，参数和 SubclassExceptionPageReqVO 是一致的")
@Data
public class SubclassExceptionExportReqVO {

    @Schema(description = "异常类型(数据字典)", example = "2")
    private String exceptionType;

    @Schema(description = "子类异常项编号")
    private String subclassExceptionCode;

    @Schema(description = "子类异常项名称", example = "王五")
    private String subclassExceptionName;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
