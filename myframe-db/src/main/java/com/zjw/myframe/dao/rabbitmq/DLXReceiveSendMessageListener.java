package com.zjw.myframe.dao.rabbitmq;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * RabbitMQ接收死信消息监听器
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class DLXReceiveSendMessageListener implements MessageListener {

    private Logger log = Logger.getLogger(getClass());

    @Override
    public void onMessage(Message message){
        log.info("接收死信消息：" + new String(message.getBody()));
    }

}
