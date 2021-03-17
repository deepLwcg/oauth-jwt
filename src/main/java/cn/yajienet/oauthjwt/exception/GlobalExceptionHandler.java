package cn.yajienet.oauthjwt.exception;

import cn.yajienet.oauthjwt.POJO.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 12:22
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //访问不存在的URL和请求方法
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result NoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException e, HttpServletResponse response){
        String uri = e.getRequestURL();
        return Result.builder().code(400).error("Not Found").message(e.getMessage()).path(uri).timestamp(new Date()).build();
    }
}
