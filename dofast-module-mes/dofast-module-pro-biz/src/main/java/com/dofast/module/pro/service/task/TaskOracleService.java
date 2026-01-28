package com.dofast.module.pro.service.task;

import java.util.Map;

public interface TaskOracleService {

    Map<String,Object> getChangeQuantity(String workorderCode, String processCode);

}
