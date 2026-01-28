package com.dofast.module.pro.controller.admin.feedbackdefect.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 报工缺陷 Excel VO
 *
 * @author 惠智造
 */
@Data
public class FeedbackDefectExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("报工单ID")
    private String feedbackId;

    @ExcelProperty("生产任务编号")
    private String taskCode;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("缺陷项名称")
    private String defectName;

    @ExcelProperty("缺陷项id")
    private Long defectId;

    @ExcelProperty("起始米数")
    private String startMeter;

    @ExcelProperty("结束米数")
    private String endMeter;

    @ExcelProperty("缺陷米数")
    private String defectMeter;

    @ExcelProperty( "工序编码")
    private String processCode;

    @ExcelProperty("工序名称")
    private String processName;

    @ExcelProperty("来源班组")
    private String originTeamCode;

    @ExcelProperty("来源物料批次")
    private String originBatchCode;

    @ExcelProperty("来源物料报工单")
    private String originFeedbackCode;

}
