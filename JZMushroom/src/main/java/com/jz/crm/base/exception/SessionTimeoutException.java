package com.jz.crm.base.exception;

/**
 * A checked exception, system can handle this type of exception.
 */
public class SessionTimeoutException extends Exception implements AssistException {
    private int errorCode;

    private Object[] metaData;

    public SessionTimeoutException(int code, Object[] metaData) {
        this.metaData = metaData;
        this.errorCode = code;
    }
    
    public SessionTimeoutException(String message) {
    	 super(message);
    }

    public SessionTimeoutException(int code, String message) {
        super(message);
        this.errorCode = code;
    }

    public SessionTimeoutException(int code, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = code;
    }

    public SessionTimeoutException(int code, Object[] metaData, Throwable cause) {
        super(cause);
        this.errorCode = code;
        this.metaData = metaData;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public Object[] getMetaData() {
        return this.metaData;
    }
}
