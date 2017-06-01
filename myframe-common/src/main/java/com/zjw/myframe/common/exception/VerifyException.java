package com.zjw.myframe.common.exception;

import java.io.Serializable;

/**
 * 验证异常
 *
 * @author Jianwen Zhu
 * @date 2017-02-11
 */
public final class VerifyException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -2092566421560422362L;

    public VerifyException(){
        super();
    }

    public VerifyException(String message){
        super(message);
    }

    public static VerifyException getException(String message){
        return new VerifyException(message);
    }

}