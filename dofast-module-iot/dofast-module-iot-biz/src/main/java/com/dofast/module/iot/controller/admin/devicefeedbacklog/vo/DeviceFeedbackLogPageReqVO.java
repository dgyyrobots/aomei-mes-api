package com.dofast.module.iot.controller.admin.devicefeedbacklog.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.dofast.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.dofast.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备产量日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceFeedbackLogPageReqVO extends PageParam {

    @Schema(description = "设备ID", example = "2817")
    private Long deviceId;

    @Schema(description = "设备名称", example = "赵六")
    private String deviceName;

    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "设备产量")
    private BigDecimal quantity;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "报工状态")
    private String  enableStatus;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
