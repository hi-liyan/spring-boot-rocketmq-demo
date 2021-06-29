# spring-boot-rocketmq-demo

## 简介

本文将介绍Spring Boot整合RocketMQ，完成消息生产与消费。

<br>

## 仓库

示例代码托管在Gitee：[spring-boot-rocketmq-demo 项目地址](https://gitee.com/ENNRIAAA/spring-boot-rocketmq-demo.git)

<br>

## Quict Start

1. 创建消息生产者项目`mq-producer`，并添加依赖。

   ```xml
   <!-- rocketmq-spring-boot-starter -->
   <dependency>
     <groupId>org.apache.rocketmq</groupId>
     <artifactId>rocketmq-spring-boot-starter</artifactId>
     <version>2.2.0</version>
   </dependency>
   ```

2. 写配置，`application.yml`。

   ```yaml
   server:
     port: 8001
   
   spring:
     application:
       name: mq-producer
   
   rocketmq:
     # namesrv地址
     name-server: 121.196.189.156:9876
     # 生产者
     producer:
       # 组
       group: mq-producer-group
       # 发送超时
       send-message-timeout: 30000
   
   producer:
     demo:
       topic: rmq-test-topic
   ```

3. 写代码。

   `RocketMQTemplate`提供了操作RocketMQ的大多数方法。

   ```java
   /**
   * 单独封装一层发送mq的工具类，如实现发送同步消息、异步消息、延迟消息等
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
   ```

   调用工具类发送消息，发送消息必须指定`topic`（消息主题）。

   ```java
   /**
   * 业务上要发送的mq都定义到这里
   */
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
   ```

   创建TestController，用于后面测试将请求参数中的字符串作为消息发送给消费者。

   ```java
   @RestController
   @RequestMapping("test")
   public class TestController {
   
       @Autowired
       private MqProducer mqProducer;
   
       @GetMapping("msg")
       public String test(String msg) {
           return mqProducer.test(msg) ? "Send Success" : "Send Fail";
       }
   }
   ```

4. 创建消息消费者项目`mq-consumer`，并添加如上依赖。

5. 写配置，`application.yml`。

   ```yaml
   server:
     port: 8002
   
   spring:
     application:
       name: mq-consumer
   
   rocketmq:
     name-server: 121.196.189.156:9876
     consumer:
       group: mq-consumer-group
   
   consumer:
     demo:
       topic: rmq-test-topic
       group: rmq-test-group
   
   ```

6. 写代码。

   创建`RocketMQListener`接口的实现类，并添加`@RocketMQMessageListener`注解，用于监听某一`topic`消息。

   ```java
   @Slf4j
   @Component
   @RocketMQMessageListener(consumerGroup = "${consumer.demo.group}", topic = "${consumer.demo.topic}", consumeMode = ConsumeMode.CONCURRENTLY, messageModel = MessageModel.CLUSTERING, consumeTimeout = 60000L)
   public class TestMessage implements RocketMQListener<String> {
       @Override
       public void onMessage(String message) {
           log.info("消息消费成功，msg：{}", message);
       }
   }
   ```

7. 测试。

   使用Postman调用`mq-producer`中定义的测试接口，观察消费者是否能监听到生产者发送的消息。

   ![image-20210630001912066](https://images.shiguangping.com/imgs/20210630001912.png)

   消费者收到消息并打印日志。

   ![image-20210630002022328](https://images.shiguangping.com/imgs/20210630002022.png)

