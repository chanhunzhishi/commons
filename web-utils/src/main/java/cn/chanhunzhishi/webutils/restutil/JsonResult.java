package cn.chanhunzhishi.webutils.restutil;

import lombok.Data;

import java.io.Serializable;

@Data
public class JsonResult<T> implements Serializable {
	private int code;
	private boolean status;
	private String message;
	private T data;

	public  JsonResult() {
	}

	public JsonResult(int code, boolean status, String message, T data) {
		this.code = code;
		this.status = status;
		this.message = message;
		this.data = data;
	}
}
