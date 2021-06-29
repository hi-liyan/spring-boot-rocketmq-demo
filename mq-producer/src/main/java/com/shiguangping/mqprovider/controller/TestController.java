package com.shiguangping.mqprovider.controller;

import com.shiguangping.mqprovider.producer.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liyan
 */

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private MqProducer mqProducer;

    @GetMapping("msg")
    public void test(String msg) {
        mqProducer.test(msg);
    }
}
