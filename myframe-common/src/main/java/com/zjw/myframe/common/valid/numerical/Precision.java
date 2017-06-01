package com.zjw.myframe.common.valid.numerical;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 数值精度验证注解类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
@Documented
@Constraint(validatedBy = PrecisionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Precision {

    Class<?>[] groups() default {};

    /**
     * 错误描述
     */
    String message() default "{javax.validation.constraints.NotNull.message}";

    Class<? extends Payload>[] payload() default {};

    /**
     * 精度
     */
    int precision();
}
