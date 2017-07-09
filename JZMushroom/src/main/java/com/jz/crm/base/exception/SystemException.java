package com.jz.crm.base.exception;

import com.jz.crm.base.config.SystemCode;

/**
 * A unchecked exception, system can not handle this type of exception.
 */
public class SystemException extends RuntimeException implements AssistException {

	// public static final int CODE_UNKNOWN_ERROR = 999;

	private int errorCode = SystemCode.SYSTEM_EXCEPTION;

	// metaData will be used to assemble response message.
	private Object metaData[];

	public SystemException(int code, Object metaData[]) {
		this.errorCode = code;
		this.metaData = metaData;
	}

	public SystemException(int code, String message) {
		super(message);
		this.errorCode = code;
	}

	public SystemException(Exception cause) {
		super(cause);
	}

	public SystemException(int code, Object[] metaData, Throwable cause) {
		super(cause);
		this.errorCode = code;
		this.metaData = metaData;
	}

	public SystemException(int code, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = code;
	}

	public SystemException(String message) {
		super(message);
		this.errorCode = SystemCode.SYSTEM_EXCEPTION;
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
		this.errorCode = SystemCode.SYSTEM_EXCEPTION;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

	public Object[] getMetaData() {
		return this.metaData;
	}
}
