package cn.yajienet.oauthjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.service.Contact;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 2:39
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String SWAGGER_SCAN_BASE_PACKAGE="cn.yajienet.oauthjwt.controller";
    private static final String VERSION = "1.0.0";

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("oauth-jwt")
                .description("This is to show API description of oauth-jwt")
                // 作者信息
                .contact(new Contact("wcg", "http://www.yajienet.cn", "975696229@qq.com"))
                .version(VERSION)
                .build();

    }
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // 是否开启
                .enable(true).select()
                // 扫描的路径包
                .apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any()).build().pathMapping("/");
    }
}
