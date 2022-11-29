package com.tying.expression;

import com.tying.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tying
 * @version 1.0
 * 这是自定义的 Bean 而且里面的方法和 SpringSecurity 中提供的相同，要是用它，先要给它起一个名字，不然怎么找
 */
@Component(value = "security_ex")
public class ReDefSecurityExpression {

    public boolean hasAuthority(String authority) {
        // 获取当前用户的权限（因为都要经过 JwtAuthenticationTokenFilter，且认证信息会存入 SecurityContextHolder，所以从里面取）
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<String> permissions = loginUser.getPermissions();
        // 判断用户权限集合中是否存在 authority
        return permissions.contains(authority);
    }
}
