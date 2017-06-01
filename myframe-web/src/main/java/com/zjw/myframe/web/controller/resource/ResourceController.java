package com.zjw.myframe.web.controller.resource;

import com.zjw.myframe.dao.bean.BaseBean;
import com.zjw.myframe.dao.bean.PageResult;
import com.zjw.myframe.dao.bean.resource.ResourceBean;
import com.zjw.myframe.dao.bean.resource.ResourceListBean;
import com.zjw.myframe.manager.resource.ResourceManager;
import com.zjw.myframe.model.resource.Resource;
import com.zjw.myframe.service.aspect.GroupValid;
import com.zjw.myframe.service.aspect.Valid;
import com.zjw.myframe.web.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * URL资源控制器
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceManager resourceManager;

    @RequestMapping(path = "/toList", method = RequestMethod.GET)
    public String list(){
        return "/resource/list";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public WebResult insert(@GroupValid({BaseBean.InsertGroup.class}) ResourceBean param){
        resourceManager.insert(param);
        return WebResult.DEFAULT_RESULT;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public WebResult update(@GroupValid({BaseBean.IdGroup.class}) ResourceBean param){
        resourceManager.update(param);
        return WebResult.DEFAULT_RESULT;
    }

    @RequestMapping(path = "/menu", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public WebResult menu(BaseBean param){
        WebResult result = new WebResult();
        List<Resource> list = resourceManager.menu(param.getRequestUserId());
        result.addData("list", list);
        return result;
    }

    @RequestMapping(path = "/queryByParam", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public WebResult queryByParam(ResourceListBean param){
        WebResult result = new WebResult();
        PageResult<Resource> list = resourceManager.queryByParam(param);
        result.setData(list);
        return result;
    }
}
