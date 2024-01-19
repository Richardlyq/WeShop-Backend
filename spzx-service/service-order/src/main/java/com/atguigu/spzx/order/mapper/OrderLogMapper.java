package com.atguigu.spzx.order.mapper;

import com.atguigu.spzx.model.entity.order.OrderLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName OrderLogMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-29 22:15
 **/
@Mapper
public interface OrderLogMapper {
    //添加数据到order_log表中，记录操作日志
    void save(OrderLog orderLog);
}
