package com.atguigu.spzx.order.controller;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import io.netty.util.internal.UnstableApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName OrderInfoController
 * @Description
 * @Author Richard
 * @Date 2023-12-29 10:02
 **/
@Tag(name = "订单管理")
@RestController
@RequestMapping(value="/api/order/orderInfo")
//@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    //确认下单，返回TradeVo，有总金额和商品列表
    @Operation(summary = "确认下单")
    @GetMapping("auth/trade")
    public Result<TradeVo> trade() {
        TradeVo tradeVo = orderInfoService.getTrade();
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    //提交订单，将数据保存到数据库
    @Operation(summary = "提交订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto){
        Long orderId = orderInfoService.submitOrder(orderInfoDto);
        return Result.build(orderId,ResultCodeEnum.SUCCESS);
    }

    //获取订单信息，根据订单id查询订单信息
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/{orderId}")
    public Result<OrderInfo> getOrderInfo(@Parameter(name = "orderId", description = "订单id", required = true) @PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfo(orderId);
        return Result.build(orderInfo, ResultCodeEnum.SUCCESS);
    }

    //立即购买，不经过购物车直接进行购买
    @Operation(summary = "立即购买")
    @GetMapping("auth/buy/{skuId}")
    public Result<TradeVo> buy(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        TradeVo tradeVo = orderInfoService.buy(skuId);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }

    //获取订单分页列表
    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> list(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestParam(required = false, defaultValue = "") Integer orderStatus) {
        PageInfo<OrderInfo> pageInfo = orderInfoService.findOrderPage(page, limit, orderStatus);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //远程调用，根据订单编号获取订单信息
    @Operation(summary = "获取订单信息")
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    public OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo) {
        OrderInfo orderInfo = orderInfoService.getByOrderNo(orderNo) ;
        return orderInfo;
    }

    //远程调用，更新订单状态
    @Operation(summary = "获取订单分页列表")
    @GetMapping("auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    public Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo , @PathVariable(value = "orderStatus") Integer orderStatus) {
        orderInfoService.updateOrderStatus(orderNo , orderStatus);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
