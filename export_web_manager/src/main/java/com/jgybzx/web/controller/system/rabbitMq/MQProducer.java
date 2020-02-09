package com.jgybzx.web.controller.system.rabbitMq;

/**
 * @author: guojy
 * @date: 2020/2/6 21:49
 * @Description: ${TODO}
 * @version:
 */
public interface MQProducer {
    public void sendData(String routingKey,Object data);
}
