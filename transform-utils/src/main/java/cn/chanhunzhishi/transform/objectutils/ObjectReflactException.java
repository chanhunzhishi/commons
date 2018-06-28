package cn.chanhunzhishi.transform.objectutils;

public class ObjectReflactException extends RuntimeException {
	public ObjectReflactException() {
	}

	public ObjectReflactException(String message) {
		super(message);
	}

	public ObjectReflactException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectReflactException(Throwable cause) {
		super(cause);
	}

	public ObjectReflactException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
