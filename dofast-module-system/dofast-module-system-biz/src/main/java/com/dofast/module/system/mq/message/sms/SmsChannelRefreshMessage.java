package com.dofast.module.system.mq.message.sms;

import com.dofast.framework.mq.core.pubsub.AbstractChannelMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 短信渠道的数据刷新 Message
 *
 * @author 芋道源码
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SmsChannelRefreshMessage extends AbstractChannelMessage {

    @Override
    public String getChannel() {
        return "system.sms-channel.refresh";
    }

}
