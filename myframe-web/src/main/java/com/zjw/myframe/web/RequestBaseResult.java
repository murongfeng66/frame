package com.zjw.myframe.web;

import com.zjw.myframe.common.enums.system.ResponseStatus;
import com.zjw.myframe.common.util.ReflectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求基础结果
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class RequestBaseResult {

    /**
     * 返回结果名称
     */
    public static final String RESULT_NAME = "result";
    /**
     * 返回状态
     */
    private int status = ResponseStatus.SUCCESS.getValue();
    /**
     * 返回状态
     */
    private String message = ResponseStatus.SUCCESS.getMessage();
    /**
     * 返回数据
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * {@linkplain #data}
     */
    public void addData(String key, Object value){
        this.data.put(key, value);
    }

    /**
     * {@linkplain #data}
     */
    public Map<String, Object> getData(){
        return data;
    }

    /**
     * {@linkplain #data}
     */
    public void setData(Map<String, Object> data){
        this.data = data;
    }

    /**
     * {@linkplain #data}（不包含父类）
     */
    public void setData(Object object){
        this.data = ReflectUtils.toMapWithOutSupper(object);
    }

    /**
     * {@linkplain #status}
     */
    public int getStatus(){
        return status;
    }

    /**
     * {@linkplain #status}
     */
    public void setStatus(ResponseStatus responseStatus){
        this.status = responseStatus.getValue();
        this.message = responseStatus.getMessage();
    }

    /**
     * {@linkplain #message}
     */
    public String getMessage(){
        return message;
    }

}
