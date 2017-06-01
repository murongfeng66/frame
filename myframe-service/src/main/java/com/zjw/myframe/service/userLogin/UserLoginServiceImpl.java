package com.zjw.myframe.service.userLogin;

import com.zjw.myframe.dao.dao.baseUser.BaseUserMapper;
import com.zjw.myframe.dao.dao.userLogin.UserLoginMapper;
import com.zjw.myframe.common.enums.system.DataStatusEnum;
import com.zjw.myframe.model.baseUser.BaseUser;
import com.zjw.myframe.model.userLogin.Login;
import com.zjw.myframe.dao.redis.RedisUtil;
import com.zjw.myframe.dao.redis.constant.RedisExpires;
import com.zjw.myframe.dao.redis.constant.RedisSuffix;
import com.zjw.myframe.service.aspect.Valid;
import com.zjw.myframe.common.exception.BusinessException;
import com.zjw.myframe.dao.bean.userLogin.UserLoginBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户登录信息业务逻辑层实现
 *
 * @author Jianwen Zhu
 * @version 1.0
 * @date 2016年8月2日
 */
@Service("userLoginService")
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginMapper userLoginMapper;
    @Autowired
    private BaseUserMapper baseUserMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean checkLogin(String sessionId){
        if(sessionId == null){
            return false;
        }
        Login userLogin = redisUtil.get(sessionId + RedisSuffix.USER_INFO, new Login());
        return userLogin != null;
    }

    @Override
    public void deleteByLoginName(String loginName){
        UserLoginBean userUserLoginBean = new UserLoginBean();
        userUserLoginBean.setLoginname(loginName);
        userUserLoginBean.setStatus(DataStatusEnum.DELETE.getValue());
        if(userLoginMapper.updateByLoginName(userUserLoginBean) == 0){
            throw new BusinessException("删除登录信息失败！");
        }
    }

    @Override
    public void insert(UserLoginBean param){
        param.setStatus(DataStatusEnum.ENABLE.getValue());
        if(userLoginMapper.insert(param) == 0){
            throw new BusinessException("新增登录信息失败！");
        }
    }

    @Override
    @Valid
    public void login(UserLoginBean param){
        Login login = userLoginMapper.getLoginByLoginName(param.getLoginname());
        if(login == null){
            throw new BusinessException("用户不存在！");
        }
		BaseUser baseUser = baseUserMapper.getById(login.getUserId());
		if (baseUser == null) {
			throw new BusinessException("用户不存在！");
		}
		if (baseUser.getStatus() != DataStatusEnum.ENABLE.getValue() || baseUser.getStatus() != DataStatusEnum.ENABLE.getValue()) {
			throw new BusinessException("用户被锁定！");
		}
        if(!login.getPassword().equals(param.getPassword())){
            throw new BusinessException("密码错误！");
        }

        redisUtil.set(param.getRequestSessionId() + RedisSuffix.USER_INFO, baseUser, RedisExpires.USERINFO_PERMISSION);
    }

    @Override
    public void updateByLoginName(UserLoginBean userUserLoginBean){
        if(userLoginMapper.updateByLoginName(userUserLoginBean) == 0){
            throw new BusinessException("更新登录信息失败！");
        }
    }

    @Override
    public void logout(String sessionId){
        redisUtil.delete(sessionId + RedisSuffix.USER_INFO);
    }

}