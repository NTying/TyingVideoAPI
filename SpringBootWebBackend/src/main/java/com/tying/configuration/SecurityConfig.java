package com.tying.configuration;

import com.tying.filter.JwtAuthenticationTokenFilter;
import com.tying.handler.SelfDefLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @author Tying
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    /**
     * 配置可注入 Spring 容器中的 Bean
     * 这里最终可注入的是 PasswordEncoder 的实现类对象
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置可注入 Spring 容器中的 Bean
     * 这里最终可注入的是 AuthenticationManager 的实现类对象
     * @return
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // 关闭 CSRF（不关闭的话，在发起请求时，要在header中设置 csrf_token
        http.csrf().disable()
                // 不通过 Session 获取 SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口，只允许匿名访问（认证后不允许访问）
                .antMatchers("/app/user/login").anonymous()
                .antMatchers("/app/user/logout").authenticated()
                .antMatchers("/app//user/full").authenticated()
                .antMatchers("/app/user/modify").authenticated()
                .antMatchers("/uploadImg").authenticated()
                .antMatchers("/uploadVideo").authenticated()
                .antMatchers("/app/video/updateInteractCount").authenticated()
                .antMatchers("/app/video/getUserInteractVideo").authenticated()
                .antMatchers("/app/video/flagsInteractWithCurrentUser").authenticated()
                .antMatchers("/app/video/getMyCollectVideos").authenticated()
                // 一下配置相当于在接口中添加 @PreAuthorize，但这里是调用官方的 hasAuthority，应该也有方法去调用自定义的
                //.antMatchers("/test").hasAuthority("user:likes:index")
                // 除上面外配置的请求按照配置规则，其他所有请求都放行
                .anyRequest().permitAll();

        // 添加一个过滤器在 UsernamePasswordAuthenticationFilter 过滤器前
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置异常处理器
        http.exceptionHandling()
                // 配置认证失败处理器
                .authenticationEntryPoint(authenticationEntryPoint)
                // 配置授权失败处理器
                .accessDeniedHandler(accessDeniedHandler);

        // 允許跨域
        http.cors();

        // 配置登出成功处理器
        http.logout()
                .logoutSuccessHandler(new SelfDefLogoutSuccessHandler());
    }
}
