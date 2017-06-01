package com.zjw.myframe.service.userLogin;

import com.zjw.myframe.dao.bean.userLogin.UserLoginBean;

/**
 * 登录信息业务逻辑层接口
 *
 * @author Jianwen Zhu
 * @version 1.0
 * @date 2016年7月16日
 */
public interface UserLoginService {

    /**
     * 根据会话ID检查登录
     */
    boolean checkLogin(String sessionId);

    /**
     * 根据登录名删除
     */
    void deleteByLoginName(String loginName);

    /**
     * 添加
     */
    void insert(UserLoginBean param);

    /**
     * 登录
     */
    void login(UserLoginBean param);

    /**
     * 根据登录名更新
     */
    void updateByLoginName(UserLoginBean param);

    /**
     * 登出
     */
    void logout(String sessionId);
}
