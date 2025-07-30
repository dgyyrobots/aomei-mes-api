package com.dofast.module.cmms.enums;

import com.dofast.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode DV_MACHINERY_TYPE_NOT_EXISTS = new ErrorCode(420001, "设备类型不存在");

    ErrorCode DV_MACHINERY_NOT_EXISTS = new ErrorCode(421001, "设备台账不存在");
    ErrorCode DV_MACHINERY_BIND_TYPE = new ErrorCode(421002, "设备类型下已配置了设备");

    ErrorCode DV_SUBJECT_NOT_EXISTS = new ErrorCode(422001, "设备点检保养项目不存在");
    ErrorCode DV_CHECK_PLAN_NOT_EXISTS = new ErrorCode(422002, "设备点检保养计划头不存在");

    ErrorCode DV_REPAIR_NOT_EXISTS = new ErrorCode(423001, "设备维修单不存在");
    ErrorCode DV_REPAIR_CODE_NOT_UNIQUE = new ErrorCode(423001, "维修单编号已存");

    ErrorCode DV_REPAIR_LINE_NOT_EXISTS = new ErrorCode(424000, "设备维修单行不存在");

    ErrorCode DV_CHECK_SUBJECT_NOT_EXISTS = new ErrorCode(425000, "点检项目不存在");
    ErrorCode DV_CHECK_SUBJECT_HAS_EXISTS = new ErrorCode(425001, "点检项目已经添加过");

    ErrorCode DV_CHECK_MACHINERY_NOT_EXISTS = new ErrorCode(426000, "点检设备不存在");
    ErrorCode DV_CHECK_MACHINERY_HAS_EXISTS = new ErrorCode(426000, "设备已设置过点检计划");

    ErrorCode DV_CHECK_PLAN_HEADER_LOG_NOT_EXISTS = new ErrorCode(427001, "点检计划记录单头不存在");
    ErrorCode DV_CHECK_PLAN_LINE_LOG_NOT_EXISTS = new ErrorCode(427002, "点检计划记录单身不存在");

    ErrorCode DV_MACHINERY_NOT_CHECKPLAN = new ErrorCode(421001, "当前设备不存在点检计划");


}
