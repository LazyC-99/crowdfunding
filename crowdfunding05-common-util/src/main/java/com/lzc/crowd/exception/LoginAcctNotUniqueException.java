package com.lzc.crowd.exception;
/**
 * 账户名不唯一异常
 * @author Administrator
 */
public class LoginAcctNotUniqueException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public LoginAcctNotUniqueException() {
        super();
    }

    public LoginAcctNotUniqueException(String message) {
        super(message);
    }

    public LoginAcctNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctNotUniqueException(Throwable cause) {
        super(cause);
    }

    protected LoginAcctNotUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
