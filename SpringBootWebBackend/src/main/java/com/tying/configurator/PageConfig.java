package com.tying.configurator;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Tying
 * @version 1.0
 */
@Configuration
@SuppressWarnings("all")
public class PageConfig {

    /**
     * 3.4.0 之前的版本
     * @return
     */
/*    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }*/

    /**
     * 3.4.0 之后版本
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {

        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
