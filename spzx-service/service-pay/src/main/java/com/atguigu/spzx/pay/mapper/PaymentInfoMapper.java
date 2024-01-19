package com.atguigu.spzx.pay.mapper;

import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName PaymentInfoMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-30 21:39
 **/
@Mapper
public interface PaymentInfoMapper {
    //根据orderNo查询支付记录
    PaymentInfo getByOrderNo(String orderNo);
    //添加数据
    void save(PaymentInfo paymentInfo);

    void updateById(PaymentInfo paymentInfo);
}
