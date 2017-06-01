package com.zjw.myframe.model.userLogin;

import java.util.Date;

/**
 * 登录信息
 *
 * @author Jianwen Zhu
 * @version 1.0
 * @date 2016年7月16日
 */
public class Login {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 登录账号
     */
    private String loginname;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 登录IP
     */
    private String loginIp;
    /**
     * 状态
     */
    private Integer status;

    /**
     * {@linkplain #userId}
     */
    public Long getUserId(){
        return userId;
    }

    /**
     * {@linkplain #userId}
     */
    public void setUserId(Long userId){
        this.userId = userId;
    }

    /**
     * {@linkplain #loginname}
     */
    public String getLoginname(){
        return loginname;
    }

    /**
     * {@linkplain #loginname}
     **/
    public void setLoginname(String loginname){
        this.loginname = loginname;
    }

    /**
     * {@linkplain #password}
     */
    public String getPassword(){
        return password;
    }

    /**
     * {@linkplain #password}
     **/
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * {@linkplain #loginTime}
     */
    public Date getLoginTime(){
        return loginTime;
    }

    /**
     * {@linkplain #loginTime}
     **/
    public void setLoginTime(Date loginTime){
        this.loginTime = loginTime;
    }

    /**
     * {@linkplain #loginIp}
     */
    public String getLoginIp(){
        return loginIp;
    }

    /**
     * {@linkplain #loginIp}
     **/
    public void setLoginIp(String loginIp){
        this.loginIp = loginIp;
    }

    /**
     * {@linkplain #status}
     */
    public Integer getStatus(){
        return status;
    }

    /**
     * {@linkplain #status}
     **/
    public void setStatus(Integer status){
        this.status = status;
    }
}
