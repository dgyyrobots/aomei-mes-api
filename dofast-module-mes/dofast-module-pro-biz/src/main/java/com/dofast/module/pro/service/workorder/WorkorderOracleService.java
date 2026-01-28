package com.dofast.module.pro.service.workorder;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

public interface WorkorderOracleService {

    /**
     * 初始化工单信息
     */
    List<Map<String , Object>> initWorkorderInfo();

    /**
     * 初始化变更工单信息
     */
    List<Map<String , Object>> initChangeWorkorderInfo();

    /*
    * 获取所有的结案工单信息
    * */
    List<Map<String , Object>> initCloseWorkorderInfo();

}
