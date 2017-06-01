package com.zjw.myframe.web;

import java.util.Date;

/**
 * WEB请求结果
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class WebResult extends RequestBaseResult {

    /**
     * 默认成功对象
     */
    public static final WebResult DEFAULT_RESULT = new WebResult();
    private Date returnTime = new Date();

    /**
     * {@linkplain #returnTime}
     */
    public Date getReturnTime(){
        return returnTime;
    }

    /**
     * {@linkplain #returnTime}
     */
    public void setReturnTime(Date returnTime){
        this.returnTime = returnTime;
    }

}
