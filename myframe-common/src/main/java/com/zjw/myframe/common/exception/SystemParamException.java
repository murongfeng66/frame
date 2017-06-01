package com.zjw.myframe.common.exception;

import java.io.Serializable;

/**
 * 系统参数异常
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public final class SystemParamException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1030931998999674664L;

    public SystemParamException(){
        super();
    }

    public SystemParamException(String message){
        super(message);
    }

    public SystemParamException(Throwable e){
        super(e);
    }

}