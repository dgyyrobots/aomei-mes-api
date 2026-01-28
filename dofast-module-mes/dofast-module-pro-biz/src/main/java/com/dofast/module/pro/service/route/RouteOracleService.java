package com.dofast.module.pro.service.route;

import java.util.List;
import java.util.Map;


public interface RouteOracleService {

    /**
     * 初始化工序信息
     * @return
     */
    List<Map<String, Object>> initRoute();

    /**
     * 初始化工序工艺绑定信息
     * @return
     */
    List<Map<String, Object>> initRouteBindProcess();

    /**
     * 初始化变更工单工序工艺绑定信息
     * @return
     */
    List<Map<String, Object>> initChangeWorkOrderRouteBindProcess(String workOrderNo);

    Map<String, Object> initWorkSequence(String workOrderNo, String processCode , String sequence);

}
