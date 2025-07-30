package com.dofast.module.purchase.enums;

import com.dofast.framework.common.exception.ErrorCode;

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

    ErrorCode MATERIAL_NOT_WAREHOUSE = new ErrorCode(1030006000, "当前物料未入库或不存在!");

    ErrorCode MATERIAL_MAX_STOCK = new ErrorCode(1030006000, "物料在调拨线边仓超出最大上限");

    ErrorCode TOOL_NOT_ENOUGH= new ErrorCode(1030007000, "镭射版库存不足!");

    ErrorCode RETREATE_ORDER_NOT_EXISTS = new ErrorCode(1030008000, "ERP仓退单不存在");

    ErrorCode RECEIVE_CANNOT_EXCEED = new ErrorCode(1030009000, "收货总数不能超过采购数量的105%");

    ErrorCode FIND_ORIGIN_GOODS = new ErrorCode(1030010000, "找寻到母单, 请使用拆分后的商品信息!");

    ErrorCode ERP_ERROR = new ErrorCode(1030010000, "ERP接口异常!");

    ErrorCode PARAMS_ERROR = new ErrorCode(1030010000, "获取条码类型异常, 请检查输入法是否为英文!");



}
