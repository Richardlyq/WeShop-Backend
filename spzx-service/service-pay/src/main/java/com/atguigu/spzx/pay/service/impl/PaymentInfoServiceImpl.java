package com.atguigu.spzx.pay.service.impl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.feign.order.OrderFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.pay.mapper.PaymentInfoMapper;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName PaymentInfoServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-30 21:38
 **/
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    //保存支付记录到支付记录表
    @Override
    public PaymentInfo savePaymentInfo(String orderNo) {
        //根据orderNo查询支付记录,如果已经存在就不用保存了（一个订单支付失败以后可以继续支付）
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(orderNo);
        if (paymentInfo == null){
            paymentInfo = new PaymentInfo();
            //远程调用获得订单信息
            OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(orderNo);
            //封装数据到paymentInfo中
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String content = "";
            for(OrderItem item : orderInfo.getOrderItemList()) {
                content += item.getSkuName() + " ";
            }
            paymentInfo.setContent(content);
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setOrderNo(orderNo);
            paymentInfo.setPaymentStatus(0);
            //添加数据
            paymentInfoMapper.save(paymentInfo);
        }
        return paymentInfo;
    }

    // 正常的支付成功，我们应该更新交易记录状态
    @Override
    public void updatePaymentStatus(Map<String, String> paramMap, Integer payType) {
        // 查询PaymentInfo
        PaymentInfo paymentInfo = paymentInfoMapper.getByOrderNo(paramMap.get("out_trade_no"));
        if (paymentInfo.getPaymentStatus() == 1) {
            return;
        }

        //更新支付信息
        paymentInfo.setPaymentStatus(1);
        paymentInfo.setOutTradeNo(paramMap.get("trade_no"));
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(JSON.toJSONString(paramMap));
        //更新数据
        paymentInfoMapper.updateById(paymentInfo);

        //更新订单状态
        orderFeignClient.updateOrderStatus(paymentInfo.getOrderNo(),payType);

        //更新商品销量
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByOrderNo(paymentInfo.getOrderNo());
        List<SkuSaleDto> skuSaleDtoList = orderInfo.getOrderItemList().stream().map(item -> {
            SkuSaleDto skuSaleDto = new SkuSaleDto();
            skuSaleDto.setSkuId(item.getSkuId());
            skuSaleDto.setNum(item.getSkuNum());
            return skuSaleDto;
        }).collect(Collectors.toList());
        productFeignClient.updateSkuSaleNum(skuSaleDtoList) ;
    }
}
