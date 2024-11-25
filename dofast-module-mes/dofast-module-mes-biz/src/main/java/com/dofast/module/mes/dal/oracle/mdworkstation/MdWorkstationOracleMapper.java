package com.dofast.module.mes.dal.oracle.mdworkstation;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface MdWorkstationOracleMapper {

    /**
     * 从ERP获取工作站信息
     */
    List<Map<String, Object>> initWorkstation();

}
