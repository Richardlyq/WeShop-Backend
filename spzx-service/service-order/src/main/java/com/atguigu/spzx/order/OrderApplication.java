package com.atguigu.spzx.order;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.common.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName OrderApplication
 * @Description
 * @Author Richard
 * @Date 2023-12-29 9:58
 **/

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.atguigu.spzx"
})
/*
service-order远程调用service-cart过程中，不是通过ajax请求调用，所以请求头里面的token是传不过去的
解决办法：需要使用feign拦截器拦截请求，获取token，重新传递token
*/
@EnableUserTokenFeignInterceptor
@EnableUserLoginAuthInterceptor
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class , args) ;
    }

}