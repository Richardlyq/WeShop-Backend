package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName OrderItemMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-29 22:15
 **/
@Mapper
public interface OrderItemMapper {
    //5.添加数据到order_item表中，封装orderItem数据
    void save(OrderItem orderItem);
    //根据订单id查询订单项列表
    List<OrderItem> getItemListByOrderId(Long id);
}
