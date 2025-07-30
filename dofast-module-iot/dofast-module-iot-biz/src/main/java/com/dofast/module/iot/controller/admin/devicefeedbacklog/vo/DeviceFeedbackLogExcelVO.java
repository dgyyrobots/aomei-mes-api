package com.dofast.module.iot.controller.admin.devicefeedbacklog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 设备产量日志 Excel VO
 *
 * @author 惠智造
 */
@Data
public class DeviceFeedbackLogExcelVO {

    @ExcelProperty("设备产量日志ID")
    private Long id;

    @ExcelProperty("设备ID")
    private Long deviceId;

    @ExcelProperty("设备名称")
    private String deviceName;

    @ExcelProperty("设备编码")
    private String deviceCode;

    @ExcelProperty("设备产量")
    private BigDecimal quantity;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("报工状态")
    private String  enableStatus;

}
