package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/10/14.
 */
public class JobHandleException extends RuntimeException{
    public JobHandleException() {
        super();
    }

    public JobHandleException(String message) {
        super(message);
    }

    public JobHandleException(Throwable cause) {
        super(cause);
    }

    public JobHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
