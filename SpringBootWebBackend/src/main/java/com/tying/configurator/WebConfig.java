package com.tying.configurator;

import com.tying.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Tying
 * @version 1.0
 */
@Configuration
@SuppressWarnings("all")
public class WebConfig implements WebMvcConfigurer {

    @Autowired(required = false)
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
/*        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/sys_user/login");*/
    }
}
