package com.shiguangping.mqprovider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author liyan
 * @since 2022/3/16 21:48
 */
@Service
@Slf4j
public class OrderService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 下单
     */
    public void ordered() {
        log.info("进入下单方法");
        rocketMQTemplate.sendMessageInTransaction("ordered-topic", MessageBuilder.withPayload("消息体").setHeader("111", "222").build(), null);
    }

    public void insertOrder() {
        int a = 1/0;
        log.info("订单数据入库");
    }
}
