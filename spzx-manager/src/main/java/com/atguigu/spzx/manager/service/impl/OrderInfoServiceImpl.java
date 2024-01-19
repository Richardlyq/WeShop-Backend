package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.manager.service.OrderInfoService;
import com.atguigu.spzx.model.dto.order.OrderStatisticsDto;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import com.atguigu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName OrderInfoServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-23 10:37
 **/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;

    //根据开始时间和结束时间查询订单日期及对应的总金额
    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        //查询统计结果数据,返回OrderStatistics
        List<OrderStatistics> orderStatisticsList = orderStatisticsMapper.selectList(orderStatisticsDto);
        //遍历统计结果数据，将其中的日期封装到一个新的list集合中
        List<String> dateList = orderStatisticsList.stream().map(orderStatistics ->
                        DateUtil.format(orderStatistics.getOrderDate(), "yyyy-MM-dd"))//转格式
                .collect(Collectors.toList());
        //遍历统计结果数据，将其中的总金额封装到一个新的list集合中
        List<BigDecimal> amountList = orderStatisticsList.stream().map(orderStatistics ->
                orderStatistics.getTotalAmount()).collect(Collectors.toList());

        //封装到OrderStatisticsVo中
        OrderStatisticsVo orderStatisticsVo = new OrderStatisticsVo();
        orderStatisticsVo.setDateList(dateList);
        orderStatisticsVo.setAmountList(amountList);
        return orderStatisticsVo;
    }

}
