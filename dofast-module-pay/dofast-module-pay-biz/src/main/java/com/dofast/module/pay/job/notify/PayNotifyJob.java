package com.dofast.module.pay.job.notify;

import com.dofast.framework.quartz.core.handler.JobHandler;
import com.dofast.framework.tenant.core.job.TenantJob;
import com.dofast.module.pay.service.notify.PayNotifyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 支付通知 Job
 * 通过不断扫描待通知的 PayNotifyTaskDO 记录，回调业务线的回调接口
 *
 * @author 芋道源码
 */
@Component
@Slf4j
public class PayNotifyJob implements JobHandler {

    @Resource
    private PayNotifyService payNotifyService;

    @Override

    @TenantJob

    public String execute(String param) throws Exception {
        int notifyCount = payNotifyService.executeNotify();
        return String.format("执行支付通知 %s 个", notifyCount);
    }

}
