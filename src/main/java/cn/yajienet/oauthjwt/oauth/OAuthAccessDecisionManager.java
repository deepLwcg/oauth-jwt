package cn.yajienet.oauthjwt.oauth;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Description 校验用户访问权限
 * @Author WangChenguang
 * @Date 2021-02-07 15:27
 */
@Slf4j
@Component
public class OAuthAccessDecisionManager implements AccessDecisionManager {

    /**
     * 判定是否拥有权限的决策方法
     * @param authentication 用户拥有的权限
     * @param object HttpServletRequest
     * @param configAttributes 从数据库中查到的所有权限
     * @throws AccessDeniedException 用于抛出无权限异常
     * @throws InsufficientAuthenticationException 暂时不知
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        String name = null,method = null,isEnable = null;
        if (configAttributes.size() >= 3){
            name = iterator.next().getAttribute();
            method = iterator.next().getAttribute();
            isEnable = iterator.next().getAttribute().toLowerCase();
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority:authorities){
            if (grantedAuthority instanceof OAuthGrantedAuthority){
                OAuthGrantedAuthority authGrantedAuthority = (OAuthGrantedAuthority) grantedAuthority;
                if (StringUtils.equals(authGrantedAuthority.getPermission(), name) &&
                        StringUtils.equals(authGrantedAuthority.getMethod(), method) &&
                        StringUtils.equals(isEnable, "true"))
                    // 说明此URL地址符合权限,可以放行
                    return;
            }
        }
        //没有权限
        throw new AccessDeniedException("没有权限");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
