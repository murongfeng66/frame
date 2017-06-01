package com.zjw.myframe.service.aspect;

import com.zjw.myframe.dao.bean.BaseBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数验证切面
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Component
@Aspect
public class ServiceAspect {

    @Pointcut("@annotation(com.zjw.myframe.service.aspect.Valid)")
    public void paramValidPointcut(){
    }

    @Before("paramValidPointcut()")
    public void before(JoinPoint joinPoint){
        Map<Class<?>, Class<?>[]> validMap = getValidGroup(joinPoint);
        doValid(joinPoint, validMap);
    }

    /**
     * 执行验证
     */
    private void doValid(JoinPoint joinPoint, Map<Class<?>, Class<?>[]> validMap){
        Object[] parameters = joinPoint.getArgs();
        for(Object parameter : parameters){
            if(!(parameter instanceof BaseBean)){
                break;
            }
            Class<?>[] group = validMap.get(parameter.getClass());
            if(group == null){
                break;
            }
            BaseBean baseBean = (BaseBean) parameter;
            //如果参数验证组别数量为0，则为默认组别
            if(group.length == 0){
                baseBean.validBean(parameter);
            }else{
                baseBean.validBean(parameter, group);
            }
        }
    }

    /**
     * 获取验证组
     */
    private Map<Class<?>, Class<?>[]> getValidGroup(JoinPoint joinPoint){
        Map<Class<?>, Class<?>[]> validGroupMap = new HashMap<>();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] Parameters = method.getParameters();
        for(Parameter parameter : Parameters){
            //判断参数类型是否继承至BaseParam，不是则直接跳过
            if(!BaseBean.class.isAssignableFrom(parameter.getType())){
                break;
            }
            //获取参数验证的组别，如果为空则需检查默认组别，如果不为空则跳过默认组别检查
            GroupValid groupValid = parameter.getAnnotation(GroupValid.class);
            if(groupValid != null){
                validGroupMap.put(parameter.getType(), groupValid.value());
                break;
            }
            //获取参数验证的默认组别
            DefaultValid defaultValid = parameter.getAnnotation(DefaultValid.class);
            if(defaultValid != null){
                validGroupMap.put(parameter.getType(), new Class[0]);
            }
        }
        return validGroupMap;
    }

}
