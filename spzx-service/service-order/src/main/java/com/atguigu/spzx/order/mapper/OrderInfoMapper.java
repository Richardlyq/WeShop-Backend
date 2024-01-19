package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName OrderInfoMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-29 22:15
 **/
@Mapper
public interface OrderInfoMapper {
    //保存到数据库中
    void save(OrderInfo orderInfo);
    //立即购买，根据订单id查询订单信息
    OrderInfo getOrderInfoByorderId(Long orderId);
    //得到所有的订单信息
    List<OrderInfo> findOrderPage(Long userId, Integer orderStatus);
    //远程调用，根据订单编号获取订单信息
    OrderInfo getByorderNo(String orderNo);
    // 更新订单状态
    void updateById(OrderInfo orderInfo);
}
