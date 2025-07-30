package com.dofast.module.system.service.dict;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.dofast.module.system.dal.oracle.dict.DictDataOracleMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.dofast.module.mes.constant.Constant;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Validated
@DS("oracle")
public class DictDataOracleServiceImpl implements DictDataOracleService {

    @Resource
    private DictDataOracleMapper dictDataOracleMapper;

    @Override
    public List<Map<String, Object>> initWarehouseReasonData(){
        String erpCode = Constant.ERP_PROD_DODE;
        return dictDataOracleMapper.initWarehouseReasonData(erpCode);
    }

    @Override
    public List<Map<String, Object>> initDocType(){
        String erpCode = Constant.ERP_PROD_DODE;
        return dictDataOracleMapper.initDocType(erpCode);
    }

    @Override
    public Map<String, Object> initCloseDate() {
        String erpCode = Constant.ERP_PROD_DODE;
        return dictDataOracleMapper.initCloseDate(erpCode);
    }
}
