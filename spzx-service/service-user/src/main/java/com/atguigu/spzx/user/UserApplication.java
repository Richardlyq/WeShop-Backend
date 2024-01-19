package com.atguigu.spzx.user;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ClassName UserApplication
 * @Description
 * @Author Richard
 * @Date 2023-12-25 23:00
 **/

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableUserLoginAuthInterceptor
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}