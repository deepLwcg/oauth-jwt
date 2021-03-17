package cn.yajienet.oauthjwt.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 14:30
 */
public interface RBACService {
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
