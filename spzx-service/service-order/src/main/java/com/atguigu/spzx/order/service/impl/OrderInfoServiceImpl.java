package com.atguigu.spzx.order.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.feign.cart.CartFeignClient;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.feign.user.UserFeignClient;
import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.entity.order.OrderItem;
import com.atguigu.spzx.model.entity.order.OrderLog;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.atguigu.spzx.order.mapper.OrderInfoMapper;
import com.atguigu.spzx.order.mapper.OrderItemMapper;
import com.atguigu.spzx.order.mapper.OrderLogMapper;
import com.atguigu.spzx.order.service.OrderInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderInfoServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-29 10:04
 **/
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    //确认下单
    @Override
    public TradeVo getTrade() {
        //远程调用，获得购物车中已选中的商品信息
        List<CartInfo> cartInfoList = cartFeignClient.getAllChecked();
        List<OrderItem> orderItemList = new ArrayList<>();

        //遍历cartInfoList，获取数据并封装到orderItemList
        cartInfoList.forEach(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        });

        //计算总金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount
                    .add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }

        //封装数据到TradeVo中
        TradeVo tradeVo = new TradeVo();
        tradeVo.setOrderItemList(orderItemList);
        tradeVo.setTotalAmount(totalAmount);

        return tradeVo;
    }

    //提交订单
    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        //1.获得orderInfoDto中的订单项orderItem
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        //2.判断List<OrderItem>是否为空，为空抛出异常
        if (CollectionUtils.isEmpty(orderItemList)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        //3.检验商品库存是否充足
        //需要远程调用获得商品sku信息的库存量
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId());
            if (productSku == null){
                throw new GuiguException(ResultCodeEnum.DATA_ERROR);
            }
            //检验库存
            if (productSku.getStockNum().intValue() < orderItem.getSkuNum().intValue()){
                throw new GuiguException(ResultCodeEnum.STOCK_LESS);
            }
        }

        //4.封装数据到OrderInfo对象中，并添加数据到order_info表中
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        //订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        //用户信息
        orderInfo.setUserId(userInfo.getId());
        orderInfo.setNickName(userInfo.getNickName());
        //远程调用获取用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        //订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        //保存到数据库中
        orderInfoMapper.save(orderInfo);

        //5.添加数据到order_item表中，封装orderItem数据
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }

        //6.添加数据到order_log表中，记录操作日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);
        //提交订单后，远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked();
        return orderInfo.getId();
    }

    //立即购买，根据订单id查询订单信息
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        return orderInfoMapper.getOrderInfoByorderId(orderId);
    }

    //立即购买，不经过购物车直接进行购买
    @Override
    public TradeVo buy(Long skuId) {
        //远程调用获取productSku信息
        ProductSku productSku = productFeignClient.getBySkuId(skuId);
        //封装数据到orderItem中
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        //封装数据到orderItemList中
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);
        //计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();
        //封装数据到tradeVo
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;
    }

    //获取订单分页列表
    @Override
    public PageInfo<OrderInfo> findOrderPage(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page,limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        //得到所有的订单信息
        List<OrderInfo> orderInfoList = orderInfoMapper.findOrderPage(userId,orderStatus);
        //订单信息封装订单项列表
        for (OrderInfo orderInfo : orderInfoList) {
            List<OrderItem> orderItemList = orderItemMapper.getItemListByOrderId(orderInfo.getId());
            orderInfo.setOrderItemList(orderItemList);
        }
        return new PageInfo<>(orderInfoList);
    }

    //远程调用，根据订单编号获取订单信息
    @Override
    public OrderInfo getByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getByorderNo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.getItemListByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }

    //远程调用，更新订单状态
    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {
        // 更新订单状态
        OrderInfo orderInfo = orderInfoMapper.getByorderNo(orderNo);
        orderInfo.setOrderStatus(1);
        orderInfo.setPayType(orderStatus);
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.updateById(orderInfo);

        // 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.save(orderLog);
    }

}
