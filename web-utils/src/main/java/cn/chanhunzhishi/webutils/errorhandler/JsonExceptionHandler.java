package cn.chanhunzhishi.webutils.errorhandler;


import cn.chanhunzhishi.webutils.restutil.JsonResult;
import cn.chanhunzhishi.webutils.restutil.JsonResultBuilder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class JsonExceptionHandler {

    @ExceptionHandler(value = BaseRestException.class)
    public JsonResult<?> resolveException(HttpServletRequest request, HttpServletResponse response, BaseRestException e) {
		e.printStackTrace();
		Throwable cause = e.getCause();
		Class<? extends Exception> aClass = e.getClass();
		System.out.println(cause + aClass.toString());
		return JsonResultBuilder.buildError(1,"异常");
    }

}