package cn.chanhunzhishi.webutils.restutil;

public class JsonResultBuilder {
	private JsonResultBuilder() {
	}

	public static JsonResult<?> buildVoid() {
		return new JsonResult(0, true, null, null);
	}

	public static <T> JsonResult<T> buildData(T data) {
		return new JsonResult(0, true, null, data);
	}

	public static JsonResult<?> buildError(int code, String message) {
		return new JsonResult(code, true, message, null);
	}

	public static <T> JsonResult<T> build(int code, boolean status, String message, T t) {
		return new JsonResult(code, status, message, t);
	}
}
