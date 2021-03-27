package cn.yajienet.oauthjwt.service.impl;

import cn.yajienet.oauthjwt.POJO.User;
import cn.yajienet.oauthjwt.entity.Permission;
import cn.yajienet.oauthjwt.entity.Role;
import cn.yajienet.oauthjwt.oauth.OAuthGrantedAuthority;
import cn.yajienet.oauthjwt.service.PermissionsService;
import cn.yajienet.oauthjwt.service.RoleService;
import cn.yajienet.oauthjwt.service.UserService;
import cn.yajienet.oauthjwt.utils.RegExUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 13:59
 */
@Service
public class UserSecurityServiceImpl implements UserDetailsService {

    @Resource(name = "userServiceImpl")
    private UserService userService;
    @Resource(name = "roleServiceImpl")
    private RoleService roleService;
    @Resource(name = "permissionsServiceImpl")
    private PermissionsService permissionsService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        cn.yajienet.oauthjwt.entity.User user = userService.selectByEmailOrUsernameOrPhone(s);
        if (user == null) throw new UsernameNotFoundException("用户不存在");
        List<Role> roles = roleService.selectByUserId(user.getId());
        if (roles == null || roles.size() <= 0) throw new UsernameNotFoundException("未分配角色");
        List<OAuthGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            List<Permission> permissions = permissionsService.selectByRoleId(role.getId());
            for (Permission permission : permissions) {
                authorities.add(
                        OAuthGrantedAuthority.builder()
                                .permission(permission.getUri())
                                .method(permission.getMethod())
                                .isEnable(permission.getIsEnable().toString())
                                .build()
                );
            }
        }

        return new User(user.getId(), user.getUserName(), user.getPassword(), roles, authorities);
    }


}
