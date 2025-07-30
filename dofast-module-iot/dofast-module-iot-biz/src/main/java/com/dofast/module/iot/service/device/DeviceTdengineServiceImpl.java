package com.dofast.module.iot.service.device;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.dofast.module.iot.dal.mysql.device.DeviceTdengineMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Validated
@DS("taos")
public class DeviceTdengineServiceImpl implements DeviceTdengineService{

    @Resource
    private DeviceTdengineMapper deviceTdengineMapper;

    @Override
    public List<Map<String, Object>> initDeviceMeter() {
        return deviceTdengineMapper.initDeviceMeter();
    }
}
