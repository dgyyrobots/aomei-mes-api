package com.dofast.module.pay.service.order;

 

import com.dofast.module.pay.service.channel.PayAppServiceImpl;
import com.dofast.module.pay.service.channel.PayChannelServiceImpl;

import com.dofast.module.pay.service.order.dto.PayOrderCreateReqDTO;
import com.dofast.module.pay.service.order.dto.PayOrderSubmitReqDTO;
import com.dofast.module.pay.test.BaseDbIntegrationTest;
import com.dofast.framework.common.util.date.DateUtils;
import com.dofast.framework.pay.config.DofastPayAutoConfiguration;
import com.dofast.framework.pay.core.enums.PayChannelEnum;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.time.Duration;

@Import({PayOrderServiceImpl.class, PayAppServiceImpl.class,
        PayChannelServiceImpl.class, DofastPayAutoConfiguration.class})
public class PayOrderServiceIntegrationTest extends BaseDbIntegrationTest {

    @Resource
    private PayOrderService payOrderService;

    @Test
    public void testCreatePayOrder() {
        // 构造请求
        PayOrderCreateReqDTO reqDTO = new PayOrderCreateReqDTO();
        reqDTO.setAppId(6L);
        reqDTO.setUserIp("127.0.0.1");
        reqDTO.setMerchantOrderId(String.valueOf(System.currentTimeMillis()));
        reqDTO.setSubject("标题");
        reqDTO.setBody("内容");
 
        reqDTO.setAmount(100);

        reqDTO.setPrice(100);

        reqDTO.setExpireTime(DateUtils.addTime(Duration.ofDays(1)));
        // 发起请求
        payOrderService.createPayOrder(reqDTO);
    }

    @Test
    public void testSubmitPayOrder() {
        // 构造请求
        PayOrderSubmitReqDTO reqDTO = new PayOrderSubmitReqDTO();
        reqDTO.setId(10L);
        reqDTO.setAppId(6L);
        reqDTO.setChannelCode(PayChannelEnum.WX_PUB.getCode());
        reqDTO.setUserIp("127.0.0.1");
        // 发起请求
        payOrderService.submitPayOrder(reqDTO);
    }

}
