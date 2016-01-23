package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/10/10.
 */
public class ProjectNoFoundException extends RuntimeException {
    public ProjectNoFoundException() {
        super();
    }

    public ProjectNoFoundException(String message) {
        super(message);
    }

    public ProjectNoFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProjectNoFoundException(Throwable cause) {
        super(cause);
    }
}
