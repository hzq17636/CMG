package com.jz.crm.base.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jz.crm.base.config.ConstantContext;
import com.jz.crm.base.config.SystemCode;
import com.jz.crm.base.exception.ApplicationException;

/**
 * 统一异常处理
 * 主要处理ApplicationException,IllegalArgumentException类型处理，其它异常统一为系统异常
 * @author 李则意
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	public static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Map<String, Object> handleException(HttpServletRequest req, Exception e) {
		LOG.error(e.getMessage(), this.getCause(e));
		Map<String, Object> result = new HashMap<String, Object>();
		if (e instanceof ApplicationException) {
			result.put(ConstantContext.RESPONSE_CODE, ((ApplicationException) e).getErrorCode());
			if (e.getMessage() != null && e.getMessage().length() > 0) {
				String[] message = e.getMessage().split("\\[");
				result.put(ConstantContext.MESSAGE, message[0]);
			}
		} else if (e instanceof IllegalArgumentException) {
			result.put(ConstantContext.RESPONSE_CODE, SystemCode.REQUEST_PARAMETER_FAILURE);
			if (e.getMessage() != null && e.getMessage().length() > 0) {
				String[] message = e.getMessage().split("\\[");
				result.put(ConstantContext.MESSAGE, message[0]);
			}
		} else {
			result.put(ConstantContext.RESPONSE_CODE, SystemCode.SYSTEM_EXCEPTION);
			result.put(ConstantContext.MESSAGE, "系统异常");
		}
		return result;
	}
	
	private Throwable getCause(Throwable e) {
		Throwable cause = e.getCause();
		if (cause == null) {
			return e;
		} else {
			return getCause(cause);
		}

	}
}
