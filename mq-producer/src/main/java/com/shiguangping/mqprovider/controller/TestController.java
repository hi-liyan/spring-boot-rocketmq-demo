package com.shiguangping.mqprovider.controller;

import com.shiguangping.mqprovider.producer.MqProducer;
import com.shiguangping.mqprovider.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author liyan
 */

@RestController
@RequestMapping("/mq")
public class TestController {

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private OrderService orderService;

    @GetMapping("/send/{msg}")
    public String test(@PathVariable("msg") String msg) {
        return mqProducer.test(msg) ? "Send Success" : "Send Fail";
    }

    @PostMapping("/ordered")
    public void ordered() {
        orderService.ordered();
    }
}
