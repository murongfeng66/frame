package com.zjw.myframe.web.controller.userLogin;

import com.zjw.myframe.manager.userLogin.UserLoginManager;
import com.zjw.myframe.dao.redis.RedisUtil;
import com.zjw.myframe.dao.redis.constant.RedisExpires;
import com.zjw.myframe.common.verifycode.Candition;
import com.zjw.myframe.common.verifycode.ImageVerifyCodeFactory;
import com.zjw.myframe.dao.bean.userLogin.UserLoginBean;
import com.zjw.myframe.dao.redis.constant.RedisSuffix;
import com.zjw.myframe.common.verifycode.VerifyCodeType;
import com.zjw.myframe.web.WebResult;
import com.zjw.myframe.web.utils.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 用户登录信息控制器
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Controller
public class UserLoginController {

    @Autowired
    private UserLoginManager userLoginManager;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object login(UserLoginBean param){
        userLoginManager.login(param);
        RequestUtil.setSessionIdToCookie();
        return WebResult.DEFAULT_RESULT;
    }

    /**
     * 登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String toLogout(){
        String sessionId = RequestUtil.getSessionIdFromCookie();
        RequestUtil.deleteSessionIdFromCookie();
        userLoginManager.logout(sessionId);
        return "login";
    }

    @RequestMapping(value = {"/toLogin", "/"}, method = RequestMethod.GET)
    public ModelAndView toLogin(ModelAndView view, HttpServletRequest request){
        String sessionId = RequestUtil.getSessionIdFromCookie();
        if(userLoginManager.checkLogin(sessionId)){
            view.setViewName("redirect:/toMain");
        }else{
            view.setViewName("login");
        }
        return view;
    }

    @RequestMapping(value = "/toMain", method = RequestMethod.GET)
    public String toMain(){
        return "main";
    }

    @RequestMapping(value = "/toTimeOut", method = RequestMethod.GET)
    public String toTimeOut(){
        return "timeOut";
    }

    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public void verifyCode(HttpServletResponse response) throws IOException{
        String sessionId = RequestUtil.getSessionId();
        String verifyCode = VerifyCodeType.TYPE_ALL_MIXED.getCode(4, null);
        Candition candition = new Candition();
        candition.setVerifyCode(verifyCode);
        candition.setFontSize(34);
        BufferedImage buffImg = ImageVerifyCodeFactory.createImageCode(candition);

        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("image/jpeg");
        ServletOutputStream sos = response.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();

        redisUtil.set(sessionId + RedisSuffix.VERIFYCODE, verifyCode, RedisExpires.VERIFYCODE);
    }

    /**
     * 根据登录名删除
     */
    @RequestMapping(value = "/login/delete", method = RequestMethod.POST)
    @ResponseBody
    public WebResult delete(UserLoginBean param){
        userLoginManager.delete(param.getLoginname());
        return WebResult.DEFAULT_RESULT;
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/login/add", method = RequestMethod.POST)
    @ResponseBody
    public WebResult insert(UserLoginBean param){
        userLoginManager.insert(param);
        return WebResult.DEFAULT_RESULT;
    }

    /**
     * 根据登录名更新
     */
    @RequestMapping(value = "/login/update", method = RequestMethod.POST)
    @ResponseBody
    public WebResult update(UserLoginBean param){
        userLoginManager.update(param);
        return WebResult.DEFAULT_RESULT;
    }

}
