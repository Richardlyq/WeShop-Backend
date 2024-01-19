package com.atguigu.spzx.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @ClassName ProductApplication
 * @Description
 * @Author Richard
 * @Date 2023-12-24 15:54
 **/

@SpringBootApplication
@EnableCaching
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

}
