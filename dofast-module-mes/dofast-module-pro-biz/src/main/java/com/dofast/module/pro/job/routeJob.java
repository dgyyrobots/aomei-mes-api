package com.dofast.module.pro.job;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.module.mes.dal.dataobject.mditem.MdItemDO;
import com.dofast.module.mes.dal.mysql.mditem.MdItemMapper;
import com.dofast.module.pro.controller.admin.workorder.vo.WorkorderListVO;
import com.dofast.module.pro.dal.dataobject.process.ProcessDO;
import com.dofast.module.pro.dal.dataobject.route.RouteDO;
import com.dofast.module.pro.dal.dataobject.routeprocess.RouteProcessDO;
import com.dofast.module.pro.dal.dataobject.routeproduct.RouteProductDO;
import com.dofast.module.pro.dal.dataobject.workorder.WorkorderDO;
import com.dofast.module.pro.dal.mysql.process.ProcessMapper;
import com.dofast.module.pro.dal.mysql.route.RouteMapper;
import com.dofast.module.pro.dal.mysql.routeprocess.RouteProcessMapper;
import com.dofast.module.pro.dal.mysql.routeproduct.RouteProductMapper;
import com.dofast.module.pro.service.process.ProcessOracleService;
import com.dofast.module.pro.service.route.RouteOracleService;
import com.dofast.module.pro.service.workorder.WorkorderService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;

/**
 * 工艺路线与工序定时器
 */
@Component
public class routeJob implements JobHandler {

    @Resource
    private ProcessOracleService processOracleService;

    @Resource
    private ProcessMapper processMapper;

    @Resource
    private RouteOracleService routeOracleService;

    @Resource
    private RouteMapper routeMapper;

    @Resource
    private RouteProcessMapper routeProcessMapper;

    @Resource
    private RouteProductMapper routeProductMapper;

    @Resource
    private MdItemMapper mdItemMapper;

    @Resource
    private WorkorderService workorderService;




