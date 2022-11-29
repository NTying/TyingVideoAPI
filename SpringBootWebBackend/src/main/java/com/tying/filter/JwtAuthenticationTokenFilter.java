package com.tying.filter;

import com.tying.domain.LoginUser;
import com.tying.utils.JsonRedisUtils;
import com.tying.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author Tying
 * @version 1.0
 *
 * OncePerRequestFilter：SpringSecurity过滤器链中的一个过滤器，该类能保证请求只经过该过滤器一次。
 */
@Component
@SuppressWarnings("all")
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private JsonRedisUtils<LoginUser> jsonRedisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 获取请求头中的 token
        String token = request.getHeader("token");
        if (!StringUtils.hasLength(token)) {
            // 当前过滤器直接放行，因为后面的过滤器还会进行校验
            filterChain.doFilter(request, response);
            // 为什么要return？因为响应的时候，还要反过来走一遍过滤器链，如果不 return，就会执行下面的解析 token的代码
            // 但是 token 又不存在，所以会出错
            return;
        }

        // 解析 token 获取 userId
        String userId;
        try {
            Claims claims = JwtUtils.parseJWT(token);
            userId = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("非法 token");
        }

        // 根据 userId 从 redis 获取用户信息
        String redisKey = "login:" + userId;
        LoginUser loginUser = jsonRedisUtils.getValue(redisKey);

        // TODO 获取权限信息封装到 Authentication 对象中
        List<GrantedAuthority> authorities = (List<GrantedAuthority>) loginUser.getAuthorities();
        // 存入 SecurityContextHolder
        // 同一个请求中 SecurityContext 对象是同一个，此处 SecurityContextHolder 会关联一个当前线程的 SecurityContext
        if (Objects.nonNull(loginUser)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                    UsernamePasswordAuthenticationToken(loginUser, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        // 放行
        filterChain.doFilter(request, response);
    }
}
