package com.tying.configurator;

import com.tying.filter.JwtAuthenticationTokenFilter;
import com.tying.handler.SelfDefLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author Tying
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
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
                .antMatchers("/user/login").anonymous()
                // 一下配置相当于在接口中添加 @PreAuthorize，但这里是调用官方的 hasAuthority，应该也有方法去调用自定义的
                //.antMatchers("/test").hasAuthority("user:likes:index")
                // 除上面外的所有请求全部需要认证后才能访问
                .anyRequest().authenticated();

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
