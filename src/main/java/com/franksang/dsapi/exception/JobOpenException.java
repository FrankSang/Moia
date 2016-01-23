package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/10/10.
 */
public class JobOpenException extends RuntimeException {
    public JobOpenException() {
        super();
    }

    public JobOpenException(String message) {
        super(message);
    }

    public JobOpenException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobOpenException(Throwable cause) {
        super(cause);
    }
}
