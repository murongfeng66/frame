package com.zjw.myframe.service.baseUser;

import com.zjw.myframe.dao.bean.baseUser.BaseUserBean;
import com.zjw.myframe.dao.dao.baseUser.BaseUserMapper;
import com.zjw.myframe.dao.bean.baseUser.BaseUserListBean;
import com.zjw.myframe.common.enums.system.DataStatusEnum;
import com.zjw.myframe.common.exception.BusinessException;
import com.zjw.myframe.model.baseUser.BaseUser;
import com.zjw.myframe.dao.bean.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("baseUserService")
public class BaseUserServiceImpl implements BaseUserService {

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Override
    public void insert(BaseUserBean param){
        param.setStatus(DataStatusEnum.ENABLE.getValue());
        if(baseUserMapper.insert(param) == 0){
            throw new BusinessException("新增基础用户失败！");
        }
    }

    @Override
    public void update(BaseUserBean param){
        if(baseUserMapper.update(param) == 0){
            throw new BusinessException("更新基础用户失败！");
        }
    }

    @Override
    public PageResult<BaseUser> queryByParam(BaseUserListBean param){
        baseUserMapper.queryByParam(param);
        return param.getPageResult();
    }
}
