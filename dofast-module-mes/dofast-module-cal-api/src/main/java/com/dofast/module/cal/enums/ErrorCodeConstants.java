package com.dofast.module.cal.enums;

import com.dofast.framework.common.exception.ErrorCode;

/**
 */
public interface ErrorCodeConstants {

    // === 节假日设置 1040000000 ===
    ErrorCode HOLIDAY_NOT_EXISTS = new ErrorCode(1040001000, "节假日设置不存在");
    
    // === 排班计划 1040002000 ===
    ErrorCode PLAN_NOT_EXISTS = new ErrorCode(1040002000, "排班计划不存在");
    
    // === 计划班组 1040003000 ===
    ErrorCode PLAN_TEAM_NOT_EXISTS = new ErrorCode(1040003000, "计划班组不存在");
    
    // === 班组 1040004000 ===
    ErrorCode TEAM_NOT_EXISTS = new ErrorCode(1040004000, "班组不存在");
    
    // === 计划班次 1040005000 ===
    ErrorCode SHIFT_NOT_EXISTS = new ErrorCode(1040005000, "计划班次不存在");
    
    // === 班组排班 1040006000 ===
    ErrorCode TEAMSHIFT_NOT_EXISTS = new ErrorCode(1040006000, "班组排班不存在");
    
    // === 班组成员 1040007000 ===
    ErrorCode TEAM_MEMBER_NOT_EXISTS = new ErrorCode(1040007000, "班组成员不存在");

    // === 班组排班 TODO 补充编号 ===
    ErrorCode TEAM_SCHEDULE_NOT_EXISTS = new ErrorCode(1040008000, "班组排班不存在");


}
