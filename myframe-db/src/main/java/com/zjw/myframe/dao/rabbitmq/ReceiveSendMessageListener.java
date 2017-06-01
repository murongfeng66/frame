package com.zjw.myframe.dao.rabbitmq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * RabbitMQ接收发送的消息监听器
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class ReceiveSendMessageListener implements MessageListener {

    private Logger log = Logger.getLogger(getClass());

    @Override
    public void onMessage(Message message){
        log.info("接收消息：" + new String(message.getBody()));
    }

}
