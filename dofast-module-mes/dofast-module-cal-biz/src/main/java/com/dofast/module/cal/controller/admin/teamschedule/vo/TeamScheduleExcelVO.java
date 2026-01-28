package com.dofast.module.cal.controller.admin.teamschedule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 班组排班 Excel VO
 *
 * @author 惠智造
 */
@Data
public class TeamScheduleExcelVO {

    @ExcelProperty("排班ID")
    private Long id;

    @ExcelProperty("班组ID")
    private Long teamId;

    @ExcelProperty("用户ID")
    private Long userId;

    @ExcelProperty("工作日期")
    private LocalDate workDate;

    @ExcelProperty("班次类型：0-白班，1-夜班")
    private String shiftType;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
