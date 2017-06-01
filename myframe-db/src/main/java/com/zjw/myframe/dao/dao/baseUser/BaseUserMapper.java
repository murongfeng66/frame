package com.zjw.myframe.dao.dao.baseUser;

import com.zjw.myframe.dao.bean.baseUser.BaseUserListBean;
import com.zjw.myframe.model.baseUser.BaseUser;
import com.zjw.myframe.dao.bean.baseUser.BaseUserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 基础用户持久层
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
@Repository
public interface BaseUserMapper {

    /**
     * 新增
     */
    int insert(BaseUserBean param);

    /**
     * 更新
     */
    int update(BaseUserBean param);

    /**
     * 列表查询
     */
    List<BaseUser> queryByParam(BaseUserListBean param);

    /**
     * 根据ID获取基础用户
     */
    BaseUser getById(Long id);
}
