package com.atguigu.spzx.pay.service;

/**
 * @ClassName AlipayService
 * @Description
 * @Author Richard
 * @Date 2023-12-30 21:35
 **/

public interface AlipayService {
    //支付宝下单
    String submitAlipay(String orderNo);
}
