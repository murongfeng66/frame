package com.zjw.myframe.dao.activemq;

import com.zjw.myframe.common.enums.fomat.DateFormatEnum;
import org.apache.log4j.Logger;

/**
 * ActiveMQ接收回复消息监听器
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class ReceiveReplyMessageListener {

    private Logger log = Logger.getLogger(getClass());

    public void receiveReplyMessage(String message){
        log.info(DateFormatEnum.DATETIME_FOMAT_CHINESE.fomatNow() + "接收回复消息：" + message);
    }

}
