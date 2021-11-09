package com.shiguangping.mqconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liyan
 * @since 2021-11-09 13:31
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${consumer.demo.group1}", topic = "${consumer.demo.topic}", consumeMode = ConsumeMode.CONCURRENTLY, messageModel = MessageModel.CLUSTERING, consumeTimeout = 60000L)
public class TestMessage1 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("2--消息消费成功，@msg：{}", message);
    }
}
