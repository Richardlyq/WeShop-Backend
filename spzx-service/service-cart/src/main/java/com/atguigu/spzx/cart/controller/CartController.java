package com.atguigu.spzx.cart.controller;

import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CartController
 * @Description
 * @Author Richard
 * @Date 2023-12-27 22:23
 **/

@Tag(name = "购物车接口")
@RestController
@RequestMapping("api/order/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //添加商品至购物车
    @Operation(summary = "添加购物车")
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addToCart(@PathVariable Long skuId, @PathVariable Integer skuNum){
        cartService.addToCart(skuId,skuNum);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //购物车列表查询功能
    @Operation(summary = "查询购物车")
    @GetMapping("auth/cartList")
    public Result<List<CartInfo>> cartList() {
        List<CartInfo> cartInfoList = cartService.getCartList();
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }


    //删除购物车中的某个商品
    @Operation(summary = "删除购物车商品")
    @DeleteMapping("auth/deleteCart/{skuId}")
    public Result deleteCart(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //更新购物车商品选中状态
    @Operation(summary="更新购物车商品选中状态")
    @GetMapping("/auth/checkCart/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable(value = "skuId") Long skuId,
                            @PathVariable(value = "isChecked") Integer isChecked) {
        cartService.checkCart(skuId, isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //更新购物车商品全部选中状态
    @Operation(summary="更新购物车商品全部选中状态")
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@Parameter(name = "isChecked", description = "是否选中 1:选中 0:取消选中", required = true)
                                   @PathVariable(value = "isChecked") Integer isChecked){
        cartService.allCheckCart(isChecked);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //清空购物车
    @Operation(summary="清空购物车")
    @GetMapping("/auth/clearCart")
    public Result clearCart(){
        cartService.clearCart();
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //获取购物车中选中的商品信息
    @Operation(summary="选中的购物车")
    @GetMapping(value = "/auth/getAllChecked")
    public List<CartInfo> getAllChecked() {
        List<CartInfo> cartInfoList = cartService.getAllChecked() ;
        return cartInfoList;
    }

    //在service-cart微服务中开发一个清空购物车的接口供service-order微服务进行调用
    @GetMapping(value = "/auth/deleteChecked")
    public Result deleteChecked() {
        cartService.deleteChecked() ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

}
