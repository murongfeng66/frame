package com.zjw.myframe.web.param;

import com.zjw.myframe.dao.bean.BaseBean;
import org.springframework.beans.BeanUtils;

/**
 * 前台基础参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class BaseParam<T extends BaseBean> extends BaseBean {

    /**
     * 初始化参数
     */
    public T initBean(T t){
        validParam();
        BeanUtils.copyProperties(this, t);
        return t;
    }

    /**
     * 手工验证参数<p>
     * 当需要手工验证参数的有效性时，则需覆盖此方法
     */
    public void validParam(){

    }

}