package com.shiguangping.mqprovider.producer.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息生产工具类
 *
 * @author liyan
 */
@Slf4j
@Component
public class RocketMqProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public boolean sendMessage(String topic, String msg) {
        log.info("mq发送开始，msg：{}", msg);
        SendResult sendResult = rocketMQTemplate.syncSend(topic, msg);
        log.info("mq发送结束：{}", sendResult);
        return "SEND_OK".equals(sendResult.getSendStatus().name()) ? true : false;
    }

}
