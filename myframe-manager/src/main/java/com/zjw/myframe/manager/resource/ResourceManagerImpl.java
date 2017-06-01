package com.zjw.myframe.manager.resource;

import com.zjw.myframe.dao.bean.PageResult;
import com.zjw.myframe.dao.bean.resource.ResourceListBean;
import com.zjw.myframe.model.resource.Resource;
import com.zjw.myframe.dao.bean.IdBean;
import com.zjw.myframe.dao.bean.resource.ResourceBean;
import com.zjw.myframe.service.resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ResourceManager")
public class ResourceManagerImpl implements ResourceManager {

    @Autowired
    private ResourceService resourceService;

    @Override
    public void insert(ResourceBean param){
        resourceService.insert(param);
    }

    @Override
    public void update(ResourceBean param){
        resourceService.update(param);
    }

    @Override
    public void delete(IdBean param){
        resourceService.delete(param);
    }

    @Override
    public List<Resource> menu(Long userId){
        return resourceService.menu(userId);
    }

    @Override
    public PageResult<Resource> queryByParam(ResourceListBean param) {
        return resourceService.queryByParam(param);
    }
}
