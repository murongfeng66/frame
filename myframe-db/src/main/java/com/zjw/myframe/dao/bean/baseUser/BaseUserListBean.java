package com.zjw.myframe.dao.bean.baseUser;

import com.zjw.myframe.model.baseUser.BaseUser;
import com.zjw.myframe.dao.bean.PageBean;

/**
 * 基础用户列表参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
public class BaseUserListBean extends PageBean<BaseUser> {

    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户类型
     */
    private Integer type;
    /**
     * 状态
     */
    private Integer status;

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
    }
}
