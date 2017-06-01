package com.zjw.myframe.model.baseUser;

import com.zjw.myframe.common.enums.system.DataStatusEnum;
import com.zjw.myframe.common.enums.system.UserTypeEnum;

/**
 * 基础用户
 *
 * @author Jianwen Zhu
 * @date 2017-02-13
 */
public class BaseUser {

    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户类型
     */
    private Integer type;
    /**
     * 用户类型描述
     */
    private String typeMessage;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 状态描述
     */
    private String statusMessage;

    /**
     * {@linkplain #id}
     */
    public Long getId(){
        return id;
    }

    /**
     * {@linkplain #id}
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * {@linkplain #username}
     */
    public String getUsername(){
        return username;
    }

    /**
     * {@linkplain #username}
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * {@linkplain #type}
     */
    public Integer getType(){
        return type;
    }

    /**
     * {@linkplain #type}
     */
    public void setType(Integer type){
        this.type = type;
        this.typeMessage = UserTypeEnum.getMessage(type);
    }

    /**
     * {@linkplain #typeMessage}
     */
    public String getTypeMessage() {
        return typeMessage;
    }

    /**
     * {@linkplain #status}
     */
    public Integer getStatus(){
        return status;
    }

    /**
     * {@linkplain #status}
     */
    public void setStatus(Integer status){
        this.status = status;
        this.statusMessage = DataStatusEnum.getMessage(status);
    }

    /**
     * {@linkplain #statusMessage}
     */
    public String getStatusMessage() {
        return statusMessage;
    }
}
