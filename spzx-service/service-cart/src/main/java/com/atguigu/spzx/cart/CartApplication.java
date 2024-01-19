package com.atguigu.spzx.cart;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName CartApplication
 * @Description
 * @Author Richard
 * @Date 2023-12-27 22:21
 **/

// exclude排除数据库的自动化配置，Cart微服务不需要访问MySQL数据库
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients(basePackages = {"com.atguigu.spzx"})
@EnableUserLoginAuthInterceptor
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class , args) ;
    }

}