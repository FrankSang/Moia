package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/10/13.
 */
public class UnknowException extends JobOpenException {
    public UnknowException() {
        super();
    }

    public UnknowException(String message) {
        super(message);
    }

    public UnknowException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknowException(Throwable cause) {
        super(cause);
    }
}
