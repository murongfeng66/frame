package com.zjw.myframe.common.exception;

import java.io.Serializable;

/**
 * 业务异常
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public class BusinessException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1030931998999674664L;

    public BusinessException(){
        super();
    }

    public BusinessException(Exception e){
        super(e);
    }

    public BusinessException(String message){
        super(message);
    }

}