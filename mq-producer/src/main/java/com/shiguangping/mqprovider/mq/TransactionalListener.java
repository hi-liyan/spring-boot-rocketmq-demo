package com.shiguangping.mqprovider.mq;

import com.shiguangping.mqprovider.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

/**
 * @author liyan
 * @since 2022/3/16 21:38
 */
@Slf4j
@RocketMQTransactionListener()
public class TransactionalListener implements RocketMQLocalTransactionListener {

    @Autowired
    private OrderService orderService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            orderService.insertOrder();
            log.info("3. 本地事务执行成功，返回 Commit");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            log.info("3. 本地事务执行失败，返回 Rollback");
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        log.info("4. 消息回查");
        return RocketMQLocalTransactionState.COMMIT;
    }
}
