package com.zjw.myframe.service.aspect;

import java.lang.annotation.*;

/**
 * 默认组验证
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultValid {

}
