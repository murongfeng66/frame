package com.zjw.myframe.common.valid.numerical;

import com.zjw.myframe.common.enums.fomat.NumericalFomatEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 精度验证注解执行类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class PrecisionValidator implements ConstraintValidator<Precision, String> {

    private int precision;

    public void initialize(Precision precision){
        this.precision = precision.precision();
    }

    public boolean isValid(String field, ConstraintValidatorContext cxt){
        return field != null && NumericalFomatEnum.matchPrecision(field, precision);
    }
}