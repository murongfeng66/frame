package com.zjw.myframe.dao.activemq;

import com.zjw.myframe.common.enums.fomat.DateFormatEnum;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Repository;

/**
 * ActiveMQ封装方法
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Repository
public class ActiveMQUtil {

    @Autowired
    private JmsTemplate jmsTemplate;
    private Logger log = Logger.getLogger(getClass());

    public void sendMessage(String message){
        log.info(DateFormatEnum.DATETIME_FOMAT_CHINESE.fomatNow() + "发送消息：" + message);
        jmsTemplate.send(session -> session.createTextMessage(message));
    }

}
