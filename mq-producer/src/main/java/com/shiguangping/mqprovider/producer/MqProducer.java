package com.shiguangping.mqprovider.producer;

import com.shiguangping.mqprovider.producer.util.RocketMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MqProducer {

    @Autowired
    private RocketMqProducer rocketMqProducer;

    @Value("${producer.demo.topic}")
    private String testTopic;

    public boolean test(String msg) {
        return rocketMqProducer.sendMessage(testTopic, msg);
    }
}
