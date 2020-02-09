package com.jgybzx.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jgybzx.common.utils.MailUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.io.IOException;

//实现监听器接口  MessageListener 监听器接口
public class MyListener implements MessageListener{


    //定义转换json的接口
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //监听方法  只要监听到消息就执行  message 消息中间件存储的消息
    @Override
    public void onMessage(Message message) {
        byte[] body = message.getBody();
        try {
            JsonNode jsonNode = MAPPER.readTree(body);
            // 获得数据
            String to = jsonNode.get("to").asText();
            String subject = jsonNode.get("subject").asText();
            String content = jsonNode.get("content").asText();
            // 发送邮件
            MailUtil.sendMsg(to,subject,content);
            System.out.println("发送邮件成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
