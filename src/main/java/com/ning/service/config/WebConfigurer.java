package com.ning.service.config;

import com.ning.service.Interceptor.UserLoginInterceptorBySpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author shenjiang
 * @Description:
 * @Date: 2019/8/14 10:30
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private UserLoginInterceptorBySpring userLoginInterceptorBySpring;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptorBySpring).addPathPatterns("/**");
    }
}
