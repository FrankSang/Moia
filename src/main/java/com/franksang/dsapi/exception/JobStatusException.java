package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/9/30.
 */
public class JobStatusException extends RuntimeException {
    public JobStatusException() {
        super();
    }

    public JobStatusException(String message) {
        super(message);
    }

    public JobStatusException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobStatusException(Throwable cause) {
        super(cause);
    }
}
