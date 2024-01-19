package com.atguigu.spzx.manager;

import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName ManagerApplication
 * @Description
 * @Author Richard
 * @Date 2023-12-07 20:39
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableConfigurationProperties(value = {UserProperties.class, MinioProperties.class})
@EnableScheduling
@EnableLogAspect
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
