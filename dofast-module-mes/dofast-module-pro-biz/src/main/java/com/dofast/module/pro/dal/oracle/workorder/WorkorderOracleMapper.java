package com.dofast.module.pro.dal.oracle.workorder;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface WorkorderOracleMapper {

    // 初始化工单信息
    List<Map<String, Object>> initWorkorder(String erpCode);

    // 初始化变更工单信息
    List<Map<String, Object>> initChangeWorkorder(String erpCode);

    List<Map<String, Object>> initCloseWorkorder(String erpCode);
}
