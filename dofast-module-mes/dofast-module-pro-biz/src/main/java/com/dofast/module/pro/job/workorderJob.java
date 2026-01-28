package com.dofast.module.pro.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.dataobject.mdproductbom.MdProductBomDO;
import com.dofast.module.mes.dal.mysql.mditem.MdItemMapper;
import com.dofast.module.mes.dal.mysql.mdproductbom.MdProductBomMapper;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.dal.dataobject.workorderbom.WorkorderBomDO;
import com.dofast.module.pro.dal.mysql.route.RouteMapper;
import com.dofast.module.pro.dal.mysql.workorder.WorkorderMapper;
import com.dofast.module.pro.dal.mysql.workorderbom.WorkorderBomMapper;
import com.dofast.module.pro.service.workorder.WorkorderOracleService;
import com.dofast.module.pro.service.workorderbom.WorkorderBomOracleService;
import com.dofast.module.wms.dal.dataobject.storagelocation.StorageLocationDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class workorderJob implements JobHandler {

    @Resource
    private WorkorderOracleService workorderOracleService;

    @Resource
    private WorkorderBomOracleService workorderBomOracleService;

    @Resource
    private WorkorderMapper workorderMapper;

    @Resource
    private MdItemMapper itemMapper;

    @Resource
    private WorkorderBomMapper workorderBomMapper;

    @Resource
    private RouteMapper routeMapper;

    @Override
    public String execute(String param) throws Exception {
        // 初始化工单信息
        List<Map<String, Object>> workOrderList = workorderOracleService.initWorkorderInfo();
        if (workOrderList.isEmpty()) {
            return "未获取到工单信息!";
        }
        List<WorkorderDO> addWorkorder = new ArrayList<>(); // 新增工单信息
        List<WorkorderDO> updateWorkorder = new ArrayList<>(); // 更新工单信息

        for (Map<String, Object> workOrder : workOrderList) {
            String workorderCode = (String) workOrder.get("WORKORDER_CODE"); // 工单号
            String productCode = (String) workOrder.get("PRODUCT_CODE"); // 产品编号
            String routeCode = (String) workOrder.get("ROUTE_CODE"); // 工艺路线编号

            // 校验当前工单信息是否存在
            WorkorderDO query = workorderMapper.selectOne(WorkorderDO::getWorkorderCode, workorderCode);

            // 根据产品编号-工艺编号确认工艺路线
            // 根据工艺路线获取对应的工艺附件
            String finRouteCode = productCode + "-" + routeCode; // 工艺路线编号
            RouteDO queryRoute = Optional.ofNullable(routeMapper.selectOne(RouteDO::getRouteCode, finRouteCode)).orElse(null);
            String file = null; // 工艺附件
            if (queryRoute != null) {
                file = queryRoute.getFile();
            }

            if(file!=null){
                 System.out.println("工艺路线附件: " + file);
            }
            if (query == null) {
                // 新增工单信息
                WorkorderDO workorderDO = new WorkorderDO();
                workorderDO.setWorkorderCode(workorderCode);
                workorderDO.setWorkorderName(workorderCode);
                workorderDO.setOrderSource((String) workOrder.get("ORDER_SOURCE")); //来源信息-已修改数据字典
                workorderDO.setClientCode((String) workOrder.get("CLIENT_CODE")); // 客户编号
                workorderDO.setProductCode(productCode); // 产品编号
                workorderDO.setRouteCode(routeCode); // 工艺路线编号
                workorderDO.setStatus("CONFIRMED"); // 工单状态-ERP获取的数据都不允许修改
                // 基于产品编号获取详情
                MdItemDO itemDO = itemMapper.selectOne(MdItemDO::getItemCode, productCode);
                if (itemDO != null) {
                    workorderDO.setProductId(itemDO.getId()); // 产品ID
                    workorderDO.setProductName(Optional.ofNullable(itemDO.getItemName()).orElse("")); // 产品名称
                    workorderDO.setProductSpc(Optional.ofNullable(itemDO.getSpecification()).orElse("")); // 产品类型
                }
                BigDecimal quantity = (BigDecimal) workOrder.get("QUANTITY");
                if (quantity == null) {
                    continue;
                }
                workorderDO.setQuantity(quantity.doubleValue()); // 数量
                workorderDO.setUnitOfMeasure((String) workOrder.get("UNIT_OF_MEASURE")); // 单位
                Timestamp timestamp = (Timestamp) workOrder.get("REQUEST_DATE");
                workorderDO.setRequestDate(timestamp.toLocalDateTime()); // 需求日期
                workorderDO.setStatus("CONFIRMED"); // 工单状态-ERP获取的数据都不允许修改
                workorderDO.setSourceCode(Optional.ofNullable((String) workOrder.get("SOURCE_CODE")).orElse("")); // 来源单据
                if (file != null) {
                    workorderDO.setAdjuncts(file); // 工艺附件
                }

                addWorkorder.add(workorderDO);
            } else {
                // 更新工单信息
                // 校验工单Adjuncts字段是否包含了当前的附件
                if (file != null) {
                    if (query.getAdjuncts() != null && !query.getAdjuncts().contains(file)) {
                        query.setAdjuncts(query.getAdjuncts() + "," + file);
                    }else if(StringUtils.isBlank(query.getAdjuncts())){
                        query.setAdjuncts(file);
                    }
                }
                query.setWorkorderCode(workorderCode);
                query.setOrderSource((String) workOrder.get("ORDER_SOURCE")); //来源信息-已修改数据字典
                query.setClientCode((String) workOrder.get("CLIENT_CODE")); // 客户编号
                query.setProductCode(productCode); // 产品编号
                query.setRouteCode(routeCode); // 工艺路线编号
                // 基于产品编号获取详情
                MdItemDO itemDO = itemMapper.selectOne(MdItemDO::getItemCode, productCode);
                if (itemDO != null) {
                    query.setProductId(itemDO.getId()); // 产品ID
                    query.setProductName(Optional.ofNullable(itemDO.getItemName()).orElse("")); // 产品名称
                    query.setProductSpc(Optional.ofNullable(itemDO.getSpecification()).orElse("")); // 产品类型
                }
                BigDecimal quantity = (BigDecimal) workOrder.get("QUANTITY");
                query.setQuantity(quantity.doubleValue()); // 数量
                query.setUnitOfMeasure((String) workOrder.get("UNIT_OF_MEASURE")); // 单位
                Timestamp timestamp = (Timestamp) workOrder.get("REQUEST_DATE");
                query.setRequestDate(timestamp.toLocalDateTime()); // 需求日期
                query.setSourceCode(Optional.ofNullable((String) workOrder.get("SOURCE_CODE")).orElse("")); // 来源单据

                updateWorkorder.add(query);
            }
        }
        if (!addWorkorder.isEmpty()) {
            workorderMapper.insertBatch(addWorkorder);
        }
        if (!updateWorkorder.isEmpty()) {
            workorderMapper.updateBatch(updateWorkorder);
        }

        // 初始化工单BOM信息
        List<Map<String, Object>> workOrderBomList = workorderBomOracleService.initWorkorderBom();
        if (workOrderBomList.isEmpty()) {
            return "未获取到工单BOM信息!";
        }
        List<WorkorderBomDO> addWorkorderBom = new ArrayList<>(); // 新增工单BOM信息
        List<WorkorderBomDO> updateWorkorderBom = new ArrayList<>(); // 更新工单BOM信息

        for (Map<String, Object> workOrderBom : workOrderBomList) {
            String workorderNo = (String) workOrderBom.get("WORKORDER_NO"); // 工单号
            String bomCode = (String) workOrderBom.get("ITEM_CODE"); // BOM编号

            // 基于工单号获取工单ID
            WorkorderDO queryWorkorderId = workorderMapper.selectOne(WorkorderDO::getWorkorderCode, workorderNo);
            if (queryWorkorderId == null) {
                continue;
            }
            // 校验当前工单BOM信息是否存在
            WorkorderBomDO queryBom = workorderBomMapper.selectOne(WorkorderBomDO::getWorkorderId, queryWorkorderId.getId(), WorkorderBomDO::getItemCode, bomCode);
            if (queryBom == null) {
                // 新增工单BOM信息
                WorkorderBomDO workorderBomDO = new WorkorderBomDO();
                workorderBomDO.setWorkorderId(queryWorkorderId.getId()); // 工单ID
                workorderBomDO.setItemCode(bomCode); // BOM料号
                // 根据当前物料料号获取物料ID
                MdItemDO itemDO = itemMapper.selectOne(MdItemDO::getItemCode, bomCode);
                if (itemDO == null) {
                    continue;
                }
                workorderBomDO.setItemId(itemDO.getId()); // 物料ID
                workorderBomDO.setItemName(Optional.ofNullable((String) workOrderBom.get("ITEM_NAME")).orElse("")); // BOM名称
                workorderBomDO.setItemSpc(Optional.ofNullable((String) workOrderBom.get("ITEM_SPEC")).orElse("")); // BOM版本
                workorderBomDO.setUnitOfMeasure(Optional.ofNullable((String) workOrderBom.get("UNIT_OF_MEASURE")).orElse("")); // 单位
                BigDecimal bigDecimalValue = (BigDecimal) workOrderBom.get("QUANTITY");
                double doubleValue = bigDecimalValue.doubleValue();
                workorderBomDO.setQuantity(doubleValue);
                workorderBomDO.setItemOrProduct("ITEM");
                BigDecimal sequence = new BigDecimal(String.valueOf(workOrderBom.get("SEQUENCE")));
                BigDecimal sequenceOrder = new BigDecimal(String.valueOf(workOrderBom.get("SEQUENCE_ORDER")));
                workorderBomDO.setSequence(sequence.longValue()); // 项次
                workorderBomDO.setSequenceOrder(sequenceOrder.longValue()); // 项序
                addWorkorderBom.add(workorderBomDO);
            } else {
                queryBom.setItemName(Optional.ofNullable((String) workOrderBom.get("ITEM_NAME")).orElse("")); // BOM名称
                queryBom.setItemSpc(Optional.ofNullable((String) workOrderBom.get("ITEM_SPEC")).orElse("")); // BOM版本
                queryBom.setUnitOfMeasure(Optional.ofNullable((String) workOrderBom.get("UNIT_OF_MEASURE")).orElse("")); // 单位
                BigDecimal bigDecimalValue = (BigDecimal) workOrderBom.get("QUANTITY");
                double doubleValue = bigDecimalValue.doubleValue();
                queryBom.setQuantity(doubleValue);
                BigDecimal sequence = new BigDecimal(String.valueOf(workOrderBom.get("SEQUENCE")));
                BigDecimal sequenceOrder = new BigDecimal(String.valueOf(workOrderBom.get("SEQUENCE_ORDER")));
                queryBom.setSequence(sequence.longValue()); // 项次
                queryBom.setSequenceOrder(sequenceOrder.longValue()); // 项序
                updateWorkorderBom.add(queryBom);
            }
        }
        if (!addWorkorderBom.isEmpty()) {
            workorderBomMapper.insertBatch(addWorkorderBom);
        }
        if (!updateWorkorderBom.isEmpty()) {
            workorderBomMapper.updateBatch(updateWorkorderBom);
        }
        return "成功!";

        // 开始初始化变更工单BOM信息


    }
}
