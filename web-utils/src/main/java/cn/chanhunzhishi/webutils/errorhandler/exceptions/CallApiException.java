package cn.chanhunzhishi.webutils.errorhandler.exceptions;

import cn.chanhunzhishi.webutils.errorhandler.BaseRestException;

/**
 * 接口调用异常
 */
public class CallApiException extends BaseRestException {
	public CallApiException() {
	}

	public CallApiException(String message) {
		super(message);
	}

	public CallApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public CallApiException(Throwable cause) {
		super(cause);
	}

	public CallApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
