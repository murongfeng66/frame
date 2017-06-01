package com.zjw.myframe.web.controller.aspect;

import com.zjw.myframe.dao.bean.BaseBean;
import com.zjw.myframe.dao.redis.RedisUtil;
import com.zjw.myframe.model.baseUser.BaseUser;
import com.zjw.myframe.dao.redis.constant.RedisSuffix;
import com.zjw.myframe.web.utils.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 控制层切面
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Component
@Aspect
public class ControllerAspect {

    @Autowired
    private RedisUtil redisUtil;

    @Before("controllerPointcut()")
    public void before(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        BaseBean baseBean = null;
        for(Object object : args){
            if(object instanceof BaseBean){
                baseBean = (BaseBean) object;
            }
        }
        if(baseBean != null){
            initBaseParam(baseBean);
        }
    }

    @Pointcut("execution(public * com.zjw.myframe.web.controller..*.*(..))")
    public void controllerPointcut(){
    }

    /**
     * 初始化基础参数
     */
    private void initBaseParam(BaseBean baseBean){
        baseBean.setRequestIp(RequestUtil.getRequestIp());
        String sessionId = RequestUtil.getSessionIdFromCookie();
        if(StringUtils.isEmpty(sessionId)){
            sessionId = RequestUtil.getSessionId();
        }
        BaseUser baseUser = redisUtil.get(sessionId+ RedisSuffix.USER_INFO, new BaseUser());
        if(baseUser != null){
            baseBean.setRequestUserId(baseUser.getId());
        }
        baseBean.setRequestSessionId(sessionId);
    }

}
