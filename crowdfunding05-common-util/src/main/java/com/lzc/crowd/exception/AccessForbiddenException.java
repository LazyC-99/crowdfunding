package com.lzc.crowd.exception;

/**
 *访问未登录时禁止访问的页码的异常
 * @author Administrator
 */
public class AccessForbiddenException extends RuntimeException{
    //序列化时为了保持版bai本的兼容性，即在版本升级时反序du列化仍保持对象的唯一性

    private static final long serialVersionUID = 1L;
    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    protected AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
