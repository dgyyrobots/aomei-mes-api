package com.dofast.module.pro.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.mysql.mditem.MdItemMapper;
import com.dofast.module.pro.dal.dataobject.process.ProcessDO;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import com.dofast.module.pro.dal.dataobject.routeproduct.RouteProductDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.dal.dataobject.workorderbom.WorkorderBomDO;
import com.dofast.module.pro.dal.mysql.process.ProcessMapper;
import com.dofast.module.pro.dal.mysql.route.RouteMapper;
import com.dofast.module.pro.dal.mysql.routeprocess.RouteProcessMapper;
import com.dofast.module.pro.dal.mysql.routeproduct.RouteProductMapper;
import com.dofast.module.pro.dal.mysql.workorder.WorkorderMapper;
import com.dofast.module.pro.dal.mysql.workorderbom.WorkorderBomMapper;
import com.dofast.module.pro.service.route.RouteOracleService;
import com.dofast.module.pro.service.workorder.WorkorderOracleService;
import com.dofast.module.pro.service.workorderbom.WorkorderBomOracleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


@Component
public class changeOrderJob implements JobHandler {

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
    private RouteOracleService routeOracleService;

    @Resource
    private RouteMapper routeMapper;

    @Resource
    private RouteProcessMapper routeProcessMapper;


    @Resource
    private RouteProductMapper routeProductMapper;

    @Resource
    private ProcessMapper processMapper;

    @Resource
    private MdItemMapper mdItemMapper;

