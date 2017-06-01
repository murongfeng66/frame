package com.zjw.myframe.common.exception;

import java.io.Serializable;

/**
 * 系统异常
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public final class SystemException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1030931998999674664L;

    public SystemException(){
        super();
    }

    public SystemException(Exception e){
        super(e);
    }

    public SystemException(String message){
        super(message);
    }
}