package cn.chanhunzhishi.webutils.errorhandler.exceptions;

import cn.chanhunzhishi.webutils.errorhandler.BaseRestException;

/**
 * 参数校验异常
 */
public class ParamsException extends BaseRestException {
	public ParamsException() {
	}

	public ParamsException(String message) {
		super(message);
	}

	public ParamsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParamsException(Throwable cause) {
		super(cause);
	}

	public ParamsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
