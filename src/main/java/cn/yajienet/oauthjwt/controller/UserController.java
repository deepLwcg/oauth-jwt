package cn.yajienet.oauthjwt.controller;

import cn.yajienet.oauthjwt.POJO.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 3:19
 */
@Slf4j
@RestController
@Api(tags = "用户管理")
public class UserController {

    @GetMapping(value = "/test")
    public Result test(){
        return Result.builder().code(200).build();
    }

    @GetMapping(value = "/login")
    public Result login(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!"anonymousUser".equals(authentication.getName()) && authentication.isAuthenticated()){
            return Result.builder().code(200).message("已经登陆").path("/login").timestamp(new Date()).build();
        }
        return Result.builder().code(401).error("Unauthorized").message("需要完成身份验证才能访问此资源").path("/login").timestamp(new Date()).build();
    }

}
