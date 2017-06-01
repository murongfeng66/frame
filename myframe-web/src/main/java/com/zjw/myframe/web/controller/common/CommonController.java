package com.zjw.myframe.web.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 公用控制层
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Controller
@RequestMapping("/common")
public class CommonController {

    @RequestMapping(value = "/toDatagrid", method = RequestMethod.GET)
    public String datagrid(){
        return "model/datagrid";
    }

}
