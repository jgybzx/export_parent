package com.jgybzx.web.controller.system.rabbitMq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: guojy
 * @date: 2020/2/6 21:52
 * @Description: ${TODO}
 * @version:
 */
@Service
public class MQProducerImpl implements MQProducer {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Override
    public void sendData(String routingKey, Object data) {
        System.out.println("开始发送sendData");
        amqpTemplate.convertAndSend(routingKey,data);
    }
}
