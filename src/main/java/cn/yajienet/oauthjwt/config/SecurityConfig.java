package cn.yajienet.oauthjwt.config;

import cn.yajienet.oauthjwt.filter.JWTAuthenticationFilter;
import cn.yajienet.oauthjwt.oauth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 2:43
 */
@Configuration
// 这个注解必须加，开启Security
@EnableWebSecurity
//开启Security的权限注解
//@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Resource(name = "userSecurityService")
    private UserDetailsService userDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers(staticPathPattern).mvcMatchers("/favicon.ico");
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/v2/**")
                .antMatchers("/swagger-resources/**")
                .mvcMatchers("/webjars/**");
    }

    @Autowired
    private OAuthAccessDecisionManager authAccessDecisionManager;
    @Autowired
    private OAuthAccessDeniedHandler oAuthAccessDeniedHandler;
    @Autowired
    private OAuthEntryPoint oAuthEntryPoint;
    @Autowired
    private OAuthFailureHandler oAuthFailureHandler;
    @Autowired
    private OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
    @Autowired
    private OAuthLogoutSuccessHandler oAuthLogoutSuccessHandler;
    @Autowired
    private OAuthSecurityMetadataSource oAuthSecurityMetadataSource;
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        // 跨站请求伪造CSRF
        http.csrf().disable();
//        http.csrf().ignoringRequestMatchers(new CsrfSecurityRequestMatcher());
        // 跨域请求
        http.cors().disable();
        // 记住密码
        http.rememberMe().disable();
        // 关闭Session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        //设置自定义的访问控制
        http.authorizeRequests().withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                object.setSecurityMetadataSource(oAuthSecurityMetadataSource);
                object.setAccessDecisionManager(authAccessDecisionManager);
                //发布授权成功的消息
                object.setPublishAuthorizationSuccess(true);

                return object;
            }
        });

        http.formLogin()
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                // 登陆成功
                .successHandler(oAuthLoginSuccessHandler)
                // 登陆失败
                .failureHandler(oAuthFailureHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                // 注销成功
                .logoutSuccessHandler(oAuthLogoutSuccessHandler)
                .permitAll()
                .and()
                .exceptionHandling()
                //用来解决认证过的用户访问无权限资源时的异常
                .accessDeniedHandler(oAuthAccessDeniedHandler)
                //用来解决匿名用户访问无权限资源时的异常
                .authenticationEntryPoint(oAuthEntryPoint);
    }

}
