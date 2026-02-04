package com.dofast.module.wms.enums;

import com.dofast.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode BARCODE_NOT_EXISTS = new ErrorCode(610001, "条码清单不存在");
    ErrorCode BARCODE_NOT_UNIQUE = new ErrorCode(610002, "当前业务内容的条码已存在");
    ErrorCode BARCODE_NOT_NULL = new ErrorCode(610003, "条码内容不能为空");


    ErrorCode STORAGE_LOCATION_NOT_EXISTS = new ErrorCode(611001, "库区不存在");
    ErrorCode STORAGE_LOCATION_CODE_EXISTS = new ErrorCode(611002, "库区编码已存在");
    ErrorCode STORAGE_LOCATION_NAME_EXISTS = new ErrorCode(611003, "库区名称已存在");

    ErrorCode STORAGE_AREA_NOT_EXISTS = new ErrorCode(612001, "库位不存在");



    ErrorCode WAREHOUSE_NOT_EXISTS = new ErrorCode(613001, "仓库不存在");
    ErrorCode WAREHOUSE_CODE_EXISTS = new ErrorCode(613002, "仓库编码已存在");
    ErrorCode WAREHOUSE_NAME_EXISTS = new ErrorCode(613003, "仓库编码已存在");

    ErrorCode BARCODE_CONFIG_NOT_EXISTS = new ErrorCode(614001, "条码配置不存在");

    ErrorCode MATERIAL_STOCK_NOT_EXISTS = new ErrorCode(615001, "库存记录不存在");
    ErrorCode MATERIAL_STOCK_COUNT_NOT_ENOUGH = new ErrorCode(615001, "库存数量不足");
    ErrorCode ITEM_RECPT_NOT_EXISTS = new ErrorCode(615002, "物料入库单不存在");
    ErrorCode ITEM_RECPT_CODE_EXISTS = new ErrorCode(615012, "单据编号已存在");
    ErrorCode ORDER_STATUS_PREPARE_NOT_DELETE = new ErrorCode(615012, "只能删除草稿状态的单据");



    ErrorCode TRANSACTION_NOT_EXISTS = new ErrorCode(615003, "库存事务不存在");
    ErrorCode TRANSACTION_NOT_NULL= new ErrorCode(61500301, "库存事务不能为空");
    ErrorCode TRANSACTION_QUANTITY_NOT_NULL= new ErrorCode(61500302, "事务数量不能为空");
    ErrorCode TRANSACTION_SourceDocCode_NOT_NULL= new ErrorCode(61500302, "来源单据号不能为空");
    ErrorCode TRANSACTION_SourceDocLineId_NOT_NULL= new ErrorCode(61500302, "来源单据行ID不能为空");


    ErrorCode ITEM_RECPT_LINE_NOT_EXISTS = new ErrorCode(615004, "物料入库单行不存在");
    ErrorCode ITEM_RECPT_LINE_INSERT = new ErrorCode(615022, "请添加入库单行");

    ErrorCode RT_VENDOR_LINE_NOT_EXISTS = new ErrorCode(616001, "供应商退货行不存在");
    ErrorCode RT_VENDOR_NOT_EXISTS = new ErrorCode(616002, "供应商退货不存在");
    ErrorCode RT_VENDOR_CODE_EXISTS = new ErrorCode(616002, "退货单编号已经存在");

    ErrorCode PRODUCT_RECPT_LINE_NOT_EXISTS = new ErrorCode(617001, "产品入库记录表行不存在");
    ErrorCode PRODUCT_RECPT_NOT_EXISTS = new ErrorCode(617002, "产品入库录不存在");
    ErrorCode PRODUCT_RECPT_CODE__EXISTS = new ErrorCode(617002, "入库单编号已存在");
    ErrorCode PRODUCT_RECPT_NEEDED_LINE= new ErrorCode(617002, "请添加要入库的产品");
    ErrorCode PRODUCT_RECPT_LINE_NO_PRODUCT= new ErrorCode(617002, "没有需要处理的产品入库单行");

    ErrorCode PRODUCT_SALSE_LINE_NOT_EXISTS = new ErrorCode(618001, "产品销售出库行不存在");
    ErrorCode PRODUCT_SALSE_NOT_EXISTS = new ErrorCode(618002, "销售出库单不存在");
    ErrorCode PRODUCT_SALSE_ID_EXISTS = new ErrorCode(618003, "出库单编号已存在");
    ErrorCode PRODUCT_SALSE_LINE_PRODUCT_NOT_NULL = new ErrorCode(618004, "出库物资不能为空");
    ErrorCode NO_PROCESS_PRODUCT_SALSE = new ErrorCode(618005, "没有需要处理的产品销售出库单行");

    ErrorCode RT_SALSE_LINE_NOT_EXISTS = new ErrorCode(619001, "产品销售退货行不存在");
    ErrorCode RT_SALSE_NOT_EXISTS = new ErrorCode(619002, "产品销售退货单不存在");
    ErrorCode RT_SALSE_CODE_EXISTS = new ErrorCode(619003, "退货单号已存在");
    ErrorCode RT_SALSE_NEED_LINE = new ErrorCode(619004, "请添加退货单行信息");
    ErrorCode RT_SALSE_LINE_NO_PROCESS = new ErrorCode(619005, "没有需要处理的原料消耗单行");

    ErrorCode PACKAGE_LINE_NOT_EXISTS = new ErrorCode(620001, "装箱明细不存在");
    ErrorCode PACKAGE_NOT_EXISTS = new ErrorCode(620002, "装箱单不存在");
    ErrorCode PACKAGE_CODE_EXISTS = new ErrorCode(620003, "装箱单编号已存在");
    ErrorCode PACKAGE_NOT_ADD_SELL = new ErrorCode(620005, "不能添加自己为子箱");
    ErrorCode PACKAGE_CHILD_HAS_PARENT = new ErrorCode(620006, "当前子箱已经有外箱包装");

    ErrorCode ISSUE_LINE_NOT_EXISTS = new ErrorCode(621001, "生产领料单行不存在");
    ErrorCode ISSUE_HEADER_NOT_EXISTS = new ErrorCode(621002, "生产领料单头不存在");
    ErrorCode ISSUE_HEADER_CDOE_EXISTS = new ErrorCode(621003, "领料单编号已存在");
    ErrorCode ISSUE_HEADER_NOT_DELETED = new ErrorCode(621004, "只能删除草稿状态的单据");
    ErrorCode ISSUE_HEADER_NEED_LINE = new ErrorCode(621004, "请指定领出的物资");
    ErrorCode ISSUE_HEADER_NEED_PROCESS_LINE = new ErrorCode(621005, "没有需要处理的领料单行");
    ErrorCode ISSUE_HEADER_TASK_EXISTS = new ErrorCode(621006, "没有需要处理的领料单行");
    ErrorCode ISSUE_HEADER_NOT_FEEDBACK_EXISTS = new ErrorCode(621007, "当前领料单身存在未报工数据!");
    ErrorCode ISSUE_HEADER_NO_PROCESS = new ErrorCode(621001, "无法领用非当前工序线边仓的物料, 请检查领料单信息!");
    ErrorCode ISSUE_HEADER_NO_ENABLE_PROCESS = new ErrorCode(621001, "选择的任务单不是此工单!");
    ErrorCode ISSUE_LINE_VIRTUAL_WH = new ErrorCode(621001, "当前物料已使用!");
    ErrorCode ISSUE_LINE_BATCH_EXIST = new ErrorCode(621001, "当前物料已上料!");
    ErrorCode ISSUE_HEADER_NO_ISSUE = new ErrorCode(621001, "只允许变更已领料的单据!");
    ErrorCode ISSUE_LINE_NOT_ENABLE = new ErrorCode(621001, "仅允许选中膜类物料!");
    ErrorCode ISSUE_LINE_MULTI_MATERIAL = new ErrorCode(621001, "当前机台已存在膜类物料!!");
    ErrorCode ISSUE_LINE_ITEM_NOT_CONTAIN_BOM = new ErrorCode(621001, "领料失败! 物料不属于当前工单BOM");
    ErrorCode ISSUE_HEADER_NO_TASK = new ErrorCode(621001, "上料失败，当前领料单对应任务单不存在！");
    ErrorCode ISSUE_LINE_DELETE_ERROR = new ErrorCode(621001, "生产领料单已领料，无法删除！");
    ErrorCode ISSUE_ERR_INTERFACE_ERROR = new ErrorCode(621001, "ERP领料接口异常！");
    ErrorCode ISSUE_LINE_NO_NEED_ERP = new ErrorCode(621001, "当前行无需调用ERP领料接口！");
    ErrorCode ISSUE_HEADER_NEED_ERP = new ErrorCode(621001, "上料失败，当前领用物料需进行ERP过账！");
    ErrorCode ISSUE_LINE_NOT_EXECUTE = new ErrorCode(621001, "存在未上料的物料，无需启用！");



    ErrorCode ALLOCATED_HEADER_NEED_PROCESS_LINE = new ErrorCode(621006, "没有需要处理的调拨单行");
    ErrorCode ALLOCATED_HEADER_NEED_TASK_TEAM = new ErrorCode(621006, "当前任务单尚未派工, 请先排产!");
    ErrorCode RT_ISSUE_LINE_NOT_EXISTS = new ErrorCode(622001, "生产退料单行不存在");
    ErrorCode RT_ISSUE_NOT_EXISTS = new ErrorCode(622002, "生产退料单头不存在");
    ErrorCode RT_ISSUE_CODE_EXISTS = new ErrorCode(622003, "退料单编号已存在");
    ErrorCode RT_ISSUE_NEED_MAT = new ErrorCode(622004, "请选择要退料的物资");
    ErrorCode RT_ISSUE_NO_LINE_PROCESS = new ErrorCode(622005, "没有需要处理的退料单行");
    ErrorCode RT_ISSUE_HAS_FEEDBACK = new ErrorCode(622006, "当前任务已报工, 无法进行退料操作!");
    ErrorCode RT_ISSUE_ERR_INTERFACE_ERROR = new ErrorCode(621001, "ERP退料接口异常！");
    ErrorCode RT_ISSUE_NO_NEED_ERP = new ErrorCode(621001, "当前退料信息无需回传ERP！");



    ErrorCode ALLOCATED_HEADER_NEED_BIND_WORKORDER = new ErrorCode(622006, "当前调拨单无需生成领料单!");
    ErrorCode ALLOCATED_HEADER_NEED_NOT_CREATE_ISSUE = new ErrorCode(622006, "当前任务单已存在领料单!");
    ErrorCode ALLOCATED_HEADER_NEED_NOT_FINISHED = new ErrorCode(622006, "调拨单物料未确认, 无法生成领料单!");
    
    ErrorCode SN_NOT_EXISTS = new ErrorCode(623001, "SN码不存在");

    ErrorCode TRANSFER_LINE_NOT_EXISTS = new ErrorCode(624001, "转移单行不存在");
    ErrorCode TRANSFER_NOT_EXISTS = new ErrorCode(624002, "转移单不存在");
    ErrorCode TRANSFER_CODE_EXISTS = new ErrorCode(624003, "转移单编号已存在");
    ErrorCode TRANSFER_NEED_ITEM = new ErrorCode(624004, "请添加需要转移的物资");
    ErrorCode TRANSFER_NEED_LINE = new ErrorCode(624005, "请添加转移单行信息");
    ErrorCode TRANSFER_NO_PROCESS_LINE = new ErrorCode(624005, "没有需要处理的原料消耗单行");
    ErrorCode USELESS_RECPT_ORDER = new ErrorCode(624006, "无效单据");
    ErrorCode RECPT_ORDER_HAS_COMMITTED = new ErrorCode(624007, "当前单据已提交");
    ErrorCode PLEASE_ADD_DETAIL_INFORMATION = new ErrorCode(624008, "请添加明细信息");
    ErrorCode TRANSFER_NO_PROCTS_LINE = new ErrorCode(624009, "没有需要处理的产品产出单行");


    // === 产品产出记录 625001 ===
    ErrorCode PRODUCT_PRODUCE_NOT_EXISTS = new ErrorCode(625001, "产品产出记录不存在");

    // === 产品产出记录表行 626001 ===
    ErrorCode PRODUCT_PRODUCE_LINE_NOT_EXISTS = new ErrorCode(626001, "产品产出记录表行不存在");

    // === 物料消耗记录 627001 ===
    ErrorCode ITEM_CONSUME_NOT_EXISTS = new ErrorCode(627001, "物料消耗记录不存在");

    // === 物料消耗记录行 628001 ===
    ErrorCode ITEM_CONSUME_LINE_NOT_EXISTS = new ErrorCode(628001, "物料消耗记录行不存在");

    ErrorCode STORAGECORE_IS_NOT_EXITS=new ErrorCode(717012,"未找到仓库");

    ErrorCode ALLOCATED_HEADER_NOT_EXISTS = new ErrorCode(629001, "调拨单头不存在");
    ErrorCode ALLOCATED_LINE_NOT_EXISTS = new ErrorCode(629002, "调拨单身不存在");
    ErrorCode ALLOCATED_LINE_STATUS_ERROR = new ErrorCode(629003, "仅允许选择已调拨的单据信息!");
    ErrorCode ALLOCATED_HEADER_TASK_CODE_EXIST = new ErrorCode(629004, "当前任务单已创建调拨单，请勿重复创建！");

    ErrorCode FEED_LINE_NOT_EXISTS = new ErrorCode(630004, "上料详情不存在");

    ErrorCode ALLOCATED_RECORD_NOT_EXISTS = new ErrorCode(6310001, "调拨单身记录不存在");

    ErrorCode ALLOCATED_TASK_EXISTS = new ErrorCode(6310001, "当前任务单号已开立调拨单号");

    ErrorCode ALLOCATED_TASK_NOT_EXISTS = new ErrorCode(6310001, "当前调拨单不存在任务单");

    ErrorCode ELECTROFORM_HEADER_NOT_EXISTS = new ErrorCode(6320001, "制版房领料单头不存在");

    ErrorCode ELECTROFORM_LINE_NOT_EXISTS = new ErrorCode(6320002, "制版房领料单身不存在");

    ErrorCode TOOL_NOT_ENOUGH = new ErrorCode(6320003, "电铸板库存不足, 请先生产!");

    ErrorCode ALLOCATED_INTERFACE_ERROR = new ErrorCode(6310001, "调拨单接口异常!");

    ErrorCode ALLOCATED_MATERIAL_STOCK_NOT_EXISTS = new ErrorCode(6310001, "调拨物料对应库存不存在!");

    ErrorCode USER_NO_POST = new ErrorCode(6320001, "请配置当前用户岗位!");

    ErrorCode WORKORDER_FINSHED_NOT_AVALIABLE = new ErrorCode(6330001, "当前工单已完成, 无法进行其他操作!");

    ErrorCode QUENTITYP_QUALIFIED_NULL = new ErrorCode(6330001, "请输入报工单正确的合格数量!");

    ErrorCode QUERY_PARAM_LOST = new ErrorCode(6330001, "请确认输入参数!");

    ErrorCode INIT_VIRTUAL_WAREHOUSE_ERROR = new ErrorCode(6340001, "初始化虚拟线边仓异常!");
}
