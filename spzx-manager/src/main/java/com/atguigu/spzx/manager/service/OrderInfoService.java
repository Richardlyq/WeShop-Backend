package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;

/**
 * @ClassName OrderInfoService
 * @Description
 * @Author Richard
 * @Date 2023-12-23 10:37
 **/

public interface OrderInfoService {
    //根据开始时间和结束时间查询订单日期及对应的总金额
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
