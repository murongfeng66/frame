package com.zjw.myframe.dao.rabbitmq;

import org.apache.log4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * RabbitMQ封装方法
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Repository
public class RabbitMQUtil {

    private Logger log = Logger.getLogger(getClass());
    @Autowired
    private AmqpTemplate rabbitmqTemplate;

    public void sendMessage(String routingKey, Object object){
        log.info("发送消息【" + object + "】至路由：" + routingKey);
        try{
            rabbitmqTemplate.convertAndSend(routingKey, object);
        }catch(AmqpException e){
            log.error("发送失败", e);
        }
    }

}
