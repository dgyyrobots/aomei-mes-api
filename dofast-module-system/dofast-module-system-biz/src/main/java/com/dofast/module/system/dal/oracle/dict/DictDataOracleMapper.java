package com.dofast.module.system.dal.oracle.dict;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface DictDataOracleMapper {

    List<Map<String, Object>> initWarehouseReasonData(String erpCode);

    List<Map<String, Object>> initDocType(String erpCode);

    Map<String, Object> initCloseDate(String erpCode);

}
