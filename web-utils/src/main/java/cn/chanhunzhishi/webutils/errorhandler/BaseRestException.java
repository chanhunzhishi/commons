package cn.chanhunzhishi.webutils.errorhandler;

/**
 * 基础异常
 */

public class BaseRestException extends RuntimeException {
	public BaseRestException() {
	}

	public BaseRestException(String message) {
		super(message);
	}

	public BaseRestException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseRestException(Throwable cause) {
		super(cause);
	}

	public BaseRestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
