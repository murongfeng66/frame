package com.zjw.myframe.dao.activemq;

import com.zjw.myframe.common.enums.fomat.DateFormatEnum;
import org.apache.log4j.Logger;

/**
 * ActiveMQ接收死信消息监听器
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class DLQReceiveSendMessageListener {

    private Logger log = Logger.getLogger(getClass());

    public void receiveSendMessage(String message){
        log.info(DateFormatEnum.DATETIME_FOMAT_CHINESE.fomatNow() + "接收死信消息：" + message);
    }

}
