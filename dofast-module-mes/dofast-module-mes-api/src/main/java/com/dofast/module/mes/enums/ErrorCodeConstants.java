package com.dofast.module.mes.enums;

import com.dofast.framework.common.exception.ErrorCode;

public interface   ErrorCodeConstants {
    ErrorCode AUTO_CODE_RULE_NOT_EXISTS = new ErrorCode(900001, "编码生成规则不存在");
    ErrorCode AUTO_CODE_PART_NOT_EXISTS = new ErrorCode(900002, "编码生成规则组成不存在");
    ErrorCode AUTO_CODE_RESULT_NOT_EXISTS = new ErrorCode(900003, "编码生成记录不存在");


    ErrorCode MD_ITEM_NOT_EXISTS = new ErrorCode(410111, "物料产品不存在");
    ErrorCode MD_ITEM_CODE_NOT_UNIQUE = new ErrorCode(410112, "物料编码已存在");
    ErrorCode MD_ITEM_NAME_NOT_UNIQUE = new ErrorCode(410113, "物料名称已存在");

    ErrorCode MD_ITEM_TYPE_NOT_EXISTS = new ErrorCode(411001, "物料产品分类不存在");
    ErrorCode MD_ITEM_TYPE_CODE_NOT_UNIQUE = new ErrorCode(411002, "物料产品编码不存在");
    ErrorCode MD_ITEM_TYPE_NAME_NOT_UNIQUE = new ErrorCode(411003, "物料产品名称不存在");
    ErrorCode MD_ITEM_TYPE_HAS_CHILDREN = new ErrorCode(411003, "分类下有子分类，请先删除子分类");
    ErrorCode MD_ITEM_TYPE_HAS_ITEM = new ErrorCode(411003, "分类下有子分类，请先删除子分类");


    ErrorCode MD_UNIT_MEASURE_NOT_EXISTS = new ErrorCode(412001, "单位不存在");
    ErrorCode MD_UNIT_CONVERSE_NOT_EXISTS = new ErrorCode(412002, "单位换算不存在");

    ErrorCode MD_CLIENT_NOT_EXISTS = new ErrorCode(413001, "客户不存在");
    ErrorCode MD_CLIENT_NAME_NOT_UNIQUE = new ErrorCode(413002, "客户名称已存在");
    ErrorCode MD_CLIENT_CODE_NOT_UNIQUE = new ErrorCode(413003, "客户编码已存在");
    ErrorCode MD_CLIENT_NICK_NOT_UNIQUE = new ErrorCode(413004, "客户简称已存在");

    ErrorCode MD_VENDOR_NOT_EXISTS = new ErrorCode(414001, "供应商不存在");
    ErrorCode MD_VENDOR_NAME_NOT_UNIQUE = new ErrorCode(414002, "供应商名称已存在");
    ErrorCode MD_VENDOR_CODE_NOT_UNIQUE = new ErrorCode(414003, "供应商编码已存在");
    ErrorCode MD_VENDOR_NICK_NOT_UNIQUE = new ErrorCode(414004, "供应商简称已存在");

    ErrorCode MD_WORKSHOP_NOT_EXISTS = new ErrorCode(415001, "车间不存在");
    ErrorCode MD_WORKSHOP_CODE_NOT_UNIQUE = new ErrorCode(415002, "车间编码已存在");
    ErrorCode MD_WORKSHOP_NAME_NOT_UNIQUE = new ErrorCode(415003, "车间名称已存在");

    ErrorCode MD_WORKSTATION_NOT_EXISTS = new ErrorCode(416001, "工作站不存在");
    ErrorCode MD_WORKSTATION_CODE_NOT_UNIQUE = new ErrorCode(416002, "工作站编码已存在");
    ErrorCode MD_WORKSTATION_NAME_NOT_UNIQUE = new ErrorCode(416003, "工作站名称已存在");

    ErrorCode MD_PRODUCT_BOM_NOT_EXISTS = new ErrorCode(417001, "产品BOM关系不存在");
    ErrorCode MD_PRODUCT_BOM_NOT_BE_SELF = new ErrorCode(417002, "产品不能作为自身的BOM物料");
    ErrorCode CODE_RULE_PART_NOT_UNIQUE = new ErrorCode(417003, "规则组成不唯一，清检查组成编码、组成名称、组成序号");
    ErrorCode RULE_CODE_EXITS = new ErrorCode(417004, "自动编码规则的编号重复");
    ErrorCode RULE_NAME_EXITS = new ErrorCode(417005, "自动编码规则的名称重复");

    ErrorCode MD_WORKSTATION_MACHINE_NOT_EXISTS = new ErrorCode(418001, "设备资源不存在");
    ErrorCode MD_WORKSTATION_TOOL_NOT_EXISTS = new ErrorCode(418002, "工装夹具资源不存在");
    ErrorCode MD_WORKSTATION_WORKER_NOT_EXISTS = new ErrorCode(418003, "人力资源不存在");

    ErrorCode MD_PRODUCT_SOP_NOT_EXISTS = new ErrorCode(418004, "产品SOP不存在");

    // === 用户工作站绑定关系 419001 ===
    ErrorCode USER_WORKSTATION_NOT_EXISTS = new ErrorCode(419001, "用户工作站绑定关系不存在");

    ErrorCode QUALITY_ABNORMITY_NOT_EXISTS = new ErrorCode(420001, "品质异常信息不存在");

    ErrorCode FREEZE_INFO_NOT_EXISTS = new ErrorCode(420002, "产品冻结信息不存在");

    ErrorCode DEFECTIVE_INFO_NOT_EXISTS = new ErrorCode(420003, "不良品信息管理不存在");

    ErrorCode INTERFACE_LOG_NOT_EXISTS = new ErrorCode(430001, "接口操作日志不存在");

    ErrorCode ELECTROPLATE_LOG_NOT_EXISTS = new ErrorCode(430004, "制版房记录不存在");

    ErrorCode SUBCLASS_EXCEPTION_NOT_EXISTS = new ErrorCode(430005, "子类异常项配置不存在");

    ErrorCode EXCEPTION_LEVEL_CONFIG_NOT_EXISTS = new ErrorCode(430006, "异常等级配置不存在");


    ErrorCode EXCEPTION_NOT_EXISTS = new ErrorCode(430007, "异常登记不存在");

    ErrorCode REGISTRATION_NOT_EXISTS = new ErrorCode(430008, "计时登记不存在");

    ErrorCode REGISTRATION_TASK_EXISTS = new ErrorCode(430008, "已存在相同任务单, 登记类型的计时记录!");

    ErrorCode REGISTRATION_NOT_SUPPORT = new ErrorCode(430008, "当前计时已完成!");

    ErrorCode REGISTRATION_STATUS_STOP = new ErrorCode(430008, "请先开始计时!");

    ErrorCode REGISTRATION_STATUS_START = new ErrorCode(430008, "选中行已开始计时!");


    ErrorCode REGISTRATION_LINE_NOT_EXISTS = new ErrorCode(430009, "计时登记记录不存在");

    ErrorCode MD_ITEM_CATEGORY_NOT_EXISTS = new ErrorCode(430010, "物料类别不存在");



}
