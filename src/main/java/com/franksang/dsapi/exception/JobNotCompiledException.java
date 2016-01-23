package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/9/29.
 */
public class JobNotCompiledException extends RuntimeException {
    public JobNotCompiledException(String message) {
        super(message);
    }

    public JobNotCompiledException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobNotCompiledException(Throwable cause) {
        super(cause);
    }

    public JobNotCompiledException() {
        super();
    }
}
