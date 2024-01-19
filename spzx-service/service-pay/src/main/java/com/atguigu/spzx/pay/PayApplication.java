package com.atguigu.spzx.pay;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.pay.properties.AlipayProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ClassName PayApplication
 * @Description
 * @Author Richard
 * @Date 2023-12-30 11:31
 **/

@SpringBootApplication
@EnableUserLoginAuthInterceptor
@EnableFeignClients(basePackages = {"com.atguigu.spzx"})
@EnableConfigurationProperties(value = { AlipayProperties.class })
public class PayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class , args) ;
    }

}
