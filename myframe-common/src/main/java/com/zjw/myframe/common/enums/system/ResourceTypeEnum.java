package com.zjw.myframe.common.enums.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据状态代码
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public enum ResourceTypeEnum {

    /**
     * 菜单
     */
    MENU(1, "菜单"),
    /**
     * 按钮
     */
    BUTTON(2, "按钮");

    /**
     * 代码
     */
    private int value;
    /**
     * 描述
     */
    private String message;

    ResourceTypeEnum(int value, String message){
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
        for(ResourceTypeEnum item : ResourceTypeEnum.values()){
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
        for(ResourceTypeEnum item : ResourceTypeEnum.values()){
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
