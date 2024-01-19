package com.atguigu.spzx.feign.cart;

import com.atguigu.spzx.model.entity.h5.CartInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @ClassName CartFeignClient
 * @Description
 * @Author Richard
 * @Date 2023-12-29 10:28
 **/
@FeignClient(value = "service-cart")
public interface CartFeignClient {
    @GetMapping("/api/order/cart/auth/getAllChecked")
    List<CartInfo> getAllChecked();

    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    void deleteChecked() ;
}
