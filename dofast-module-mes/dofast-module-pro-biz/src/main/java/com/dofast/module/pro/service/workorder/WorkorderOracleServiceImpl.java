package com.dofast.module.pro.service.workorder;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.dofast.module.mes.constant.Constant;
import com.dofast.module.pro.dal.oracle.workorder.WorkorderOracleMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Service
@Validated
@DS("oracle")
public class WorkorderOracleServiceImpl implements WorkorderOracleService {

    @Resource
    private WorkorderOracleMapper workorderOracleMapper;

    /**
     * 初始化工单信息
     */
    @Override
    public List<Map<String , Object>> initWorkorderInfo(){
        String erpCode = Constant.ERP_PROD_DODE;
        return workorderOracleMapper.initWorkorder(erpCode);
    }

    /**
     * 初始化变更工单信息
     */
    @Override
    public List<Map<String , Object>> initChangeWorkorderInfo(){
        String erpCode = Constant.ERP_PROD_DODE;
        return workorderOracleMapper.initChangeWorkorder(erpCode);
    }

    /**
     * 初始化结案工单信息
     */
    @Override
    public List<Map<String , Object>> initCloseWorkorderInfo(){
        String erpCode = Constant.ERP_PROD_DODE;
        return workorderOracleMapper.initCloseWorkorder(erpCode);
    }


}
