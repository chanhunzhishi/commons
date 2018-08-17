package cn.chanhunzhishi.transform.current;

public class CacheDateException extends RuntimeException {
	public CacheDateException() {
	}

	public CacheDateException(String message) {
		super(message);
	}

	public CacheDateException(String message, Throwable cause) {
		super(message, cause);
	}

	public CacheDateException(Throwable cause) {
		super(cause);
	}

	public CacheDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
