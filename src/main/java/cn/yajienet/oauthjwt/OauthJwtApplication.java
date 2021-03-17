package cn.yajienet.oauthjwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ServletComponentScan
@MapperScan(basePackages = "cn.yajienet.oauthjwt.dao")
public class OauthJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthJwtApplication.class, args);
    }

}
