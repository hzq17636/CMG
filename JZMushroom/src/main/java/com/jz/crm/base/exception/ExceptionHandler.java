package com.jz.crm.base.exception;

import com.jz.crm.base.config.SystemCode;

/**
 * Handle all exceptions, assemble a error message from exception.
 */
public class ExceptionHandler {
	private Throwable t;

	public ExceptionHandler(Throwable t) {
		assert t != null : "t can NOT be null.";
		this.t = t;
	}

	/**
	 * retrieve error code from exception
	 */
	public int getErrorCode() {
		if (t instanceof AssistException) {
			return ((AssistException) t).getErrorCode();
		} else {
			return SystemCode.SYSTEM_EXCEPTION;
		}
	}

	private Object[] getMetaData() {
		if (t instanceof AssistException) {
			return ((AssistException) t).getMetaData();
		} else {
			return null;
		}
	}
}
