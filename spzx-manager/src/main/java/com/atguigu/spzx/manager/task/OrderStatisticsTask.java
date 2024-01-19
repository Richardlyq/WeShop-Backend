package com.atguigu.spzx.manager.task;

import cn.hutool.core.date.DateUtil;
import com.atguigu.spzx.manager.mapper.OrderInfoMapper;
import com.atguigu.spzx.manager.mapper.OrderStatisticsMapper;
import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName OrderStatisticsTask
 * @Description
 * @Author Richard
 * @Date 2023-12-23 10:11
 **/
@Component
public class OrderStatisticsTask {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderStatisticsMapper orderStatisticsMapper;


//    @Scheduled(cron = "0/10 * * * * ?") // TODO 为了测试，每隔10秒执行一次
    @Scheduled(cron = "0 0 2 * * ? ")
    public void  orderTotalAmountStatistics(){
        //1.获取前一天的日期
        String createTime = DateUtil.offsetDay(new Date(), -1).toString("yyyy-MM-dd");
        //2.查询前一天的订单信息
        OrderStatistics orderStatistics = orderInfoMapper.selectOrderStatistics(createTime);
        //3.将查询到的订单信息插入到order_statistics表中
        if(orderStatistics != null){
            orderStatisticsMapper.insert(orderStatistics);
        }
    }

}
