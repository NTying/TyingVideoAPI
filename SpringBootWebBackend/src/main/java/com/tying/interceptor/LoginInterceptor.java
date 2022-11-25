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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 获取请求头中的 token
        String token = request.getHeader("token");
        // 如果 token 为空，代表为登录，提醒重新登录（401）
        if (!StringUtils.hasText(token)) {
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            // 因为定义异常处理的类和相关方法，所以只需要抛出异常即可
            throw new RuntimeException("未登录，请登录后重试");
            // return false;
        }
        // 如果不为空，解析 token
        try {
            Claims claims = JwtUtils.parseJWT(token);
            String subject = claims.getSubject();
            System.out.println(subject);
        } catch (Exception e) {
            // 如果解析出现异常，说明未登录，提醒重新登录
            // response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            throw new RuntimeException("未登录，请登录后重试");
            // return false;
        }
        // token解析成功，说明是登录状态，允许请求转发
        return true;
    }
}
