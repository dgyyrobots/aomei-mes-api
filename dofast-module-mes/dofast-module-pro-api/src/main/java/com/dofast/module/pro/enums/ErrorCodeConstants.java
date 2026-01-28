package com.dofast.module.pro.enums;

import com.dofast.framework.common.exception.ErrorCode;

public interface ErrorCodeConstants {
    ErrorCode WORKORDER_BOM_NOT_EXISTS = new ErrorCode(710001, "生产工单BOM组成不存在");
    ErrorCode WORKORDER_NOT_EXISTS = new ErrorCode(710002, "生产工单不存在");
    ErrorCode WORKORDER_CODE_EXISTS = new ErrorCode(710003, "生产工单编号已存在");
    ErrorCode WORKORDER_NOT_DELETED = new ErrorCode(710004, "只能删除草稿状态单据");
    ErrorCode WORKORDER_NUMBER_MORE = new ErrorCode(710005, "工单生产数量超出生产计划");
    ErrorCode TASK_CHANGE_QUANTITY_ERROR = new ErrorCode(710005, "未查询到转换后的工单生产数量!");

    ErrorCode CESS_CONTENT_NOT_EXISTS = new ErrorCode(711001, "生产工序内容不存在");
    ErrorCode CESS_NOT_EXISTS = new ErrorCode(711002, "工序不存在");

    ErrorCode CESS_CODE_EXISTS = new ErrorCode(711002, "工序编码已存在");
    ErrorCode CESS_NAME_EXISTS = new ErrorCode(711003, "工序名称已存在");

    ErrorCode ROUTE_PRODUCT_BOM_NOT_EXISTS = new ErrorCode(712001, "产品制程物料BOM不存在");
    ErrorCode ROUTE_PRODUCT_BOM_HAS_IN_ROUTE = new ErrorCode(712002, "当前BOM物料在此工序已经配置过");
    ErrorCode ROUTE_PRODUCT_NOT_EXISTS = new ErrorCode(712003 , "产品制程不存在");
    ErrorCode ROUTE_PRODUCT_HAS_IN_ROUTE = new ErrorCode(712004 , "此产品已配置了工艺路线");
    ErrorCode ROUTE_PROCESS_NOT_EXISTS = new ErrorCode(712005, "工艺组成不存在");
    ErrorCode ROUTE_PRODUCT_NOT_EXISTS_PROCESS = new ErrorCode(712006, "当前产品未配置工艺路线");
    ErrorCode FEEDBACK_NOT_EXISTS = new ErrorCode(712007, "生产报工记录不存在");
    ErrorCode ROUTE_NOT_EXISTS = new ErrorCode(712009,"工艺路线不存在");
    ErrorCode ROUTE_CODE_EXISTS = new ErrorCode(712010, "工艺路线不存在");
    ErrorCode ROUTE_NOT_REPEAT = new ErrorCode(712011, "不能重复添加工序");
    ErrorCode ROUTE_PROCESS_HAS_KEY= new ErrorCode(712012, "当前工艺路线已经指定过关键工序");
    ErrorCode ROUTE_CODE_EXITS= new ErrorCode(712013, "工艺路线编号已存在");
    ErrorCode FEEDBACK_DELETE_NOT_LICENCE = new ErrorCode(712007, "当前报工记录不允许删除!");
    ErrorCode FEEDBACK_NEXT_PROCEESS_EXISTS = new ErrorCode(712007, "当前报工单存在下道制程, 无需打印合格证!");
    ErrorCode MERGE_FEEDBACK_NOT_AVALIBE = new ErrorCode(712007, "只允许合并ERP报工已同步的单据");
    ErrorCode IOT_FEEDBACK_NOT_EXISTS = new ErrorCode(712008, "数采报工产量记录不存在!");


    ErrorCode TASK_ISSUE_NOT_EXISTS = new ErrorCode(713001, "生产任务投料不存在");
    ErrorCode TASK_NOT_EXISTS = new ErrorCode(713002, "生产任务不存在");
    ErrorCode TASK_NUM_MORE_THAN_0 = new ErrorCode(713003, "排产数量必须大于0");
    ErrorCode TASK_NUM_MORE = new ErrorCode(713004, "工单生产数量超出生产任务");
    ErrorCode TASK_HAS_FINISHED = new ErrorCode(713005, "生产任务已经完成");

