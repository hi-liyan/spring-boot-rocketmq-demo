package com.shiguangping.mqconsumer.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author liyan
 */
@Slf4j
@Component
@RocketMQMessageListener(consumerGroup = "${consumer.demo.group}", topic = "${consumer.demo.topic}", consumeMode = ConsumeMode.CONCURRENTLY, messageModel = MessageModel.CLUSTERING, consumeTimeout = 60000L)
public class TestMessage implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("消息消费成功，msg：{}", message);
    }
}
