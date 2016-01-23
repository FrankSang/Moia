package com.franksang.dsapi.exception;

/**
 * Created by Administrator on 2015/10/13.
 */
public class OutofMemoryException extends JobOpenException {
    public OutofMemoryException() {
        super();
    }

    public OutofMemoryException(String message) {
        super(message);
    }

    public OutofMemoryException(Throwable cause) {
        super(cause);
    }

    public OutofMemoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
