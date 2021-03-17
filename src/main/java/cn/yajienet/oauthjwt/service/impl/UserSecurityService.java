package cn.yajienet.oauthjwt.service.impl;

import cn.yajienet.oauthjwt.POJO.User;
import cn.yajienet.oauthjwt.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 13:59
 */
@Service
public class UserSecurityService implements UserDetailsService {

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return new User("SuperAdministrator",new BCryptPasswordEncoder().encode("123456"),new LinkedList<>());
    }



}
