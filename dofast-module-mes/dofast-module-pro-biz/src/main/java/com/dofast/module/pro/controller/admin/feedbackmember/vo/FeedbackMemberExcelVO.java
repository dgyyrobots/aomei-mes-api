package com.dofast.module.pro.controller.admin.feedbackmember.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 报工班组人员 Excel VO
 *
 * @author 惠智造
 */
@Data
public class FeedbackMemberExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("报工单编号")
    private String feedbackId;

    @ExcelProperty("生产任务编号")
    private String taskCode;

    @ExcelProperty("班组编号")
    private String teamCode;

    @ExcelProperty("用户ID")
    private Long userId;

    @ExcelProperty("用户名")
    private String userName;

    @ExcelProperty("用户昵称")
    private String nickName;

    @ExcelProperty("岗位Id")
    private String postIds;

    @ExcelProperty("生产产量")
    private BigDecimal quantity;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
