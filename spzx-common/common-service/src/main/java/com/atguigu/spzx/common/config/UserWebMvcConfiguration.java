package com.atguigu.spzx.common.config;

import com.atguigu.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName UserWebMvcConfiguration
 * @Description
 * @Author Richard
 * @Date 2023-12-26 21:51
 **/
//完成拦截器的注册
public class UserWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserLoginAuthInterceptor userLoginAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .addPathPatterns("/api/**");
    }
}
