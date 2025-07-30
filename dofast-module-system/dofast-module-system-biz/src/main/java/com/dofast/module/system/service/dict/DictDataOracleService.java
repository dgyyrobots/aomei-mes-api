package com.dofast.module.system.service.dict;

import java.util.List;
import java.util.Map;


public interface DictDataOracleService {

    // 获取ERP仓退原因码
    List<Map<String, Object>> initWarehouseReasonData();

    // 获取ERP单据类型
    List<Map<String, Object>> initDocType();

    // 获取ERP关账日期
    Map<String, Object> initCloseDate();
}
