package me.ffs.www.conf;

import com.github.gserv.serv.commons.security.XSSSecurityFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.ffs.www.interceptor.LoginInterceptor;


public class WebMvcConfg implements WebMvcConfigurer {

     // 自定义拦截器
   public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")// 拦截哪个请求
		.excludePathPatterns("/m/login/**","404","500") // 不拦截哪个请求,设置后这些接口不会进拦截器
		.excludePathPatterns("/css/**","/js/**","/img/**");// 设置静态资源访问区,不能加static

    }

    @Bean
    public FilterRegistrationBean XSSFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new XSSSecurityFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("securityconfig","classpath:xss_security_config.xml");
        //--白名单参数 暂未使用
        registration.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico");
        registration.setName("XSSSecurityFilter");
        registration.setOrder(1);
        return registration;
    }
}
