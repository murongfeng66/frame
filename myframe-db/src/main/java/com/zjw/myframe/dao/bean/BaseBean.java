package com.zjw.myframe.dao.bean;

import com.zjw.myframe.common.exception.ParamException;
import com.zjw.myframe.common.util.ReflectUtils;
import com.zjw.myframe.common.valid.string.RegularString;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.Set;

/**
 * 基础参数
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class BaseBean {

    /**
     * 请求IP
     */
    private String requestIp;
    /**
     * 请求sessionId
     * 优先从cookie中取
     */
    private String requestSessionId;
    /**
     * 请求用户ID
     */
    private Long requestUserId;
    /**
     * 是否验证过
     */
    private boolean isChecked = false;

    /**
     * 从Model新建Param
     *
     * @param source   源对象
     * @param target   目标对象类型
     * @param baseBean 请求基础参数
     * @param ignores  排除属性
     */
    public static void copyFromModel(Object source, Class<? extends BaseBean> target, BaseBean baseBean, String... ignores) {
        Object result = ReflectUtils.copyWithOutSupper(source, target, ignores);
        ReflectUtils.copyWithOutSupper(baseBean, result, "isChecked");
    }

    /**
     * 设置基础参数
     *
     * @param baseBean 基础参数
     */
    public void setBaseBean(BaseBean baseBean) {
        ReflectUtils.copyWithOutSupper(baseBean, this, "isChecked");
    }

    /**
     * 参数验证<p>
     * 如果参数isChecked为true，则跳过验证，否则进行验证，验证完之后将isChecked设为true<p>
     * 当参数验证不通过时，则随机抛出第一个参数异常
     */
    public <T> void validBean(T parameter, Class<?>... groups) {
        if (isChecked) {
            return;
        }
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(parameter, groups);
        isChecked = true;
        if (!constraintViolations.isEmpty()) {
            ConstraintViolation<T> constraintViolation = constraintViolations.iterator().next();
            ConstraintDescriptor<?> ConstraintDescriptor = constraintViolation.getConstraintDescriptor();
            Object annotation = ConstraintDescriptor.getAnnotation();
            ParamException peb = new ParamException(constraintViolation.getMessage());
            peb.setFieldValue(constraintViolation.getInvalidValue());
            peb.setFieldName(constraintViolation.getPropertyPath().toString());
            peb.setObjectName(constraintViolation.getRootBeanClass().getSimpleName());
            if (annotation instanceof RegularString) {
                RegularString regularString = (RegularString) annotation;
                peb.setProperRule(regularString.value().getRegular());
            }
            throw peb;
        }
    }

    /**
     * {@linkplain #requestIp}
     */
    public String getRequestIp() {
        return requestIp;
    }

    /**
     * {@linkplain #requestIp}
     */
    public void setRequestIp(String requestIp) {
        this.requestIp = requestIp;
    }

    /**
     * {@linkplain #requestSessionId}
     */
    public String getRequestSessionId() {
        return requestSessionId;
    }

    /**
     * {@linkplain #requestSessionId}
     */
    public void setRequestSessionId(String requestSessionId) {
        this.requestSessionId = requestSessionId;
    }

    /**
     * {@linkplain #requestUserId}
     */
    public Long getRequestUserId() {
        return requestUserId;
    }

    /**
     * {@linkplain #requestUserId}
     */
    public void setRequestUserId(Long requestUserId) {
        this.requestUserId = requestUserId;
    }

    /**
     * ID组
     */
    public interface IdGroup {
    }

    /**
     * 新增组
     */
    public interface InsertGroup {
    }

}
