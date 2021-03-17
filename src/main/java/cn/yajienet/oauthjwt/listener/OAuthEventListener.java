package cn.yajienet.oauthjwt.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 9:27
 */
@Slf4j
@Component
public class OAuthEventListener {

    @EventListener
    public void publicInvocation(PublicInvocationEvent event){
        FilterInvocation filterInvocation = (FilterInvocation) event.getSource();
        HttpServletRequest request = filterInvocation.getHttpRequest();
        try {
            log.info("URL:{} {}",request.getRequestURI(),request.getMethod());
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }
}
