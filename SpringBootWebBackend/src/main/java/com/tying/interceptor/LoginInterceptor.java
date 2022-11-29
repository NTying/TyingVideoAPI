package com.tying.interceptor;

import com.tying.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录接口拦截器
 * 使用了 SpringSecurity 后登录拦截器就不用了
 * @author Tying
 * @version 1.0
 */
@Component
@SuppressWarnings("all")
public class LoginInterceptor implements HandlerInterceptor {

}
