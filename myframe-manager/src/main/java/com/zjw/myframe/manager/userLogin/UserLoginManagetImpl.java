package com.zjw.myframe.manager.userLogin;

import com.zjw.myframe.dao.bean.userLogin.UserLoginBean;
import com.zjw.myframe.service.userLogin.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userLoginManager")
public class UserLoginManagetImpl implements UserLoginManager {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    public boolean checkLogin(String sessionId){
        return userLoginService.checkLogin(sessionId);
    }

    @Override
    public void delete(String loginName){
        userLoginService.deleteByLoginName(loginName);
    }

    @Override
    public void insert(UserLoginBean param){
        userLoginService.insert(param);
    }

    @Override
    public void login(UserLoginBean param){
        userLoginService.login(param);
    }

    @Override
    public void logout(String sessionId){
        userLoginService.logout(sessionId);
    }

    @Override
    public void update(UserLoginBean param){
        userLoginService.updateByLoginName(param);
    }

}