    ErrorCode TRANS_CONSUME_NOT_EXISTS = new ErrorCode(714001, "物料消耗记录不存在");
    ErrorCode TRANS_ORDER_NOT_EXISTS = new ErrorCode(714002, "流转单不存在");
    ErrorCode MD_WORKSTATION_NOT_EXISTS = new ErrorCode(416001, "工作站不存在");

    // === 上下工记录 715001 ===
    ErrorCode WORKRECORD_NOT_EXISTS = new ErrorCode(715001, "上下工记录不存在");

    ErrorCode FEEDBACK_NEED_SAVE_FIRST = new ErrorCode(716001, "请先保存单据");
    ErrorCode FEEDBACK_NUM_IS_ZERO = new ErrorCode(716002, "报工数量必须大于0");
    ErrorCode QUENTITYP_RODUCED_IS_MORE = new ErrorCode(716003, "无法重复报工，已超出下发生产数量");
    ErrorCode FEEDBACK_IS_REPEAT = new ErrorCode(716004, "请勿重复报工");
    ErrorCode FEEDBACK_NOT_ACQUIRE = new ErrorCode(716005, "当前订单不存在工单任务,请确认身份");
    ErrorCode FEEDBACK_NOT_APPROVED = new ErrorCode(715003, "报工未通过");
    ErrorCode WORKORDER_NOT_EXIST = new ErrorCode(716003, "一键报工的工单不存在");
    ErrorCode FEEDBACK_TASK_NOT_EXISTS = new ErrorCode(716003, "当前报工单未选择任务单！");
    ErrorCode FEEDBACK_NOT_SAME = new ErrorCode(716003, "请选择相同任务单下的报工单");
    ErrorCode FEEDBACK_ERP_ERROR = new ErrorCode(716003, "报工单接口调用异常!");
    ErrorCode WAREHOUSING_ERP_ERROR = new ErrorCode(716003, "完工入库接口调用异常!");

    ErrorCode MATERIALSTOCK_NOT_EXIST = new ErrorCode(716003, "完工入库接口调用异常!");

    ErrorCode FEEDBACK_TASK_STATUS_ERROR = new ErrorCode(716003, "报工单任务状态只能为已开工或已完工!");


    ErrorCode TASK_UPDATE_COUNT=new ErrorCode(717001,"更新生产任务的生产数量失败");

    ErrorCode WORKORER_UPDATE_COUNT=new ErrorCode(717002,"更新工单的生产数量失败");

    ErrorCode GET_WORKORER_HEAD=new ErrorCode(717003,"生成单据头信息失败");
    ErrorCode GET_WORKORER_ROW=new ErrorCode(717004,"生成单据行信息失败");

    ErrorCode UPDATE_INFO=new ErrorCode(717005,"更新物料的信息失败");

    ErrorCode UODATE_COME=new ErrorCode(717006,"更新物料入库单失败");

    ErrorCode STOCK_COUNT_UPDATE=new ErrorCode(717007,"更新库存失败");

    ErrorCode STACK_UPDATE_LOG=new ErrorCode(717008,"库存记录添加失败");

    ErrorCode PROUDT_PRODUCT_LOG=new ErrorCode(717009,"更新产品产出记录失败");

    ErrorCode PRODUCT_PEODUCT_ROW=new ErrorCode(717010,"产品产出记录表行添加失败");

    ErrorCode UPDATE_PROUDTC_STATUS=new ErrorCode(717011,"更新报工单的状态失败");

    ErrorCode COME_WORDORER=new ErrorCode(717012,"添加销售出库单失败");

    ErrorCode ADD_ROW=new ErrorCode(717013,"添加产品销售出库行失败");

    ErrorCode WORKORDER_IS_NOT=new ErrorCode(717014,"生产工单不存在");

    ErrorCode WORKORDER_BIG_COUNT=new ErrorCode(717015,"当前工单排产超出计划数量");

    ErrorCode TASK_IS_EXITS=new ErrorCode(717016,"当前用户没有任务");

