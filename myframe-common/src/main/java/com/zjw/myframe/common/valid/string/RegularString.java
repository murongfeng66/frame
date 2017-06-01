package com.zjw.myframe.common.valid.string;

import com.zjw.myframe.common.enums.fomat.StringFomatEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 数值正则验证注解类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Documented
@Constraint(validatedBy = RegularValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegularString {

    Class<?>[] groups() default {};

    /**
     * 错误描述
     */
    String message() default "";

    Class<? extends Payload>[] payload() default {};

    /**
     * 正则枚举
     */
    StringFomatEnum value();

}
