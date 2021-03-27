package cn.yajienet.oauthjwt.oauth;

import cn.yajienet.oauthjwt.POJO.Result;
import cn.yajienet.oauthjwt.POJO.User;
import cn.yajienet.oauthjwt.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * @Description 拦截登陆成功的请求，登陆成功后在此可做其它操作
 * @Author WangChenguang
 * @Date 2021-02-07 15:20
 */
@Slf4j
@Component
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private JwtUtil jwtUtils;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("登陆成功");
        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateToken(user.getUsername());
        jwtUtils.setExpire(jwtToken,user.getUsername());
        response.setHeader("Authorization", "Bearer " + jwtToken);
        response.setStatus(200);
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.print(mapper.writeValueAsString(Result.builder().code(200).message("登陆成功").data(jwtToken).path(request.getRequestURI()).timestamp(new Date()).build()));
        writer.flush();
        writer.close();
    }
}
