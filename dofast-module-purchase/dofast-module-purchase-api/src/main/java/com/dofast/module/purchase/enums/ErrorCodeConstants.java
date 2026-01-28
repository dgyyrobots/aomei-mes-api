package com.dofast.module.purchase.enums;

import com.dofast.framework.common.exception.ErrorCode;
import com.dofast.framework.common.pojo.CommonResult;

/**
 *
 */
public interface ErrorCodeConstants {

    // === 采购商品明细 1030000000 ===
    ErrorCode GOODS_NOT_EXISTS = new ErrorCode(1030001000, "采购商品明细不存在");

    // === 采购商品明细 1030000000 ===
    ErrorCode GOODS_NOT_CONFIG = new ErrorCode(1030001001, "当前采购单下还有未配置的单据信息!");

    ErrorCode GOODS_NOT_RECEIVE = new ErrorCode(1030001001, "当前采购单下还有未收货的单据信息!");

    // === 采购入库单 1030002000 ===
    ErrorCode INVOICE_NOT_EXISTS = new ErrorCode(1030002000, "采购入库单不存在");

    // === 采购订单 1030003000 ===
    ErrorCode ORDER_NOT_EXISTS = new ErrorCode(1030003000, "采购订单不存在");

    // === 采购退货 1030004000 ===
    ErrorCode REFUND_NOT_EXISTS = new ErrorCode(1030004000, "采购退货不存在");

    ErrorCode GOODS_NOT_WAREHOUSE = new ErrorCode(1030005000, "采购商品未入库");

    ErrorCode MATERIAL_NOT_WAREHOUSE = new ErrorCode(1030006000, "当前物料未入库, 请进行二次确认!");

    ErrorCode MATERIAL_MAX_STOCK = new ErrorCode(1030006000, "物料在调拨线边仓超出最大上限");

    ErrorCode TOOL_NOT_ENOUGH= new ErrorCode(1030007000, "镭射版库存不足!");

    ErrorCode RETREATE_ORDER_NOT_EXISTS = new ErrorCode(1030008000, "ERP仓退单不存在");

    ErrorCode RECEIVE_CANNOT_EXCEED = new ErrorCode(1030009000, "收货总数不能超过采购数量的105%");

    ErrorCode FIND_ORIGIN_GOODS = new ErrorCode(1030010000, "找寻到母单, 请使用拆分后的商品信息!");

    ErrorCode FIND_SUB_GOODS = new ErrorCode(1030010000, "无法拆分非来源单据物料!");

    ErrorCode ERP_ERROR = new ErrorCode(1030010000, "ERP接口异常!");

    ErrorCode PARAMS_ERROR = new ErrorCode(1030010000, "获取条码类型异常, 请检查输入法是否为英文!");

    ErrorCode MERGE_AT_LEAST_TWO = new ErrorCode(1030010001, "至少需要选择两条记录进行合并");

    ErrorCode MERGE_ALREADY_WAREHOUSED = new ErrorCode(1030010002, "已入库的商品不允许合并");

    ErrorCode MERGE_SAME_PO_AND_ITEM = new ErrorCode(1030010003, "只能合并相同采购单和物料编号的商品");

    ErrorCode MERGE_SAME_ERP_RECEIVE_CODE = new ErrorCode(1030010004, "未打印和已打印状态的商品只能合并相同ERP收货单号的记录");

    ErrorCode MERGE_SAME_STATUS = new ErrorCode(1030010005, "只能合并相同状态的单据");

    ErrorCode MATERIAL_ALLOCATE = new ErrorCode(621002, "当前物料存在未确认记录，请检查！");

    ErrorCode PURCHASE_ORDER_LOCKED = new ErrorCode(1030010006, "当前采购单正在操作中，请稍后再试！");
}
