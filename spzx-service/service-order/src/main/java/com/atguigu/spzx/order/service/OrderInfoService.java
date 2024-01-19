package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName OrderInfoService
 * @Description
 * @Author Richard
 * @Date 2023-12-29 10:04
 **/

public interface OrderInfoService {
    //确认下单
    TradeVo getTrade();
    //提交订单
    Long submitOrder(OrderInfoDto orderInfoDto);
    //获取订单信息，根据订单id查询订单信息
    OrderInfo getOrderInfo(Long orderId);
    //立即购买，不经过购物车直接进行购买
    TradeVo buy(Long skuId);
    //获取订单分页列表
    PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus);
    //远程调用，根据订单编号获取订单信息
    OrderInfo getByOrderNo(String orderNo);
    //远程调用，更新订单状态
    void updateOrderStatus(String orderNo, Integer orderStatus);
}
