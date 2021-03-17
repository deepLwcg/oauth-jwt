package cn.yajienet.oauthjwt.oauth;

import cn.yajienet.oauthjwt.POJO.User;
import cn.yajienet.oauthjwt.entity.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 18:07
 */
@Slf4j
@Component
public class OAuthSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        //超级管理员有全部权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //登陆状态
        if (authentication != null && !"anonymousUser".equals(authentication.getName())){
            //用户信息
            User user = (User) authentication.getPrincipal();
            if ("SuperAdministrator".equals(user.getUsername())){
                return null;
            }
        }

        //游客的所有权限
        List<Permission> guestPermissions = PermissionsContainer.getInstance().getGuestPermissions();
        for (Permission guestPermission:guestPermissions){
            if (new AntPathRequestMatcher(guestPermission.getUri(),guestPermission.getMethod(),false).matches(request)
                    && guestPermission.getIsEnable()){
                //允许游客访问自己的权限
                return null;
            }
        }


        //从所有权限中找到URL对应的权限送到校验器，用于校验登陆后的用户权限
        List<Permission> permissions = PermissionsContainer.getInstance().getPermissions();
        for (Permission permission:permissions){
            if (new AntPathRequestMatcher(permission.getUri(),permission.getMethod(),false).matches(request)){
                if (permission.getIsEnable()){
                    //除超级管理员以外的用户
                    return PermissionsContainer.getInstance().getConfigAttributes().get(permission.getUri());
                }else {
                    throw new AccessDeniedException("当前访问没有权限");
                }
            }
        }

        //还没有配置权限的URL
        // PermissionsContainer.getInstance().addNotConfigPermissions(request);

        //没有配置的URL权限，除管理员外其她角色是否可以访问
        if (PermissionsContainer.getInstance().isNoConfigPermission()){
            return null;
        }
        throw new AccessDeniedException("当前访问没有权限");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return PermissionsContainer.getInstance().getAllConfigAttributes();
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return  FilterInvocation.class.isAssignableFrom(clazz);
    }
}
