package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName OrderStatisticsMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-23 10:12
 **/
@Mapper
public interface OrderStatisticsMapper {
    //3.将查询到的订单信息插入到order_statistics表中
    void insert(OrderStatistics orderStatistics);
//    查询统计结果数据,返回OrderStatistics
    List<OrderStatistics> selectList(OrderStatisticsDto orderStatisticsDto);
}
