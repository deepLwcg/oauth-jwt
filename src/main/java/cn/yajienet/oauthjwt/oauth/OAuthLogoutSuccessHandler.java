package cn.yajienet.oauthjwt.oauth;

import cn.yajienet.oauthjwt.POJO.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Description 拦截登出成功的请求
 * @Author WangChenguang
 * @Date 2021-02-07 15:22
 */
@Slf4j
@Component
public class OAuthLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("注销成功");
        response.setStatus(200);
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(Result.builder().code(200).message("注销成功").path(request.getRequestURI()).timestamp(new Date()).build()));
        writer.flush();
        writer.close();
    }
}
