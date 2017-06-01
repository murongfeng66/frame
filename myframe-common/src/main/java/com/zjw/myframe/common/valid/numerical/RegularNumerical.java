package com.zjw.myframe.common.valid.numerical;

import com.zjw.myframe.common.enums.fomat.NumericalFomatEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 精度验证注解执行类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Documented
@Constraint(validatedBy = RegularValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RegularNumerical {

    Class<?>[] groups() default {};

    /**
     * 错误描述
     */
    String message() default "";

    Class<? extends Payload>[] payload() default {};

    /**
     * 精度
     */
    int precision() default 0;

    /**
     * 正则枚举
     */
    NumericalFomatEnum value();
}
