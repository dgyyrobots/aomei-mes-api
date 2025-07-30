package com.dofast.module.mes.constant;

public class Constant {
    /** 是否为系统默认（是） */
    public static final String YES = "Y";

    public static final String NO = "N";
    /** 校验返回结果码 */
    public final static String UNIQUE = "0";
    public final static String NOT_UNIQUE = "1";


    /**
     * 检测单类型，这里的类型是大类
     * 首检、末检等等是过程检验中的子分类
     */
    public static final String QC_TYPE_IQC = "IQC"; //来料检验单
    public static final String QC_TYPE_IPQC = "IPQC"; //过程检验单
    public static final String QC_TYPE_OQC = "OQC"; //出货检验

    /**
     * 甘特图中的TASK类型
     */
    public static final String GANTT_TASK_TYPE_TASK="task";
    public static final String GANTT_TASK_TYPE_PROJECT="project";

    /**
     * 条码格式
     */
    public static final String QR_CODE = "QR_CODE";
    public static final String EAN_CODE = "EAN_CODE";
    public static final String UPC_CODE = "UPC_CODE";
    public static final String CODE39_CODE = "CODE39_CODE";

    /**
     * 条码类型
     */
    public static final String BARCODE_TYPE_ITEM = "ITEM"; //物料
    public static final String BARCODE_TYPE_PACKAGE = "PACKAGE"; //装箱单
    public static final String BARCODE_TYPE_STOCK = "STOCK"; //库存
    public static final String BARCODE_TYPE_MACHINERY = "MACHINERY"; //设备
    public static final String BARCODE_TYPE_WORKSTATION = "WORKSTATION"; //工作站
    public static final String BARCODE_TYPE_WAREHOUSE = "WAREHOUSE"; //仓库
    public static final String BARCODE_TYPE_STORAGELOCATION = "LOCATION"; //库区
    public static final String BARCODE_TYPE_STORAGEAREA = "AREA"; //库位
    public static final String BARCODE_TYPE_TRANSORDER = "TRANSORDER"; //流转单
    public static final String BARCODE_TYPE_CLIENT = "CLIENT"; //客户
    public static final String BARCODE_TYPE_VENDOR = "VENDOR"; //供应商
    public static final String BARCODE_TYPE_SN = "SN";

    /**
     * 单据的状态类型
     */
    public static final String ORDER_STATUS_PREPARE="PREPARE";
    public static final String ORDER_STATUS_CONFIRMED="CONFIRMED";
    public static final String ORDER_STATUS_APPROVING="APPROVING";
    public static final String ORDER_STATUS_APPROVED="APPROVED";
    public static final String ORDER_STATUS_FINISHED="FINISHED";

    /**
     * 库存事务类型
     */
    public static final String TRANSACTION_TYPE_ITEM_RECPT = "ITEM_RECPT"; //原材料接收入库
    public static final String TRANSACTION_TYPE_ITEM_RTV = "ITEM_RTV"; //原材料退回供应商

    public static final String TRANSACTION_TYPE_ITEM_ALLOCATED_OUT = "ITEM_ALLOCATED_OUT"; //生产调拨-出库事务
    public static final String TRANSACTION_TYPE_ITEM_ALLOCATED_IN = "ITEM_ALLOCATED_IN"; //生产调拨-入库事务

    public static final String TRANSACTION_TYPE_ITEM_ISSUE_OUT = "ITEM_ISSUE_OUT"; //生产领用-出库事务
    public static final String TRANSACTION_TYPE_ITEM_ISSUE_IN = "ITEM_ISSUE_IN"; //生产领用-入库事务

    public static final String TRANSACTION_TYPE_ITEM_REISSUE_OUT = "ITEM_ISSUE_OUT"; //生产撤销领用-出库事务
    public static final String TRANSACTION_TYPE_ITEM_REISSUE_IN = "ITEM_ISSUE_IN"; //生产撤销领用-入库事务

    public static final String TRANSACTION_TYPE_ITEM_RT_ISSUE_OUT = "ITEM_RT_ISSUE_OUT"; //生产退料-出库事务
    public static final String TRANSACTION_TYPE_ITEM_RT_ISSUE_IN = "ITEM_RT_ISSUE_IN"; //生产退料-入库事务

    public static final String TRANSACTION_TYPE_WAREHOUSE_TRANS_OUT = "TRANS_OUT"; //移库,移出
    public static final String TRANSACTION_TYPE_WAREHOUSE_TRANS_IN = "TRANS_IN"; //移库,移入

    public static final String TRANSACTION_TYPE_ITEM_CONSUME = "ITEM_CONSUME";//物料生产消耗
    public static final String TRANSACTION_TYPE_PRODUCT_PRODUCE = "PRODUCT_PRODUCE";//产品生产

    public static final String TRANSACTION_TYPE_PRODUCT_RECPT_OUT = "PRODUCT_RECPT_OUT"; //产品入库-出库事务
    public static final String TRANSACTION_TYPE_PRODUCT_RECPT_IN = "PRODUCT_RECPT_IN"; //产品入库-入库事务

    public static final String TRANSACTION_TYPE_PRODUCT_ISSUE = "PRODUCT_SALSE"; //销售出库
    public static final String TRANSACTION_TYPE_PRODUCT_RS = "PRODUCT_RT"; //销售退货


    /**
     * 默认线边库对应的仓库、库区、库位编码
     */
    public static  final String VIRTUAL_WH ="XBK_VIRTUAL";
    public static  final String VIRTUAL_WS ="XBKKQ_VIRTUAL";
    public static  final String VIRTUAL_WA ="XBKKW_VIRTUAL";

    /**
     * 各种业务单据的内定自动编码规则标识
     */
    public static final String ITEM_TYPE_CODE ="ITEM_TYPE_CODE";
    public static final String ITEM_CODE ="ITEM_CODE";
    public static final String MACHINERY_TYPE_CODE="MACHINERY_TYPE_CODE";
    public static final String TASK_CODE="TASK_CODE";
    public static final String DEFECT_CODE = "DEFECT_CODE";
    public static final String SN_CODE = "SN_CODE";
    public static final String TRANS_ORDER_CODE ="TRANS_ORDER_CODE";
    public static final String ITEMRECPT_CODE ="ITEMRECPT_CODE"; //物料采购入库
    public static final String WM_RTVENDOR_CODE ="WM_RTVENDOR_CODE";//退回供应商
    public static final String ISSUE_CODE ="ISSUE_CODE"; //生产领料
    public static final String RTISSUE_CODE ="RTISSUE_CODE"; //生产退料
    public static final String PRODUCTRECPT_CODE ="PRODUCTRECPT_CODE"; //产品入库
    public static final String PRODUCTSALSE_CODE ="PRODUCTSALSE_CODE"; //销售出库
    public static final String RTSALSE_CODE ="RTSALSE_CODE"; //销售退货
    public static final String TRANSFER_CODE ="TRANSFER_CODE"; //移库
    public static final String STOCKTAKING_CODE ="STOCKTAKING_CODE"; //盘库单
    public static final String FEEDBACK_CODE ="FEEDBACK_CODE"; //报工单

    /**
     * 仓库编码
     */
    public static final String WAREHOUSE_CODE = "WH166";
    public static final String LINE_EDGE_CODE = "WH165";

    /**
     * ERP企业编码
     */
    public static final String ERP_DEV_DODE = "32";
    public static final String ERP_PROD_DODE = "100";


}
