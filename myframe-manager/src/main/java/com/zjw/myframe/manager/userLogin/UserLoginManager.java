package com.zjw.myframe.manager.userLogin;

import com.zjw.myframe.dao.bean.userLogin.UserLoginBean;

/**
 * 登录信息事务层
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public interface UserLoginManager {

    /**
     * 根据会话ID检查登录
     */
    boolean checkLogin(String sessionId);

    /**
     * 删除
     */
    void delete(String loginName);

    /**
     * 添加
     */
    void insert(UserLoginBean param);

    /**
     * 登录
     */
    void login(UserLoginBean param);

    /**
     * 登出
     */
    void logout(String sessionId);

    /**
     * 更新
     */
    void update(UserLoginBean param);

}
