package com.zjw.myframe.dao.activemq;

import com.zjw.myframe.common.enums.fomat.DateFormatEnum;
import org.apache.log4j.Logger;

/**
 * ActiveMQ接收发送消息监听器
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class ReceiveSendMessageListener {

    private Logger log = Logger.getLogger(getClass());

    public String receiveSendMessage(String message){
        log.info(DateFormatEnum.DATETIME_FOMAT_CHINESE.fomatNow() + "接收消息：" + message);
        return "回复消息：" + message;
    }

}
