package cn.yajienet.oauthjwt.oauth;

import cn.yajienet.oauthjwt.POJO.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Description 用户未登录时返回给前端的数据
 * @Author WangChenguang
 * @Date 2021-02-07 15:28
 */
@Slf4j
@Component
public class OAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("{} , Path: {}",authException.getLocalizedMessage(), request.getRequestURI());
        response.setStatus(401);
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(Result.builder().code(401).error("Unauthorized").message("需要完成身份验证才能访问此资源").path(request.getRequestURI()).timestamp(new Date()).build()));
        writer.flush();
        writer.close();
    }
}
