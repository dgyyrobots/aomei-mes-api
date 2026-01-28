package com.dofast.module.pro.service.route;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.dal.oracle.route.RouteOracleMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Validated
@DS("oracle")
public class RouteOracleServiceImpl implements RouteOracleService {

    @Resource
    private RouteOracleMapper routeOracleMapper;

    /**
     * 初始化工艺信息
     * @return
     */
    @Override
    public List<Map<String, Object>> initRoute(){
        String erpCode = Constant.ERP_PROD_DODE;
        return routeOracleMapper.initRoute(erpCode);
    }


    /**
     * 初始化工序工艺绑定信息
     * @return
     */
    @Override
    @DS("oracle")
    public List<Map<String, Object>> initRouteBindProcess(){
        String erpCode = Constant.ERP_PROD_DODE;
        return routeOracleMapper.initRouteBindProcess(erpCode);
    }

    /**
     * 初始化变更工单工序工艺绑定信息
     * @return
     */
    @Override
    public List<Map<String, Object>> initChangeWorkOrderRouteBindProcess(String workOrderNo){
        String erpCode = Constant.ERP_PROD_DODE;
        return routeOracleMapper.initChangeWorkOrderRouteBindProcess(workOrderNo, erpCode);
    }

    @Override
    public Map<String, Object> initWorkSequence(String workOrderNo, String processCode , String sequence){
        String erpCode = Constant.ERP_PROD_DODE;
        return routeOracleMapper.initWorkSequence(workOrderNo, processCode, sequence , erpCode);
    }


}