    @Override
    public String execute(String param) throws Exception {
        // 初始化工序信息
        List<Map<String, Object>> processList = processOracleService.initProcess();
        // 校验当前工序是否存在
        if (processList.isEmpty()) {
            return "未获取到工序信息!";
        }
        List<ProcessDO> addList = new ArrayList<>();
        List<ProcessDO> editList = new ArrayList<>();
        for (Map<String, Object> process : processList) {
            // 校验当前工序编号是否存在
            ProcessDO queryProcess = processMapper.selectOne(ProcessDO::getProcessCode, process.get("PROCESS_CODE"));
            if (queryProcess == null) {
                // 插入工序信息
                ProcessDO addProcess = new ProcessDO();
                addProcess.setProcessCode((String) process.get("PROCESS_CODE"));
                addProcess.setProcessName((String) process.get("PROCESS_NAME"));
                addProcess.setEnableFlag("Y");
                addList.add(addProcess);
            } else {
                // 变更工序信息
                queryProcess.setProcessName((String) process.get("PROCESS_NAME"));
                editList.add(queryProcess);
            }
        }
        if (!addList.isEmpty()) {
            processMapper.insertBatch(addList);
        }
        if (!editList.isEmpty()) {
            processMapper.updateBatch(editList);
        }

        // 开始初始化工艺信息
        List<Map<String, Object>> routeList = routeOracleService.initRoute();
        if (routeList.isEmpty()) {
            return "未获取到工艺信息!";
        }

        List<RouteDO> addRouteList = new ArrayList<>();
        List<RouteDO> editRouteList = new ArrayList<>();
        for (Map<String, Object> route : routeList) {
            // 获取所有的工艺信息
            String processCode = (String) route.get("PROCESS_CODE");
            String itemCode = (String) route.get("ITEM_CODE");
            String processDesc = (String) route.get("PROCESS_DESC");
            String routeCode = (String) route.get("ROUTE_CODE");
            // 校验当前工艺是否存在
            String uniRoute = itemCode + "-" + routeCode;
            RouteDO queryRoute = routeMapper.selectOne(RouteDO::getRouteCode, uniRoute);
            if (queryRoute == null) {
                // 插入工艺信息
                RouteDO addRoute = new RouteDO();
                addRoute.setRouteCode(uniRoute);
                addRoute.setRouteName(uniRoute);
                addRoute.setRouteDesc(processDesc);
                addRoute.setProductCode(itemCode);
                addRouteList.add(addRoute);
            } else {
                // 变更工艺信息
                queryRoute.setRouteName(uniRoute);
                queryRoute.setRouteDesc(processDesc);
                queryRoute.setProductCode(itemCode);
                editRouteList.add(queryRoute);
            }
        }
        if (!addRouteList.isEmpty()) {
            routeMapper.insertBatch(addRouteList);
        }
        if (!editRouteList.isEmpty()) {
            routeMapper.updateBatch(editRouteList);
        }
        // 开始添加工序与工艺关系
        List<Map<String, Object>> bindList = routeOracleService.initRouteBindProcess();
        if (bindList.isEmpty()) {
            return "未获取到工序与工艺关系!";
        }
        List<RouteProcessDO> addBindList = new ArrayList<>();
        List<RouteProcessDO> editBindList = new ArrayList<>();

        List<RouteProductDO> addProdcutList = new ArrayList<>();
        List<RouteProductDO> editProdcutList = new ArrayList<>();

        List<RouteProcessDO> editSequenceList = new ArrayList<>();


        // 创建映射，用于存储基于物料料号和版本号的工序列表
        Map<String, List<Map<String, Object>>> finProcessMap = new HashMap<>();

        for (Map<String, Object> bind : bindList) {
            // 校验当前工序与工艺关系是否存在
            String processCode = (String) bind.get("PROCESS_CODE");
            String itemCode = (String) bind.get("ITEM_CODE");
            String routeCode = (String) bind.get("ROUTE_CODE");
            String route = itemCode + "-" + routeCode;
            String topProcess = (String) bind.get("TOP_PROCESS_CODE");
            String nextProcess = (String) bind.get("NEXT_PROCESS_CODE");
            List<Map<String, Object>> finProcessList = finProcessMap.computeIfAbsent(route, k -> new ArrayList<>());
            finProcessList.add(bind);
        }

        // 构建工艺路线
        List<Map<String, Object>> finRouteList = new ArrayList<>();
        finProcessMap.forEach((key, value) -> {
                List<String> route = buildRoute(value);
                if (route != null && !route.isEmpty()) {
                    Map<String, Object> routeInfo = new HashMap<>();
                    routeInfo.put("routeCode", key);
                    routeInfo.put("routeLine", route);
                    List<Long> sequenceList = new ArrayList<>();

                    Map<String, Map<String, Object>> processRecordMap = new HashMap<>();
                    for (Map<String, Object> record : value) {
                        String procCode = (String) record.get("PROCESS_CODE");
                        processRecordMap.put(procCode, record);
                        BigDecimal sequence = new BigDecimal(record.get("SEQUENCE").toString());
                        sequenceList.add(sequence.longValue());
                    }
                    routeInfo.put("sequenceLine", sequenceList);
                    routeInfo.put("processRecordMap", processRecordMap);
                    finRouteList.add(routeInfo);
                }
        });

        // 构建工艺路线信息
        for (Map<String, Object> routeInfo : finRouteList) {
            List<String> route = (List<String>) routeInfo.get("routeLine");
            List<Long> sequenceList =  (List<Long>) routeInfo.get("sequenceLine");
            Map<String, Map<String, Object>> processRecordMap = (Map<String, Map<String, Object>>) routeInfo.get("processRecordMap");

            // 开始校验当前唯一标识码下的工艺路线是否存在
            for (int i = 0; i < route.size(); i++) {
                String processCode = route.get(i); // 当前工序编码
                String routeCode = (String) routeInfo.get("routeCode"); // 当前工艺编码
                Long sequence = sequenceList.get(i); // 当前工序顺序

                // 追加单位换算逻辑
                Map<String, Object> originalRecord = processRecordMap.get(processCode);
                String receiveingUnits = Optional.ofNullable((String) originalRecord.get("RECEIVING_UNITS")).orElse(null);
                BigDecimal receiveingUnitsConversionNumberator = originalRecord.get("RECEIVING_UNITS_CONVERSION_NUMERATOR") != null ?
                        new BigDecimal(String.valueOf(originalRecord.get("RECEIVING_UNITS_CONVERSION_NUMERATOR"))) : null;
                BigDecimal receiveingUnitsConversionDenominator = originalRecord.get("RECEIVING_UNITS_CONVERSION_DENOMINATOR") != null ?
                        new BigDecimal(String.valueOf(originalRecord.get("RECEIVING_UNITS_CONVERSION_DENOMINATOR"))) : null;

                String outUnits = Optional.ofNullable((String) originalRecord.get("OUT_UNITS")).orElse(null);
                BigDecimal outUnitsConversionNumberator = originalRecord.get("OUT_UNITS_CONVERSION_NUMERATOR") != null ?
                        new BigDecimal(String.valueOf(originalRecord.get("OUT_UNITS_CONVERSION_NUMERATOR"))) : null;
                BigDecimal outUnitsConversionDenominator = originalRecord.get("OUT_UNITS_CONVERSION_DENOMINATOR") != null ?
                        new BigDecimal(String.valueOf(originalRecord.get("OUT_UNITS_CONVERSION_DENOMINATOR"))) : null;

                String nextProcessCodeInRecord = null;
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
                    queryRouteProcess.setSequence(Long.valueOf(sequence));

                    if(receiveingUnits!=null){
                        queryRouteProcess.setReceivingUnits(receiveingUnits);
                        queryRouteProcess.setReceivingUnitsConversionNumerator(receiveingUnitsConversionNumberator);
                        queryRouteProcess.setReceivingUnitsConversionDenominator(receiveingUnitsConversionDenominator);
                    }
                    if(outUnits!=null){
                        queryRouteProcess.setOutUnits(outUnits);
                        queryRouteProcess.setOutUnitsConversionNumerator(outUnitsConversionNumberator);
                        queryRouteProcess.setOutUnitsConversionDenominator(outUnitsConversionDenominator);
                    }

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
                    addRouteProcess.setSequence(sequence);
                    if(receiveingUnits!=null){
                        addRouteProcess.setReceivingUnits(receiveingUnits);
                        addRouteProcess.setReceivingUnitsConversionNumerator(receiveingUnitsConversionNumberator);
                        addRouteProcess.setReceivingUnitsConversionDenominator(receiveingUnitsConversionDenominator);
                    }
                    if(outUnits!=null){
                        addRouteProcess.setOutUnits(outUnits);
                        addRouteProcess.setOutUnitsConversionNumerator(outUnitsConversionNumberator);
                        addRouteProcess.setOutUnitsConversionDenominator(outUnitsConversionDenominator);
                    }

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
        }
        for (Map<String, Object> route : routeList) {
            // 获取工艺信息
            String itemCode = (String) route.get("ITEM_CODE");
            String routeCode = (String) route.get("ROUTE_CODE");
            //  获取工艺信息
            String uniRoute = itemCode + "-" + routeCode;
            RouteDO queryRoute = routeMapper.selectOne(RouteDO::getRouteCode, uniRoute);
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
                editProdcutList.add(productInfo);
            } else {
                // 新增数据
                RouteProductDO addProductInfo = new RouteProductDO();
                addProductInfo.setRouteId(routeId);
                addProductInfo.setItemCode(itemCode);
                addProductInfo.setItemId(itemInfo.getId());
                addProductInfo.setItemName(itemInfo.getItemName());
                addProductInfo.setUnitOfMeasure(itemInfo.getUnitOfMeasure());
                addProdcutList.add(addProductInfo);
            }
        }
        if (!addBindList.isEmpty()) {
            routeProcessMapper.insertBatch(addBindList);
        }
        if (!editBindList.isEmpty()) {
            routeProcessMapper.updateBatch(editBindList);
        }
        if (!addProdcutList.isEmpty()) {
            routeProductMapper.insertBatch(addProdcutList);
        }
        if (!editProdcutList.isEmpty()) {
            routeProductMapper.updateBatch(editProdcutList);
        }

        // 开始追加工单工作序
        List<WorkorderDO> workorderList = workorderService.getWorkorderList(new WorkorderListVO());
        for(WorkorderDO workorderDO : workorderList){
           String fullRouteCode =  workorderDO.getProductCode() + "-" + workorderDO.getRouteCode();
           // 获取工艺路线
           RouteDO routeDO = routeMapper.selectOne(RouteDO::getRouteCode, fullRouteCode);
           if(routeDO != null){
               // 获取工艺路线工序
               List<RouteProcessDO> routeProcessDOList = routeProcessMapper.selectList(RouteProcessDO::getRouteId, routeDO.getId());
               if(!routeProcessDOList.isEmpty()){
                   for(RouteProcessDO routeProcess: routeProcessDOList){
                       // 获取工作序
                       Map<String, Object> workOderSequence = routeOracleService.initWorkSequence(workorderDO.getWorkorderCode() , routeProcess.getProcessCode());
                       if(workOderSequence == null){
                           continue;
                       }
                       String workorderSequence = (String) workOderSequence.get("WORKORDER_SEQUENCE");
                       routeProcess.setWorkorderSequence(Long.valueOf(workorderSequence));
                       editSequenceList.add(routeProcess);
                   }
               }
           }
        }
        // 追加工作序
        if(!editSequenceList.isEmpty()){
            routeProcessMapper.updateBatch(editSequenceList);
        }
        return "success";
    }

    /**
     * 构筑工艺路线
     */
    // 构建工艺路线的方法
    private static List<String> buildRoute(List<Map<String, Object>> records) {
       // 返回列表
        List<String> route = new ArrayList<>();
        // 若当前工序只有一条, 则仅返回当前的工序信息
        if (records.size() == 1) {
            route.add(records.get(0).get("PROCESS_CODE").toString());
            return route;
        }
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
