package cn.yajienet.oauthjwt.oauth;

import cn.yajienet.oauthjwt.POJO.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Description 拦截登陆失败请求
 * @Author WangChenguang
 * @Date 2021-02-07 15:26
 */
@Slf4j
@Component
public class OAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info(exception.getLocalizedMessage());
        response.setStatus(200);
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(Result.builder().code(400).error("error").message(exception.getMessage()).path(request.getRequestURI()).timestamp(new Date()).build()));
        writer.flush();
        writer.close();
    }
}
