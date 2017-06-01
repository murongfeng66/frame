package com.zjw.myframe.service.resource;

import com.zjw.myframe.dao.bean.PageResult;
import com.zjw.myframe.dao.bean.resource.ResourceListBean;
import com.zjw.myframe.dao.dao.resource.ResourceMapper;
import com.zjw.myframe.common.enums.system.DataStatusEnum;
import com.zjw.myframe.common.enums.system.ResourceTypeEnum;
import com.zjw.myframe.common.exception.BusinessException;
import com.zjw.myframe.model.resource.Resource;
import com.zjw.myframe.dao.bean.IdBean;
import com.zjw.myframe.dao.bean.resource.ResourceBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public void insert(ResourceBean param){
        param.setStatus(DataStatusEnum.ENABLE.getValue());
        if(resourceMapper.insert(param) == 0){
            throw new BusinessException("新增URL资源失败！");
        }
    }

    @Override
    public void update(ResourceBean param){
        if(resourceMapper.update(param) == 0){
            throw new BusinessException("更新URL资源失败！");
        }
    }

    @Override
    public void delete(IdBean param){
        if(resourceMapper.delete(param.getId()) == 0){
            throw new BusinessException("删除URL资源失败！");
        }
    }

    @Override
    public List<Resource> menu(Long userId){
        List<Resource> list = new ArrayList<>();
        Map<Long, Resource> map = resourceMapper.queryMenu(userId, ResourceTypeEnum.MENU.getValue(), DataStatusEnum.ENABLE.getValue());
        for(Resource item : map.values()){
            if(item.getPid() == 0){
                list.add(item);
            }else{
                map.get(item.getPid()).getChildren().add(item);
            }
        }
        return list;
    }

    @Override
    public PageResult<Resource> queryByParam(ResourceListBean param) {
        resourceMapper.queryByParam(param);
        return param.getPageResult();
    }
}
