package com.zjw.myframe.web.controller.baseUser;

import com.alibaba.fastjson.JSONObject;
import com.zjw.myframe.dao.bean.BaseBean;
import com.zjw.myframe.dao.bean.PageResult;
import com.zjw.myframe.dao.bean.baseUser.BaseUserBean;
import com.zjw.myframe.dao.bean.baseUser.BaseUserListBean;
import com.zjw.myframe.manager.baseUser.BaseUserManager;
import com.zjw.myframe.model.baseUser.BaseUser;
import com.zjw.myframe.service.aspect.GroupValid;
import com.zjw.myframe.service.aspect.Valid;
import com.zjw.myframe.web.WebResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 基础用户控制器
 *
 * @author Jianwen Zhu
 * @date 2017-02-14
 */
@Controller
@RequestMapping("/baseUser")
public class BaseUserController {

    @Autowired
    private BaseUserManager baseUserManager;

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public WebResult insert(@GroupValid({BaseBean.InsertGroup.class}) BaseUserBean param){
        baseUserManager.insert(param);
        return WebResult.DEFAULT_RESULT;
    }

    @RequestMapping(path = "/update", method = RequestMethod.POST)
    @ResponseBody
    @Valid
    public WebResult update(@GroupValid({BaseBean.IdGroup.class}) BaseUserBean param){
        baseUserManager.update(param);
        return WebResult.DEFAULT_RESULT;
    }

    @RequestMapping(path = "/queryByParam", method = RequestMethod.POST)
    @ResponseBody
    public WebResult queryByParam(BaseUserListBean param){
        WebResult result = new WebResult();
        PageResult<BaseUser> list = baseUserManager.queryByParam(param);
//        result.setData(list);
        JSONObject returnjobj =  new JSONObject();
        returnjobj.put("iTotalRecords", list.getCount());
        returnjobj.put("iTotalDisplayRecords", list.getCount());
        returnjobj.put("aaData",list.getData());
        result.setData(returnjobj);
        return result;
    }
}
