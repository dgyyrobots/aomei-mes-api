package com.dofast.module.qms.enums;

import com.dofast.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode DEFECT_NOT_EXISTS = new ErrorCode(810001, "常见缺陷不存在");
    ErrorCode DEFECT_RECORD_NOT_EXISTS = new ErrorCode(811001, "检验单缺陷记录不存在");
    ErrorCode INDEX_NOT_EXISTS = new ErrorCode(812001, "检测项不存在");
    ErrorCode INDEX_CODE_EXISTS = new ErrorCode(812002, "检测项编号已存在");
    ErrorCode INDEX_NAME_EXISTS = new ErrorCode(812003, "检测项名称已存在");
    ErrorCode TEMPLATE_PRODUCT_NOT_EXISTS = new ErrorCode(813001, "检测模板-产品不存在");
    ErrorCode PRODUCT_HAS_TEMPLATE = new ErrorCode(813002, "产品已设置检测模板");
    ErrorCode TEMPLATE_INDEX_NOT_EXISTS = new ErrorCode(814001, "检测模板-检测项不存在");
    ErrorCode TEMPLATE_NOT_EXISTS = new ErrorCode(815001, "检测模板不存在");
    ErrorCode TEMPLATE_CODE_EXISTS = new ErrorCode(815002, "检测模板编号已存在");
    ErrorCode TEMPLATE_TYPE_EXISTS = new ErrorCode(815002, "请至少选择一项检测种类!");

    ErrorCode IPQC_LINE_NOT_EXISTS = new ErrorCode(816001, "过程检验单行不存在");
    ErrorCode IPQC_NOT_EXISTS = new ErrorCode(816002, "过程检验单不存在");
    ErrorCode IPQC_CODE_EXISTS = new ErrorCode(816002, "检测单编码已存在");
    ErrorCode WORKORDER_NOT_HAS_IPQC_TEMPLATE = new ErrorCode(816002, "当前工单生产的产品未配置此类型的检验模板");
    ErrorCode CAN_NOT_DELETE = new ErrorCode(816002, "只能删除草稿状态的单据");
    ErrorCode OQC_LINE_NOT_EXISTS = new ErrorCode(817001, "出货检验单行不存在");
    ErrorCode OQC_NOT_EXISTS = new ErrorCode(817002, "出货检验单不存在");
    ErrorCode OQC_CODE_EXISTS = new ErrorCode(817003, "出货单编号已存在");
    ErrorCode PRODUCT_NOT_HAS_OQC_TEMPLATE = new ErrorCode(817004, "当前产品未配置检测模板");

    ErrorCode IQC_LINE_NOT_EXISTS = new ErrorCode(818001, "来料检验单行不存在");
    ErrorCode IQC_NOT_EXISTS = new ErrorCode(818002, "来料检验单不存在");
    ErrorCode IQC_CODE_EXISTS = new ErrorCode(818002, "单据编号已存在");
    ErrorCode IQC_PRODUCT_NOT_EXISTS = new ErrorCode(818002, "当前产品未配置检测模板");
    ErrorCode CAN_NOT_DELETED = new ErrorCode(818002, "只能删除草稿状态单据");

}
