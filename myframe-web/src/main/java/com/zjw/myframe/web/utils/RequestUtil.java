package com.zjw.myframe.web.utils;

import com.zjw.myframe.common.constant.ConstantVariables;
import com.zjw.myframe.common.exception.SystemException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

public class RequestUtil {

    private RequestUtil(){
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse(){
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void deleteCookie(String key){
        HttpServletResponse response = getResponse();
        response.addCookie(new Cookie(key, ""));
    }

    public static void addCookie(String key, String value){
        HttpServletResponse response = getResponse();
        response.addCookie(new Cookie(key, value));
    }

    public static String getCookie(String key){
        HttpServletRequest request = getRequest();
        Cookie[] cookies = request.getCookies();
        if(cookies == null){
            return null;
        }
        for(Cookie cookie : cookies){
            if(key.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String getRequestIp(){
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getSessionId(){
        return getRequest().getSession().getId();
    }

    public static void setSessionIdToCookie(){
        String sessionId = getSessionId();
        addCookie(ConstantVariables.COOKIE_SESSIONID_NAME, sessionId);
    }

    public static String getSessionIdFromCookie(){
        return getCookie(ConstantVariables.COOKIE_SESSIONID_NAME);
    }

    public static void deleteSessionIdFromCookie(){
        deleteCookie(ConstantVariables.COOKIE_SESSIONID_NAME);
    }

    public static Boolean isAjaxRequest(){
        HttpServletRequest request = getRequest();
        Boolean flag = Boolean.FALSE;
        Enumeration<String> headerNames = request.getHeaderNames();

        while(headerNames.hasMoreElements()){
            String headerName = headerNames.nextElement();
            if(("x-requested-with").equalsIgnoreCase(headerName) && request.getHeader(headerName) != null){
                flag = Boolean.TRUE;
                break;
            }
        }
        return flag;
    }

    public static void writeTextToResponse(String message){
        try{
            ServletResponse response = getResponse();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(message);
            writer.flush();
            writer.close();
        }catch(IOException e){
            throw new SystemException(e);
        }
    }
}
