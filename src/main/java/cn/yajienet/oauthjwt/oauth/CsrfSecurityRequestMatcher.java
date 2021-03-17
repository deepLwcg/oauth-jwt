package cn.yajienet.oauthjwt.oauth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 17:46
 */
@Slf4j
@Component
public class CsrfSecurityRequestMatcher implements RequestMatcher {

    private Pattern allowedMethods = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    @Override
    public boolean matches(HttpServletRequest request) {
        List<String> abolishUrls = new ArrayList<>();
        //允许post请求的url路径，这只是简单测试，具体要怎么设计这个csrf处理，看个人爱好
        abolishUrls.add("/**");
        if (abolishUrls.size() > 0) {
            String servletPath = request.getServletPath();
            for (String url : abolishUrls) {
                if (servletPath.contains(url)) {
                    return false;
                }
            }
        }
        return !allowedMethods.matcher(request.getMethod()).matches();
    }

}
