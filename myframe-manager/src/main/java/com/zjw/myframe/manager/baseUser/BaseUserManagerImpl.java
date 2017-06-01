package com.zjw.myframe.manager.baseUser;

import com.zjw.myframe.dao.bean.baseUser.BaseUserBean;
import com.zjw.myframe.dao.bean.PageResult;
import com.zjw.myframe.dao.bean.baseUser.BaseUserListBean;
import com.zjw.myframe.service.baseUser.BaseUserService;
import com.zjw.myframe.model.baseUser.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("baseUserManager")
public class BaseUserManagerImpl implements BaseUserManager {

    @Autowired
    private BaseUserService baseUserService;

    @Override
    public void insert(BaseUserBean param){
        baseUserService.insert(param);
    }

    @Override
    public void update(BaseUserBean param){
        baseUserService.update(param);
    }

    @Override
    public PageResult<BaseUser> queryByParam(BaseUserListBean param){
        return baseUserService.queryByParam(param);
    }
}
