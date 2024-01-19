package com.atguigu.spzx.feign.user;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName UserFeignClient
 * @Description
 * @Author Richard
 * @Date 2023-12-29 22:21
 **/

@FeignClient(value = "service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    UserAddress getUserAddress(@PathVariable Long id) ;

}