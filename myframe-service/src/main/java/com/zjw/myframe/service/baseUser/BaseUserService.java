package com.zjw.myframe.service.baseUser;

import com.zjw.myframe.dao.bean.baseUser.BaseUserBean;
import com.zjw.myframe.dao.bean.PageResult;
import com.zjw.myframe.dao.bean.baseUser.BaseUserListBean;
import com.zjw.myframe.model.baseUser.BaseUser;

/**
 * 基础用户业务层
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
public interface BaseUserService {

    /**
     * 新增
     */
    void insert(BaseUserBean param);

    /**
     * 更新
     */
    void update(BaseUserBean param);

    /**
     * 列表查询
     */
    PageResult<BaseUser> queryByParam(BaseUserListBean param);
}
