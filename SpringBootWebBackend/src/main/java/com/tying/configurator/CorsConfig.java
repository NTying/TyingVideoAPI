package com.tying.configurator;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Tying
 * @version 1.0
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        // 设置允许跨域的路径（接口，这是后端服务的接口路径）
        registry.addMapping("/**")
                // 设置允许跨域请求的域名（这是发起请求的域）
                .allowedOriginPatterns("*")
                // 是否允许 cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的 header 属性
                .allowedHeaders("*")
                // 跨域允许时间（如果跨域被允许，这段时间都是被允许的，浏览器就不用每次都在请求头中添加跨域请求相关信息了）
                .maxAge(3600);
    }
}
