package com.shiguangping.mqconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liyan
 * @since 2022/3/16 21:55
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "ordered-topic", consumerGroup = "ordered-group", messageModel = MessageModel.CLUSTERING, consumeMode = ConsumeMode.ORDERLY)
public class OrderedConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("下单成功后，扣除商品库存 {}", message);
    }
}
