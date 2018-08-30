package cn.chanhunzhishi.transform.colllectionutils;

public class CollectionsException extends RuntimeException {
	public CollectionsException() {
	}

	public CollectionsException(String message) {
		super(message);
	}

	public CollectionsException(String message, Throwable cause) {
		super(message, cause);
	}

	public CollectionsException(Throwable cause) {
		super(cause);
	}

	public CollectionsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
