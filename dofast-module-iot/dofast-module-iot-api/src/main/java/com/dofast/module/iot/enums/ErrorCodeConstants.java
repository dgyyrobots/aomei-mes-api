package com.dofast.module.iot.enums;

import com.dofast.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {

    // === 物模型模板 TODO 设备告警不存在 ===
    ErrorCode ALERT_NOT_EXISTS = new ErrorCode(1001012000, "设备告警不存在");
    // === 物模型模板 TODO 设备告警日志不存在 ===
    ErrorCode ALERT_LOG_NOT_EXISTS = new ErrorCode(1001012001, "设备告警日志不存在");
    // === 物模型模板 TODO 物模型模板不存在 ===
    ErrorCode THINGS_MODEL_TEMPLATE_NOT_EXISTS = new ErrorCode(1001012002, "物模型模板不存在");

    // === 物模型 TODO 物模型不存在 ===
    ErrorCode THINGS_MODEL_NOT_EXISTS = new ErrorCode(1001012003, "物模型不存在");

    // === 场景联动 TODO 场景联动不存在 ===
    ErrorCode SCENE_NOT_EXISTS = new ErrorCode(1001012004, "场景联动不存在");

    //TODO 产品授权码不存在 ===
    ErrorCode PRODUCT_AUTHORIZE_NOT_EXISTS = new ErrorCode(1001012005, "产品授权码不存在");

    // === 产品 TODO 产品不存在 ===
    ErrorCode PRODUCT_NOT_EXISTS = new ErrorCode(1001012006, "产品不存在");

    // === 设备分组 TODO 设备分组不存在 ===
    ErrorCode GROUP_NOT_EXISTS = new ErrorCode(1001012007, "设备分组不存在");

    // === 设备定时 TODO 设备定时不存在 ===
    ErrorCode DEVICE_JOB_NOT_EXISTS = new ErrorCode(1001012008, "设备定时不存在");

    // === 产品分类 TODO 产品分类不存在 ===
    ErrorCode CATEGORY_NOT_EXISTS = new ErrorCode(1001012009, "产品分类不存在");

    // === 设备 TODO 设备不存在 ===
    ErrorCode DEVICE_NOT_EXISTS = new ErrorCode(1001012010, "设备不存在");

    // === 设备日志 TODO 设备日志不存在 ===
    ErrorCode DEVICE_LOG_NOT_EXISTS = new ErrorCode(1001012011, "设备日志不存在");

    // === 设备用户 TODO 设备用户不存在 ===
    ErrorCode DEVICE_USER_NOT_EXISTS = new ErrorCode(1001012012, "设备用户不存在");

    // === 产品固件 TODO 产品固件不存在 ===
    ErrorCode FIRMWARE_NOT_EXISTS = new ErrorCode(1001012013, "产品固件不存在");

    ErrorCode DEVICE_GROUP_NOT_EXISTS=new ErrorCode(1001012014,"设备分组不存在");

    ErrorCode DEVICE_FEEDBACK_LOG_NOT_EXISTS = new ErrorCode(1001012015, "设备产量日志不存在");

}
