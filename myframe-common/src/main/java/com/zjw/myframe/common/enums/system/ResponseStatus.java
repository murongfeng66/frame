package com.zjw.myframe.common.enums.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 无权限代码
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public enum ResponseStatus {
    /**
     * 成功
     */
    SUCCESS(1, "成功"),
    /**
     * 失败
     */
    FAIL(-1, "失败"),
    /**
     * 异常
     */
    EXCEPTION(-2, "异常"),
    /**
     * 未登录
     */
    NO_LOGIN(-3, "未登录"),
    /**
     * 登录超时
     */
    TIME_OUT(-4, "登录超时"),
    /**
     * 无权限
     */
    NO_PERMISSION(-5, "无权限");

    /**
     * 代码
     */
    private int value;
    /**
     * 描述
     */
    private String message;

    ResponseStatus(int value, String message){
        this.value = value;
        this.message = message;
    }

    /**
     * 根据代码获取描述
     */
    public static String getMessage(Integer status){
        if(status == null){
            return "";
        }
        int value = status;
        for(ResponseStatus item : ResponseStatus.values()){
            if(item.value == value){
                return item.message;
            }
        }
        return "";
    }

    /**
     * 获取所有枚举的状态值和描述Map
     */
    public static List<Map<String, Object>> getAllStatusMap(){
        List<Map<String, Object>> list = new ArrayList<>();
        for(ResponseStatus item : ResponseStatus.values()){
            Map<String, Object> map = new HashMap<>();
            map.put("value", item.value);
            map.put("message", item.message);
            list.add(map);
        }
        return list;
    }

    public boolean equals(Integer value){
        return value != null && this.value == value;
    }

    /**
     * {@linkplain #value}
     */
    public int getValue(){
        return value;
    }

    /**
     * {@linkplain #message}
     */
    public String getMessage(){
        return message;
    }

}
