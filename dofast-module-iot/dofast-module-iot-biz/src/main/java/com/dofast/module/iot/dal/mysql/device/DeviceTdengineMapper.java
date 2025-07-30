package com.dofast.module.iot.dal.mysql.device;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
@InterceptorIgnore(tenantLine = "true")
public interface DeviceTdengineMapper {

    List<Map<String,Object>> initDeviceMeter();

}
