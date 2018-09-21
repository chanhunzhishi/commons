package cn.chanhunzhishi.webutils.errorhandler.exceptions;

import cn.chanhunzhishi.webutils.errorhandler.BaseRestException;

/**
 * 数据记录值异常
 */
public class DataRecordException extends BaseRestException {
	public DataRecordException() {
	}

	public DataRecordException(String message) {
		super(message);
	}

	public DataRecordException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataRecordException(Throwable cause) {
		super(cause);
	}

	public DataRecordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
