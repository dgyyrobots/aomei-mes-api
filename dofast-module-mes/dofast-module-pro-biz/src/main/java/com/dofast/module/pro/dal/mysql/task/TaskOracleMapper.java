package com.dofast.module.pro.dal.mysql.task;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface TaskOracleMapper {

    Map<String,Object> getChangeQuantity(@Param("workorderCode")String workorderCode,@Param("processCode") String processCode);

}
