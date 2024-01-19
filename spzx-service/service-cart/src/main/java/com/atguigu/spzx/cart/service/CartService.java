package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.h5.CartInfo;

import java.util.List;

/**
 * @ClassName CartService
 * @Description
 * @Author Richard
 * @Date 2023-12-27 22:24
 **/

public interface CartService {
    //添加商品至购物车
    void addToCart(Long skuId, Integer skuNum);
    //购物车列表查询功能
    List<CartInfo> getCartList();
    //删除购物车中的某个商品
    void deleteCart(Long skuId);
    //更新购物车商品选中状态
    void checkCart(Long skuId, Integer isChecked);
    //更新购物车商品全部选中状态
    void allCheckCart(Integer isChecked);
    //清空购物车
    void clearCart();
    //获取购物车中选中的商品信息
    List<CartInfo> getAllChecked();
    //在service-cart微服务中开发一个清空购物车的接口供service-order微服务进行调用
    void deleteChecked();
}
