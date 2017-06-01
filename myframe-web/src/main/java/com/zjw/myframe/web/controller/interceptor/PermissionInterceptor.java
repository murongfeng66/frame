package com.zjw.myframe.web.controller.interceptor;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.http.client.domain.UserInfo;
import com.zjw.myframe.dao.redis.RedisUtil;
import com.zjw.myframe.dao.redis.constant.RedisExpires;
import com.zjw.myframe.common.enums.system.ResponseStatus;
import com.zjw.myframe.common.exception.SystemParamException;
import com.zjw.myframe.dao.redis.constant.RedisSuffix;
import com.zjw.myframe.web.WebResult;
import com.zjw.myframe.web.utils.RequestUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限过滤
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    /**
     * 权限和登陆排除URL集合
     */
    private List<String> excludePermissionAndLoginUrls = new ArrayList<>();
    /**
     * 权限排除URL集合（需登录）
     */
    private List<String> excludePermissionUrls = new ArrayList<>();
    /**
     * 登出接口地址
     */
    private String logoutUrl;
    /**
     * 超时接口地址
     */
    private String timeOutUrl;
    /**
     * 是否进行了拦截器参数验证
     */
    private boolean isChecked = false;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 检查拦截器参数
     */
    private void checkInitParam(){
        if(isChecked){
            return;
        }
        if(StringUtils.isEmpty(logoutUrl)){
            throw new SystemParamException("权限拦截器参数[logoutUrl]不能为空");
        }
        if(StringUtils.isEmpty(timeOutUrl)){
            timeOutUrl = logoutUrl;
        }
        isChecked = true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        checkInitParam();
        String url = request.getRequestURI();
        if(excludePermissionAndLoginUrls.contains(url)){
            return true;
        }

        String sessionId = RequestUtil.getSessionIdFromCookie();
        if(sessionId == null){
            return returnFlase(response, ResponseStatus.NO_LOGIN);
        }

        UserInfo userInfo = new UserInfo();
        userInfo = redisUtil.get(sessionId + RedisSuffix.USER_INFO, userInfo);
        if(userInfo == null){
            return returnFlase(response, ResponseStatus.TIME_OUT);
        }

        if(excludePermissionUrls.contains(url)){
            return true;
        }

        List<String> resources = redisUtil.get(sessionId + RedisSuffix.USER_PERMISSION, new ArrayList<String>());

//        if(resources == null || !resources.contains(url)){
//            return returnFlase(response, ResponseStatus.NO_PERMISSION);
//        }

        updateRedisUserInfo(sessionId);
        return true;
    }

    /**
     * 更新用户登录信息
     */
    private void updateRedisUserInfo(String sessionId){
        redisUtil.expire(sessionId + RedisSuffix.USER_INFO, RedisExpires.USERINFO_PERMISSION);
        redisUtil.expire(sessionId + RedisSuffix.USER_PERMISSION, RedisExpires.USERINFO_PERMISSION);
    }

    /**
     * 返回拦截信息
     */
    private boolean returnFlase(HttpServletResponse response, ResponseStatus responseStatus) throws IOException{
        if(RequestUtil.isAjaxRequest()){
            WebResult result = new WebResult();
            result.setStatus(responseStatus);
            response.setContentType("text/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(result));
        }else{
            if(ResponseStatus.TIME_OUT.equals(responseStatus)){
                response.sendRedirect(timeOutUrl);
            }else{
                response.sendRedirect(logoutUrl);
            }
        }
        return false;
    }

    /**
     * {@linkplain #excludePermissionAndLoginUrls}
     */
    public void setExcludePermissionAndLoginUrls(List<String> excludePermissionAndLoginUrls){
        this.excludePermissionAndLoginUrls = excludePermissionAndLoginUrls;
    }

    /**
     * {@linkplain #excludePermissionUrls}
     */
    public void setExcludePermissionUrls(List<String> excludePermissionUrls){
        this.excludePermissionUrls = excludePermissionUrls;
    }

    /**
     * {@linkplain #logoutUrl}
     */
    public void setLogoutUrl(String logoutUrl){
        this.logoutUrl = logoutUrl;
    }

    /**
     * {@linkplain #timeOutUrl}
     */
    public void setTimeOutUrl(String timeOutUrl){
        this.timeOutUrl = timeOutUrl;
    }

}
