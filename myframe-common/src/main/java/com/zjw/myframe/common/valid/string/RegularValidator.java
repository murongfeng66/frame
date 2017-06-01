package com.zjw.myframe.common.valid.string;

import com.zjw.myframe.common.enums.fomat.StringFomatEnum;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 数值正则验证注解执行类
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class RegularValidator implements ConstraintValidator<RegularString, String> {

    private StringFomatEnum regularNumericalEnum;

    public void initialize(RegularString regular){
        this.regularNumericalEnum = regular.value();
    }

    public boolean isValid(String field, ConstraintValidatorContext cxt){
        return field != null && regularNumericalEnum.getPattern().matcher(field).matches();
    }
}