package cn.yajienet.oauthjwt.controller;

import cn.yajienet.oauthjwt.POJO.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 4:11
 */
@RestController
@Api(tags = "主页管理")
public class HomeController {

    @GetMapping(value = "/")
    public Object index(){
        return Result.builder().code(200).message("这是Oauth JWT 认证，主要用于APP的登陆！").build();
    }

}
