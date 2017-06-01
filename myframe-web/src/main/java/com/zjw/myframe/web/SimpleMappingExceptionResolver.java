package com.zjw.myframe.web;

import com.alibaba.fastjson.JSONObject;
import com.zjw.myframe.common.constant.ExceptionCode;
import com.zjw.myframe.common.enums.system.ResponseStatus;
import com.zjw.myframe.common.exception.ParamException;
import com.zjw.myframe.common.exception.SystemParamException;
import com.zjw.myframe.web.utils.RequestUtil;
import com.zjw.myframe.common.exception.BusinessException;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring异常处理
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class SimpleMappingExceptionResolver extends org.springframework.web.servlet.handler.SimpleMappingExceptionResolver {

    private Logger log = Logger.getLogger(getClass());
    private String jsonPath = null;
    private String pagePath = null;

    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        String viewName = determineViewName(ex, request);

        if(viewName == null){
            if(RequestUtil.isAjaxRequest() && jsonPath != null){
                viewName = jsonPath;
            }else if(pagePath != null){
                viewName = pagePath;
            }else{
                return null;
            }
        }

        Integer statusCode = determineStatusCode(request, viewName);
        if(statusCode != null){
            applyStatusCodeIfPossible(request, response, statusCode);
        }

        return getModelAndView(viewName, ex, request);
    }

    protected ModelAndView getModelAndView(String viewName, Exception ex){
        ModelAndView mv = new ModelAndView(viewName);
        mv.addObject(WebResult.RESULT_NAME, getWebResultJsonString(ex));
        return mv;
    }

    protected String getWebResultJsonString(Exception ex){
        WebResult result = new WebResult();
        result.setStatus(ResponseStatus.EXCEPTION);

        if(ex instanceof ParamException){
            ParamException bpe = (ParamException) ex;
            result.addData("type", ExceptionCode.CONTROLLER_PARAM_EXCEPTION);
            result.addData("message", bpe.getMessage());
            result.addData("properRule", bpe.getProperRule());
            printBusinessParamException(bpe);
        }else if(ex instanceof SystemParamException){
            log.error("系统参数异常", ex);
            result.addData("type", ExceptionCode.SYSTEM_PARAM_EXCEPTION);
            result.addData("message", ex.getMessage());
        }else if(ex instanceof BusinessException){
            log.error("业务异常", ex);
            result.addData("type", ExceptionCode.BUSINESS_EXCEPTION);
            result.addData("message", ex.getMessage());
        }else{
            log.error("系统异常", ex);
            result.addData("type", ExceptionCode.SYSTEM_EXCEPTION);
            result.addData("message", "系统异常");
        }

        return JSONObject.toJSONString(result);
    }

    /**
     * 格式化打印业务参数异常
     */
    protected void printBusinessParamException(ParamException e){
        String message = "业务参数异常，参数：[" + e.getObjectName() + "]    " + "字段：[" + e.getFieldName() + "]    " + "验证值：[" + e.getFieldValue() + "]    " + "描述：[" + e.getMessage() + "]    " + "规则：[" + e.getProperRule() + "]";
        log.error(message);
    }

    /**
     * {@linkplain #jsonPath}
     */
    public void setJsonPath(String jsonPath){
        this.jsonPath = jsonPath;
    }

    /**
     * {@linkplain #pagePath}
     */
    public void setPagePath(String pagePath){
        this.pagePath = pagePath;
    }

}
