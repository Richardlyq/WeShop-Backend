package com.atguigu.spzx.common.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.Servlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

/**
 * @ClassName UserTokenFeignInterceptor
 * @Description
 * @Author Richard
 * @Date 2023-12-29 12:03
 **/

public class UserTokenFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = 
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String token = request.getHeader("token");
        requestTemplate.header("token",token);
    }
}
