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
public class RegularValidator implements ConstraintValidator<RegularNumerical, String> {

    private NumericalFomatEnum regularNumericalEnum;
    private int precision;

    public void initialize(RegularNumerical regular){
        this.regularNumericalEnum = regular.value();
        this.precision = regular.precision();
    }

    public boolean isValid(String field, ConstraintValidatorContext cxt){
        if(field == null){
            return false;
        }
        if(precision == 0){
            return regularNumericalEnum.getPattern().matcher(field).matches();
        }else{
            return regularNumericalEnum.getPattern().matcher(field).matches() && NumericalFomatEnum.matchPrecision(field, precision);
        }
    }

}