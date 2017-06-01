package com.zjw.myframe.dao.dao.resource;

import com.zjw.myframe.dao.bean.resource.ResourceBean;
import com.zjw.myframe.dao.bean.resource.ResourceListBean;
import com.zjw.myframe.model.resource.Resource;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 资源持久层
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
@Repository
public interface ResourceMapper {

    /**
     * 新增
     */
    int insert(ResourceBean param);

    /**
     * 更新
     */
    int update(ResourceBean param);

    /**
     * 删除（物理删除）
     */
    int delete(long id);

    /**
     * 根据用户ID查询拥有权限的URL资源Map
     */
    @MapKey("id")
    Map<Long, Resource> queryMenu(@Param("userId") long userId, @Param("type") int type, @Param("status") int status);

    /**
     * 列表查询
     */
    List<Resource> queryByParam(ResourceListBean param);
}
