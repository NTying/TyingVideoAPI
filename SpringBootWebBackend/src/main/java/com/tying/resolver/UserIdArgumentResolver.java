package com.tying.resolver;

import com.tying.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Tying
 * @version 1.0
 */
@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * 判断方法参数是否能使用当前的参数解析器进行解析
     * 还要自定义注解，如@CurrentUserId，参数前有该注解就会使用该解析器，并且该参数会被封装成 MethodParameter 对象
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 判断参数是否具有 @CurrentUserId 注解（有就能被解析器解析）
        return parameter.hasParameterAnnotation(CurrentUserId.class);
    }

    /**
     * 进行参数解析的方法
     * 方法的返回值就会赋值给对应的方法参数（加了特定注解的参数）
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 获取请求头中的 token
        String token = webRequest.getHeader("token");
        if (StringUtils.hasText(token)) {
            Claims claims = JwtUtils.parseJWT(token);
            String userId = claims.getSubject();
            return userId;
        }
        return null;
    }
}
