package com.app.exception;

public class SystemException extends Exception {


    public SystemException(String msg) {
        super(msg);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
