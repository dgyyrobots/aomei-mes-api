package com.dofast.module.pro.dal.oracle.route;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface RouteOracleMapper {

    List<Map<String, Object>> initRoute(String erpCode);


    List<Map<String, Object>> initRouteBindProcess(String erpCode);

    List<Map<String, Object>> initChangeWorkOrderRouteBindProcess(@Param("workOrderNo") String workOrderNo, @Param("erpCode") String erpCode);

    Map<String, Object> initWorkSequence(@Param("workOrderNo") String workOrderNo, @Param("processCode") String processCode , @Param("sequence") String sequence , @Param("erpCode") String erpCode);

}
