package com.zjw.myframe.dao.bean.baseUser;

import com.zjw.myframe.dao.bean.BaseBean;

/**
 * 基础用户参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-13
 */
public class BaseUserBean extends BaseBean {

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
     * 状态
     */
    private Integer status;

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
    }
}
