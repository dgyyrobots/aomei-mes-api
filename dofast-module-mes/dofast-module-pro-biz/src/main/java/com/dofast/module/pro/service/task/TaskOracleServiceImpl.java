package com.dofast.module.pro.service.task;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.dofast.module.pro.dal.mysql.task.TaskOracleMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;

@Service
@Validated
@DS("oracle")
public class TaskOracleServiceImpl implements TaskOracleService{

    @Resource
    private TaskOracleMapper taskOracleMapper;

    public Map<String,Object> getChangeQuantity(String workorderCode, String processCode){
        return taskOracleMapper.getChangeQuantity(workorderCode , processCode);
    }

}
