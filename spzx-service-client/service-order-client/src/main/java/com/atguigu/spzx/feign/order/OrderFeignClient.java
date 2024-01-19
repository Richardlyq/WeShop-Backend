package com.atguigu.spzx.feign.order;

import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @ClassName OrderFeignClient
 * @Description
 * @Author Richard
 * @Date 2023-12-30 22:17
 **/
@FeignClient(value = "service-order")
public interface OrderFeignClient {
    //根据订单号获取订单信息
    @GetMapping("/api/order/orderInfo/auth/getOrderInfoByOrderNo/{orderNo}")
    OrderInfo getOrderInfoByOrderNo(@PathVariable String orderNo);

    //更新订单状态
    @GetMapping("/api/order/orderInfo/auth/updateOrderStatusPayed/{orderNo}/{orderStatus}")
    Result updateOrderStatus(@PathVariable(value = "orderNo") String orderNo , @PathVariable(value = "orderStatus") Integer orderStatus) ;
}
