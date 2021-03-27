package cn.yajienet.oauthjwt.oauth;

import cn.yajienet.oauthjwt.POJO.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Description 拦截权限不足的请求
 * @Author WangChenguang
 * @Date 2021-02-07 15:23
 */
@Slf4j
@Component
public class OAuthAccessDeniedHandler extends AccessDeniedHandlerImpl {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info(accessDeniedException.getLocalizedMessage());
        response.setStatus(400);
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(Result.builder().code(403).error("Access Denied").message("权限不足").path(request.getRequestURI()).timestamp(new Date()).build()));
        writer.flush();
        writer.close();
    }
}
