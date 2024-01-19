package com.atguigu.spzx.pay.service.impl;
import com.alibaba.fastjson2.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.entity.pay.PaymentInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.pay.properties.AlipayProperties;
import com.atguigu.spzx.pay.service.AlipayService;
import com.atguigu.spzx.pay.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @ClassName AlipayServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-30 21:35
 **/

@Service
public class AlipayServiceImpl implements AlipayService {


    @Autowired
    private PaymentInfoService paymentInfoService;

    @Autowired
    private AlipayProperties alipayProperties;

    @Autowired
    private AlipayClient alipayClient;

    //支付宝下单
    @Override
    public String submitAlipay(String orderNo) {
        //保存支付记录
        PaymentInfo paymentInfo = paymentInfoService.savePaymentInfo(orderNo);

        //构建需要的参数，调用支付宝服务接口
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 同步回调, 支付成功后，支付宝服务就会调用本地的一个页面，返回支付成功或失败
        alipayRequest.setReturnUrl(alipayProperties.getReturnPaymentUrl());

        // 异步回调，支付宝异步调后端的接口路径，更新本地的订单状态
        alipayRequest.setNotifyUrl(alipayProperties.getNotifyPaymentUrl());

        // 准备请求参数 ，声明一个map 集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("out_trade_no",paymentInfo.getOrderNo());
        map.put("product_code","QUICK_WAP_WAY");
        //map.put("total_amount",paymentInfo.getAmount()); //实际支付
        map.put("total_amount",new BigDecimal("0.01"));//为了测试，支付0.01元
        map.put("subject",paymentInfo.getContent());
        alipayRequest.setBizContent(JSON.toJSONString(map));

        //调用支付宝服务接口
        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest);
            if (response.isSuccess()){
                String form = response.getBody();
                return form;
            }else{
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
