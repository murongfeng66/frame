package com.zjw.myframe.common.exception;

/**
 * 业务参数异常
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public final class ParamException extends RuntimeException {

    private static final long serialVersionUID = 1030931998999674664L;
    /**
     * 正确规则
     */
    private String properRule;
    /**
     * 参数名称
     */
    private String fieldName;
    /**
     * 参数值
     */
    private Object fieldValue;
    /**
     * 对象名称
     */
    private String objectName;

    public ParamException(){
        super();
    }

    public ParamException(Exception e){
        super(e);
    }

    public ParamException(String message){
        super(message);
    }

    /**
     * {@linkplain #properRule}
     */
    public String getProperRule(){
        return properRule;
    }

    /**
     * {@linkplain #properRule}
     */
    public void setProperRule(String properRule){
        this.properRule = properRule;
    }

    /**
     * {@linkplain #fieldName}
     */
    public String getFieldName(){
        return fieldName;
    }

    /**
     * {@linkplain #fieldName}
     */
    public void setFieldName(String fieldName){
        this.fieldName = fieldName;
    }

    /**
     * {@linkplain #fieldValue}
     */
    public Object getFieldValue(){
        return fieldValue;
    }

    /**
     * {@linkplain #fieldValue}
     */
    public void setFieldValue(Object fieldValue){
        this.fieldValue = fieldValue;
    }

    /**
     * {@linkplain #objectName}
     */
    public String getObjectName(){
        return objectName;
    }

    /**
     * {@linkplain #objectName}
     */
    public void setObjectName(String objectName){
        this.objectName = objectName;
    }

}