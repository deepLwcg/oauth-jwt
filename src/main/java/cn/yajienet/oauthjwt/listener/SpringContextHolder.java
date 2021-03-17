package cn.yajienet.oauthjwt.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @Description
 * @Author WangChenguang
 * @Date 2021-02-07 3:31
 */
@Component
public class SpringContextHolder implements ApplicationContextAware, ServletContextAware {

    private static ServletContext servletContext;

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringContextHolder.applicationContext==null){
            SpringContextHolder.applicationContext = applicationContext;
        }
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        if(SpringContextHolder.servletContext==null){
            SpringContextHolder.servletContext = servletContext;
        }
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName)
    {
        return applicationContext.getBean(beanName);
    }
    public static <T> T getBean(Class<T> beanName) {
        return applicationContext.getBean(beanName);
    }
}
