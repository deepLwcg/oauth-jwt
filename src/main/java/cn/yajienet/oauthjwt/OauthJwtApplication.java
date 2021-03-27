package cn.yajienet.oauthjwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAsync
@ServletComponentScan
@MapperScan(basePackages = "cn.yajienet.oauthjwt.dao")
@EnableTransactionManagement
public class OauthJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthJwtApplication.class, args);
    }

}
