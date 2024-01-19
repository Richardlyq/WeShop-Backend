package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName OrderInfoMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-23 10:11
 **/
@Mapper
public interface OrderInfoMapper {
    //2.查询前一天的订单信息
    OrderStatistics selectOrderStatistics(String createTime);
}
