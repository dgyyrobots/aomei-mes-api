package com.dofast.module.promotion.job.combination;

import cn.hutool.core.util.StrUtil;
import com.dofast.framework.common.core.KeyValue;
import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.framework.tenant.core.job.TenantJob;
import com.dofast.module.promotion.service.combination.CombinationRecordService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 拼团过期 Job
 *
 * @author HUIHUI
 */
@Component
public class CombinationRecordExpireJob implements JobHandler {

    @Resource
    private CombinationRecordService combinationRecordService;

    @Override
    @TenantJob
    public String execute(String param) {
        KeyValue<Integer, Integer> keyValue = combinationRecordService.expireCombinationRecord();
        return StrUtil.format("过期拼团 {} 个, 虚拟成团 {} 个", keyValue.getKey(), keyValue.getValue());
    }

}