    @Override
    public String execute(String param) throws Exception {
        // 初始化变更工单信息
        List<Map<String, Object>> workOrderList = workorderOracleService.initChangeWorkorderInfo();
        if (workOrderList.isEmpty()) {
            return "未获取到变更工单信息!";
        }

        List<String> workorderNoList = new ArrayList<>(); // 工单号列表
        List<WorkorderDO> updateWorkorder = new ArrayList<>(); // 更新工单信息

        for (Map<String, Object> workOrder : workOrderList) {
            String workorderCode = (String) workOrder.get("WORKORDER_CODE"); // 工单号
            workorderNoList.add(workorderCode);
            String oldWorkorderCode = (String) workOrder.get("OLD_WORKORDER_CODE"); // 来源工单号
            String productCode = (String) workOrder.get("PRODUCT_CODE"); // 产品编号
            String routeCode = (String) workOrder.get("WORKORDER_CODE"); // 工艺路线-变更工单的工艺路线仅供当前工单使用, 故而填写当前工单号即可
            // 校验当前工单是否存在
            WorkorderDO query = workorderMapper.selectOne(WorkorderDO::getWorkorderCode, workorderCode);
            if (query == null) {
                return "变更工单号不存在!工单号: " + workorderCode;
            } else {
                // 更新工单信息
                query.setOrderSource((String) workOrder.get("ORDER_SOURCE")); //来源信息-已修改数据字典
                query.setClientCode((String) workOrder.get("CLIENT_CODE")); // 客户编号
                query.setProductCode(productCode); // 产品编号
                query.setStatus("CONFIRMED"); // 工单状态-ERP获取的数据都不允许修改
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
                updateWorkorder.add(query);
            }
        }

        if (!updateWorkorder.isEmpty()) {
            workorderMapper.updateBatch(updateWorkorder);
        }

        // 更新变更工单BOM信息
        List<WorkorderBomDO> updateWorkorderBom = new ArrayList<>(); // 更新工单BOM信息
        for (String workorder : workorderNoList) {
            List<Map<String, Object>> workOrderBomList = workorderBomOracleService.initChangeWorkorderBom(workorder);
            if (workOrderBomList.isEmpty()) {
                return "未获取到变更工单BOM信息!";
            }
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
                    return "变更工单BOM信息不存在!工单号: " + workorderNo + ", BOM编号: " + bomCode;
                } else {
                    queryBom.setItemName(Optional.ofNullable((String) workOrderBom.get("ITEM_NAME")).orElse("")); // BOM名称
                    queryBom.setItemSpc(Optional.ofNullable((String) workOrderBom.get("ITEM_SPEC")).orElse("")); // BOM版本
                    queryBom.setUnitOfMeasure(Optional.ofNullable((String) workOrderBom.get("UNIT_OF_MEASURE")).orElse("")); // 单位
                    BigDecimal bigDecimalValue = (BigDecimal) workOrderBom.get("QUANTITY");
                    double doubleValue = bigDecimalValue.doubleValue();
                    queryBom.setQuantity(doubleValue);
                    updateWorkorderBom.add(queryBom);
                }
            }
        }
        if (!updateWorkorderBom.isEmpty()) {
            workorderBomMapper.updateBatch(updateWorkorderBom);
        }

        // 开始绑定变更工单的工艺路线信息
        List<RouteDO> addRouteList = new ArrayList<>(); // 新增工艺路线
        for (String workorder : workorderNoList) { // 便利所有工单信息

            // 基于变更工单号获取新的工艺路线, 并修改变更工单的工艺编码
            // 工艺编码视为当前物料料号
            List<Map<String, Object>> workOrderRouteList = routeOracleService.initChangeWorkOrderRouteBindProcess(workorder);
            if (workOrderRouteList.isEmpty()) {
                System.out.println("未获取到变更工单的工艺路线信息!");
                continue;
            }

            List<RouteProcessDO> addBindList = new ArrayList<>();
            List<RouteProcessDO> editBindList = new ArrayList<>();
            List<RouteProductDO> editProdcutList = new ArrayList<>();

            // 创建映射，用于存储基于物料料号和版本号的工序列表
            Map<String, List<Map<String, Object>>> finProcessMap = new HashMap<>();
            // 存在数据
            for (int i = 0; i < workOrderRouteList.size(); i++) {
                Map<String, Object> workOrderRoute = workOrderRouteList.get(i);
                String itemCode = (String) workOrderRoute.get("ITEM_CODE"); // 物料料号
                String workorderNo = (String) workOrderRoute.get("WORKORDER_NO"); // 工单号
                String processCode = (String) workOrderRoute.get("PROCESS_CODE"); // 当前工序编码
                String topProcessCode = (String) workOrderRoute.get("TOP_PROCESS_CODE"); // 当前工序编码
                String nextProcessCode = (String) workOrderRoute.get("NEXT_PROCESS_CODE"); // 当前工序编码
                String uniRoute = itemCode + "-" + workorderNo; // 当前工艺仅给当前工单使用, 故工单号作为唯一标识
                // 构筑工艺路线
                List<Map<String, Object>> finProcessList = finProcessMap.computeIfAbsent(uniRoute, k -> new ArrayList<>());
                finProcessList.add(workOrderRoute);
            }

            // 添加route表
            // 插入工艺信息
           /* RouteDO addRoute = new RouteDO();
            addRoute.setRouteCode(uniRoute);
            addRoute.setRouteName(uniRoute);
            addRoute.setProductCode(itemCode);
            addRouteList.add(addRoute);
*/
            if (!addRouteList.isEmpty()) {
                routeMapper.insertBatch(addRouteList);
            }

            // 构建工艺路线
            List<Map<String, Object>> finRouteList = new ArrayList<>();
            finProcessMap.forEach((key, value) -> {
                //if (value.size() > 0) {
                List<String> route = buildRoute(value);
                if (route != null && !route.isEmpty()) {
                    Map<String, Object> routeInfo = new HashMap<>();
                    routeInfo.put("routeCode", key);
                    routeInfo.put("routeLine", route);
                    finRouteList.add(routeInfo);
                }
            });

            // 构建工艺路线信息
            for (Map<String, Object> routeInfo : finRouteList) {
                List<String> route = (List<String>) routeInfo.get("routeLine");

                // 校验当前工艺唯一标识是否存在
                RouteDO updateRoute = Optional.ofNullable(routeMapper.selectOne(RouteDO::getRouteCode, routeInfo.get("routeCode"))).orElse(null);
                if (updateRoute == null) {
                    // 开始新增工艺主表信息
                    RouteDO addRoute = new RouteDO();
                    String routeCode = (String) routeInfo.get("routeCode");
                    String itemCode = routeCode.split("-")[0];

                    int index = routeCode.indexOf('-');
                    String workorderCode = routeCode.substring(index + 1);
                    //String workorderCode= routeCode.split("-")[1];
                    addRoute.setRouteCode(routeCode);
                    addRoute.setRouteName(routeCode);
                    addRoute.setProductCode(itemCode);
                    routeMapper.insert(addRoute);

                    // 开始修改工单主表绑定的工艺编码
                    WorkorderDO queryWorkorder = workorderMapper.selectOne(WorkorderDO::getWorkorderCode, workorderCode);
                    if (queryWorkorder != null) {
                        queryWorkorder.setRouteCode(queryWorkorder.getWorkorderCode());
                        workorderMapper.updateById(queryWorkorder);
                    }

                    // 追加当前工艺-产品关系表
                    RouteDO queryRoute = routeMapper.selectOne(RouteDO::getRouteCode, routeCode);
                    Long routeId = queryRoute.getId();
                    // 校验当前工艺产品绑定关系是否存在
                    // 获取物料详情
                    MdItemDO itemInfo = mdItemMapper.selectOne(MdItemDO::getItemCode, itemCode);
                    RouteProductDO productInfo = routeProductMapper.selectOne(RouteProductDO::getRouteId, queryRoute.getId(), RouteProductDO::getItemCode, itemCode);
                    if (productInfo != null) {
                        // 存在开始更新
                        productInfo.setRouteId(routeId);
                        productInfo.setItemCode(itemCode);
                        productInfo.setItemId(itemInfo.getId());
                        productInfo.setItemName(itemInfo.getItemName());
                        productInfo.setUnitOfMeasure(itemInfo.getUnitOfMeasure());
                        routeProductMapper.updateById(productInfo);
                    } else {
                        // 新增数据
                        RouteProductDO addProductInfo = new RouteProductDO();
                        addProductInfo.setRouteId(routeId);
                        addProductInfo.setItemCode(itemCode);
                        addProductInfo.setItemId(itemInfo.getId());
                        addProductInfo.setItemName(itemInfo.getItemName());
                        addProductInfo.setUnitOfMeasure(itemInfo.getUnitOfMeasure());
                        routeProductMapper.insert(addProductInfo);
                    }
                }

                // 开始校验当前唯一标识码下的工艺路线是否存在
                for (int i = 0; i < route.size(); i++) {
                    String processCode = route.get(i); // 当前工序编码
                    String routeCode = (String) routeInfo.get("routeCode"); // 当前工艺编码
                    // 根据工艺编码获取工艺Id
                    Long routeId = Optional.ofNullable(routeMapper.selectOne(RouteDO::getRouteCode, routeCode).getId()).orElse(0L);
                    if (routeId == 0L) {
                        continue;
                    }
                    // 根据工序编码获取当前工序信息
                    ProcessDO process = processMapper.selectOne(ProcessDO::getProcessCode, processCode);
                    if (process == null) {
                        // 工序不存在
                        return "未获取到当前工序信息";
                    }
                    ProcessDO nextProcess = null;
                    if (i + 1 != route.size()) {
                        // 不是最后一个工序，获取下一个工序编码
                        String nextProcessCode = route.get(i + 1);
                        // 根据下一个工序编码获取下一个工序信息
                        nextProcess = processMapper.selectOne(ProcessDO::getProcessCode, nextProcessCode);
                        if (nextProcess == null) {
                            // 下一个工序不存在
                            return "未获取到下一个工序信息";
                        }
                    }
                    // 校验当前工艺工序绑定关系是否存在
                    RouteProcessDO queryRouteProcess = routeProcessMapper.selectOne(RouteProcessDO::getRouteId, routeId, RouteProcessDO::getProcessCode, process.getProcessCode());
                    if (queryRouteProcess != null) {
                        // 存在开始更新
                        queryRouteProcess.setProcessName(process.getProcessName());
                        queryRouteProcess.setProcessId(process.getId());
                        queryRouteProcess.setOrderNum(i + 1);
                        queryRouteProcess.setLinkType("FS");
                        queryRouteProcess.setColorCode("#00AEF3");
                        if (nextProcess != null) {
                            queryRouteProcess.setNextProcessId(nextProcess.getId());
                            queryRouteProcess.setNextProcessCode(nextProcess.getProcessCode());
                            queryRouteProcess.setNextProcessName(nextProcess.getProcessName());
                        } else {
                            queryRouteProcess.setNextProcessId(0L);
                            queryRouteProcess.setNextProcessName("无");
                        }
                        editBindList.add(queryRouteProcess);
                    } else {
                        // 新增数据
                        RouteProcessDO addRouteProcess = new RouteProcessDO();
                        addRouteProcess.setRouteId(routeId);
                        addRouteProcess.setProcessCode(process.getProcessCode());
                        addRouteProcess.setProcessName(process.getProcessName());
                        addRouteProcess.setProcessId(process.getId());
                        addRouteProcess.setOrderNum(i + 1);
                        addRouteProcess.setLinkType("FS");
                        addRouteProcess.setColorCode("#00AEF3");
                        if (nextProcess != null) {
                            addRouteProcess.setNextProcessId(nextProcess.getId());
                            addRouteProcess.setNextProcessCode(nextProcess.getProcessCode());
                            addRouteProcess.setNextProcessName(nextProcess.getProcessName());
                        } else {
                            addRouteProcess.setNextProcessId(0L);
                            addRouteProcess.setNextProcessName("无");
                        }
                        addBindList.add(addRouteProcess);
                    }
                }

                // addBindList
                if (!addBindList.isEmpty()) {
                    routeProcessMapper.insertBatch(addBindList);
                }
                if (!editBindList.isEmpty()) {
                    routeProcessMapper.updateBatch(editBindList);
                }

            }
        }
        return "success";
    }

    /**
     * 构筑工艺路线
     */
    // 构建工艺路线的方法
    private static List<String> buildRoute(List<Map<String, Object>> records) {
        // 找到起始工序
        String currentProcessCode = null;
        for (Map<String, Object> record : records) {
            if ("INIT".equals(record.get("TOP_PROCESS_CODE"))) {
                currentProcessCode = (String) record.get("PROCESS_CODE"); // 获取起始工序
                break;
            }
        }
        // 如果没有找到起始工序，返回null
        if (currentProcessCode == null) {
            return null;
        }
        List<String> route = new ArrayList<>();
        route.add(currentProcessCode); // 添加起始工序到路线
        String nextProcessCode = currentProcessCode;
        // 循环直到找到END工序
        do {
            nextProcessCode = null;
            for (Map<String, Object> record : records) {
                if ("END".equals(record.get("NEXT_PROCESS_CODE"))) {
                    // 找到END工序，结束循环
                    route.add(record.get("PROCESS_CODE").toString());
                    break;
                }
                if (record.get("TOP_PROCESS_CODE").equals(currentProcessCode) && !route.contains(record.get("PROCESS_CODE"))) {
                    nextProcessCode = (String) record.get("PROCESS_CODE");
                    route.add(nextProcessCode);
                    break;
                }
            }
        } while (nextProcessCode != null); // 继续循环直到没有找到下一个工序

        return route;
    }

}
