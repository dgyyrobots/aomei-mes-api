package com.dofast.module.pro.controller.admin.feedbackaudit.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.constraints.NotNull;

/**
 * 报工审批主表 Excel VO
 *
 * @author 惠智造
 */
@Data
public class FeedbackAuditExcelVO {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty("审批单号")
    private String auditCode;

    @ExcelProperty("提交人ID")
    private Long submitUserId;

    @ExcelProperty("提交人姓名")
    private String submitUserName;

    @ExcelProperty("提交人昵称")
    private String submitNickName;

    @ExcelProperty("提交时间")
    private LocalDateTime submitTime;

    @ExcelProperty("审批人ID")
    private Long auditUserId;

    @ExcelProperty("审批人姓名")
    private String auditUserName;

    @ExcelProperty("审批人昵称")
    private String auditNickName;

    @ExcelProperty("审批时间")
    private LocalDateTime auditTime;

    @ExcelProperty("状态(PENDING:待审批 APPROVED:已审批 REJECTED:已驳回)")
    private String status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
