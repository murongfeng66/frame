package com.zjw.myframe.service.resource;

import com.zjw.myframe.dao.bean.PageResult;
import com.zjw.myframe.dao.bean.resource.ResourceListBean;
import com.zjw.myframe.model.resource.Resource;
import com.zjw.myframe.dao.bean.IdBean;
import com.zjw.myframe.dao.bean.resource.ResourceBean;

import java.util.List;

/**
 * URL资源业务层
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
public interface ResourceService {

    /**
     * 新增
     */
    void insert(ResourceBean param);

    /**
     * 更新
     */
    void update(ResourceBean param);

    /**
     * 删除
     */
    void delete(IdBean param);

    /**
     * 根据用户ID请求菜单列表
     */
    List<Resource> menu(Long userId);

    /**
     * 列表查询
     */
    PageResult<Resource> queryByParam(ResourceListBean param);
}
