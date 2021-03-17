package cn.yajienet.oauthjwt.service.impl;

import cn.yajienet.oauthjwt.POJO.User;
import cn.yajienet.oauthjwt.service.RBACService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 14:31
 */
@Slf4j
@Component(value = "RBACService")
public class RBACServiceImpl implements RBACService {
    @Override
    public boolean hasPermission(HttpServletRequest request,  Authentication authentication) {
        log.info(request.getRequestURI());
        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof User){
            // 登录的用户名
            String username = ((User) principal).getUsername();
        }
        return false;
    }
}
