package cn.chanhunzhishi.webutils.errorhandler.exceptions;

import cn.chanhunzhishi.webutils.errorhandler.BaseRestException;

/**
 * 权限不足异常
 */
public class RoleException extends BaseRestException {
	public RoleException() {
	}

	public RoleException(String message) {
		super(message);
	}

	public RoleException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoleException(Throwable cause) {
		super(cause);
	}

	public RoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
