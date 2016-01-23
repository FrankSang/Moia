package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/9/29.
 */
public class JobNotFoundException extends RuntimeException {
    public JobNotFoundException() {
        super();
    }

    public JobNotFoundException(String message) {
        super(message);
    }

    public JobNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobNotFoundException(Throwable cause) {
        super(cause);
    }
}
