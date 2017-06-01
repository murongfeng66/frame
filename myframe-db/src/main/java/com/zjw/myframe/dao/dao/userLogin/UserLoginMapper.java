package com.zjw.myframe.dao.dao.userLogin;

import com.zjw.myframe.model.userLogin.Login;
import com.zjw.myframe.dao.bean.userLogin.UserLoginBean;
import org.springframework.stereotype.Repository;

/**
 * 登录信息持久层
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Repository
public interface UserLoginMapper {

    /**
     * 获取用户登录信息
     *
     * @param loginName 登录名
     */
    Login getLoginByLoginName(String loginName);

    /**
     * 新增
     */
    int insert(UserLoginBean param);

    /**
     * 根据登录名更新
     */
    int updateByLoginName(UserLoginBean param);

    /**
     * 根据用户ID更新
     */
    int updateByUserId(UserLoginBean param);

}
