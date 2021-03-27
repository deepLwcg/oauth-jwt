package cn.yajienet.oauthjwt.controller;

import cn.yajienet.oauthjwt.POJO.Result;
import cn.yajienet.oauthjwt.entity.User;
import cn.yajienet.oauthjwt.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 3:19
 */
@Slf4j
@RestController
@Api(tags = "用户管理")
public class UserController {

    @Resource(name = "userServiceImpl")
    private UserService userService;

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

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Result register(@Valid @RequestBody User user, BindingResult bindingResult){
        // 判断是否含有校验不匹配的参数错误
        if (bindingResult.hasErrors()){
            List<FieldError> errors = bindingResult.getFieldErrors();
            Map<String,Object> body = new HashMap<>(errors.size());
            errors.forEach(error -> body.put(error.getField(),error.getDefaultMessage()));
            return Result.builder().code(400).error("User registration failed").message("注册失败").data(body).path("/register").timestamp(new Date()).build();
        }else {
            if (userService.register(user)){
                return Result.builder().code(200).error("User registered successfully").message("注册成功").path("/register").timestamp(new Date()).build();
            }
        }
        return Result.builder().code(400).error("User registration failed").message("注册失败").path("/register").timestamp(new Date()).build();
    }

    @GetMapping(value = "/register/verify")
    public Result verify(@RequestParam(required = false) String username,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone){
        Map<String,String> map = new HashMap<>();
        if(username != null)map.put("username",username);
        if(email != null)map.put("email",email);
        if(phone != null)map.put("phone",phone);
        if (userService.selectCountByMap(map) > 0){
            return Result.builder().code(400).message("已存在").path("/register/verify").timestamp(new Date()).build();
        }
        return Result.builder().code(200).message("不存在").path("/register/verify").timestamp(new Date()).build();
    }



}
