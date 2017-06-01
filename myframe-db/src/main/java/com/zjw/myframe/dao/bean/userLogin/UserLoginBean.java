package com.zjw.myframe.dao.bean.userLogin;

import com.zjw.myframe.dao.bean.BaseBean;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

/**
 * 用户登录参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class UserLoginBean extends BaseBean {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 登录账号
     */
    @NotEmpty(message = "登录账号格式错误", groups = {IdGroup.class, LoginGroup.class})
    private String loginname;
    /**
     * 登录密码
     */
    @NotEmpty(message = "登录密码格式错误", groups = {LoginGroup.class})
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
     * {@linkplain #loginIp}
     */
    public String getLoginIp(){
        return loginIp;
    }

    /**
     * {@linkplain #loginIp}
     */
    public void setLoginIp(String loginIp){
        this.loginIp = loginIp;
    }

    /**
     * {@linkplain #loginname}
     */
    public String getLoginname(){
        return loginname;
    }

    /**
     * {@linkplain #loginname}
     */
    public void setLoginname(String loginname){
        this.loginname = loginname;
    }

    /**
     * {@linkplain #loginTime}
     */
    public Date getLoginTime(){
        return loginTime;
    }

    /**
     * {@linkplain #loginTime}
     */
    public void setLoginTime(Date loginTime){
        this.loginTime = loginTime;
    }

    /**
     * {@linkplain #password}
     */
    public String getPassword(){
        return password;
    }

    /**
     * {@linkplain #password}
     */
    public void setPassword(String password){
        this.password = password;
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
     * 登录组
     */
    public interface LoginGroup {}

}