    ErrorCode ADD_FEEDBACK_FAIL=new ErrorCode(717017,"报工记录创建失败");

    ErrorCode NOW_AFTER_MAXTIME=new ErrorCode(717017,"当前创建时间不能超过23.59.59");

    ErrorCode ANDON_RECORD_NOT_EXISTS = new ErrorCode(717018, "安灯呼叫记录不存在");

    ErrorCode FEEDBACK_MEMBER_NOT_EXISTS = new ErrorCode(717019, "报工班组人员不存在");

    ErrorCode MATERIAL_STOCK_NOT_EXISTS = new ErrorCode(717020, "单据对应库存不存在");

    ErrorCode MATERIAL_STOCK_NOT_RECEPT = new ErrorCode(717020, "存在未入库单据, 请先入库!");

    ErrorCode ISSUE_NOT_EXISTS = new ErrorCode(717020, "单据对应领料单不存在");

    ErrorCode TASK_NOT_RECEPT = new ErrorCode(717020, "任务单对应领料单存在未上料单据, 请先上料!");

    ErrorCode PRODUCT_PRODUCE_NOT_EXISTS = new ErrorCode(717021, "产品产出记录不存在");

    ErrorCode ITEM_CONSUME_NOT_EXISTS = new ErrorCode(717022, "物料消耗记录不存在");

    ErrorCode CESS_DEFECT_NOT_EXISTS = new ErrorCode(717023, "工序异常缺陷名称不存在");

    ErrorCode FEEDBACK_DEFECT_NOT_EXISTS = new ErrorCode(717024, "报工缺陷不存在");

    ErrorCode USER_NOT_CONFIG_POSTIDS = new ErrorCode(717025, "当前用户未配置岗位");

    ErrorCode USER_NOT_EXIST = new ErrorCode(717025, "用户不存在或未启用!");

    ErrorCode USER_EXIST = new ErrorCode(717025, "当前班组已存在该用户!");

    ErrorCode USER_NOT_CONFIG_ROLES = new ErrorCode(717025, "当前用户未配置角色");

    ErrorCode ROUTE_PROCESS_HAS_DOWN = new ErrorCode(717026, "当前报工单存在下道工序, 无需调用ERP完工入库!");

    ErrorCode INIT_ERP_PARAM_ERROR = new ErrorCode(717027, "初始化ERP参数异常!");

    ErrorCode FEEDBACK_WAREHOUSING_LOG_NOT_EXISTS = new ErrorCode(717028, "报工入库日志不存在");

    ErrorCode FEEDBACK_AUDIT_NOT_EXISTS = new ErrorCode(717029, "报工审批主表不存在");

    ErrorCode FEEDBACK_AUDIT_ITEM_NOT_EXISTS = new ErrorCode(717030, "报工审批汇总不存在");

    ErrorCode FEEDBACK_AUDIT_DETAIL_NOT_EXISTS = new ErrorCode(717031, "报工审批明细不存在");

    ErrorCode MACHINERY_NOT_EXISTS = new ErrorCode(717031, "报工设备不存在!");

    ErrorCode BATCH_CODE_NOT_NULL = new ErrorCode(717031, "批次号不存在!");

    ErrorCode FEEDBACK_AUDIT_DETAIL_NOT_BELONG = new ErrorCode(717032, "明细不属于当前审批单");

    ErrorCode NO_NEED_ERP = new ErrorCode(717033, "无需调用ERP接口");

    ErrorCode ERP_WAREHOUSING_EXISTS = new ErrorCode(717034, "存在ERP入库单据, 无法撤销");

    ErrorCode INVENTORY_NOT_EXISTS = new ErrorCode(717035, "库存不存在");

    ErrorCode INVENTORY_WAREHOUSING_EXISTS = new ErrorCode(717036, "当前单据已入库, 请先调用撤销入库方法");

    ErrorCode TIME_NOT_COMPLETE = new ErrorCode(717037, "请填写完整的时间");

    ErrorCode LOCK_FAIL = new ErrorCode(717038, "当前任务单有他人正进行报工, 请稍后尝试");

    ErrorCode BATCH_CODE_DUPLICATE = new ErrorCode(717039, "批次号重复");

}
