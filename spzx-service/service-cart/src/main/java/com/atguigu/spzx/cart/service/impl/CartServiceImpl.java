package com.atguigu.spzx.cart.service.impl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.cart.service.CartService;
import com.atguigu.spzx.feign.product.ProductFeignClient;
import com.atguigu.spzx.model.entity.h5.CartInfo;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CartServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-27 22:25
 **/
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ProductFeignClient productFeignClient;

    private String getCartKey(Long userId) {
        //定义key user:cart:userId
        return "user:cart:" + userId;
    }

    //添加商品至购物车
    @Override
    public void addToCart(Long skuId, Integer skuNum) {
        //1.必须先登录，从ThreadLocal中获取用户id作为redis中hash类型的key
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //构建redis中hash类型的key名称
        String cartKey = this.getCartKey((userId));

        //2.从redis中获取购物车数据，根据用户id+skuId获取（redis中存的是hash类型，key, field, value）
        //redis中的hash类型： key: userId, field: userId, value: sku信息CartInfo
        Object cartInfoObj = redisTemplate.opsForHash().get(cartKey, skuId.toString());

        //3.如果购物车存在要添加的商品，把商品数量相加
        CartInfo cartInfo = null;
        if(cartInfoObj != null){//说明购物车中有要添加的商品
            //cartInfoObj --> CartInfo
            cartInfo = JSON.parseObject(cartInfoObj.toString(), CartInfo.class);
            cartInfo.setSkuNum(cartInfo.getSkuNum()+skuNum); // 数量相加
            cartInfo.setIsChecked(1);//设置属性，购物车商品选中状态
            cartInfo.setUpdateTime(new Date());
        }else {//4.如果购物车中没有要添加的商品，直接将商品加入购物车（添加到redis中）
            cartInfo = new CartInfo();
            //根据skuId获取商品sku信息，没有MySQL，这里用 远程调用实现：通过nacos + openFeign实现
            ProductSku productSku = productFeignClient.getBySkuId(skuId) ;
            //设置相关数据到cartInfo对象中
            cartInfo.setCartPrice(productSku.getSalePrice());
            cartInfo.setSkuNum(skuNum);
            cartInfo.setSkuId(skuId);
            cartInfo.setUserId(userId);
            cartInfo.setImgUrl(productSku.getThumbImg());
            cartInfo.setSkuName(productSku.getSkuName());
            cartInfo.setIsChecked(1);
            cartInfo.setCreateTime(new Date());
            cartInfo.setUpdateTime(new Date());
        }

        // 将商品数据存储到购物车中
        redisTemplate.opsForHash().put(cartKey , skuId.toString() , JSON.toJSONString(cartInfo));
    }

    //购物车列表查询功能
    @Override
    public List<CartInfo> getCartList() {
        //1.必须先登录，从ThreadLocal中获取用户id作为redis中hash类型的key
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //构建redis中hash类型的key名称
        String cartKey = this.getCartKey((userId));

        //2.根据key从redis里面hash类型中获取所有value值，cartInfo
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);

        //3. List<Object> --> List<CartInfo>
        if(!CollectionUtils.isEmpty(objectList)){
            List<CartInfo> cartInfoList = objectList.stream()
                    .map(cartInfo -> JSON.parseObject(cartInfo.toString(), CartInfo.class))
                    .sorted((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()))
                    .collect(Collectors.toList());
            return cartInfoList;
        }
        //如果redis中没有数据，则返回空的集合 []
        return new ArrayList<>();
    }

    //删除购物车中的某个商品
    @Override
    public void deleteCart(Long skuId) {
        //1.必须先登录，从ThreadLocal中获取用户id作为redis中hash类型的key
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //构建redis中hash类型的key名称
        String cartKey = this.getCartKey((userId));

        //从redis中删除购物车中的某个商品
        redisTemplate.opsForHash().delete(cartKey,skuId.toString());
    }

    //更新购物车商品选中状态
    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        //1.必须先登录，从ThreadLocal中获取用户id作为redis中hash类型的key
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //构建redis中hash类型的key名称
        String cartKey = this.getCartKey((userId));

        //根据cartKey和skuId判断redis中是否有数据
        Boolean hasKey = redisTemplate.opsForHash().hasKey(cartKey, skuId.toString());
        if(hasKey){
            Object object = redisTemplate.opsForHash().get(cartKey, skuId.toString());
            CartInfo cartInfo = JSON.parseObject(object.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            //将更新的数据放入redis中
            redisTemplate.opsForHash().put(cartKey,skuId.toString(),JSON.toJSONString(cartInfo));
        }
    }

    //更新购物车商品全部选中状态
    @Override
    public void allCheckCart(Integer isChecked) {
        //1.必须先登录，从ThreadLocal中获取用户id作为redis中hash类型的key
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //构建redis中hash类型的key名称
        String cartKey = this.getCartKey((userId));

        //2.获取购物车中的全部商品信息
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);
        if (!CollectionUtils.isEmpty(objectList)){
            //List<Object> --> List<CartInfo>
            List<CartInfo> cartInfoList = objectList.stream()
                    .map(object -> JSON.parseObject(String.valueOf(object), CartInfo.class))
                    .collect(Collectors.toList());

            //遍历cartInfoList，将里面的每个值都改成isChecked状态，并存入redis中
            cartInfoList.forEach(cartInfo -> {
                cartInfo.setIsChecked(isChecked);
                redisTemplate.opsForHash()
                        .put(cartKey,cartInfo.getSkuId().toString(),JSON.toJSONString(cartInfo));
            });
        }

    }

    //清空购物车
    @Override
    public void clearCart() {
        //1.必须先登录，从ThreadLocal中获取用户id作为redis中hash类型的key
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //构建redis中hash类型的key名称
        String cartKey = this.getCartKey((userId));
        redisTemplate.delete(cartKey);
    }

    //获取购物车中选中的商品信息
    @Override
    public List<CartInfo> getAllChecked() {
        //1.必须先登录，从ThreadLocal中获取用户id作为redis中hash类型的key
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userId = userInfo.getId();
        //构建redis中hash类型的key名称
        String cartKey = this.getCartKey((userId));
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);

        if (!CollectionUtils.isEmpty(objectList)){
            //遍历objectList,筛选出选中的信息，并转换成List<CartInfo>
            List<CartInfo> cartInfoList = objectList.stream()
                    .map(object -> JSON.parseObject(object.toString(), CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .collect(Collectors.toList());
            return cartInfoList;
        }

        return new ArrayList<>();
    }

    //在service-cart微服务中开发一个清空购物车的接口供service-order微服务进行调用
    @Override
    public void deleteChecked() {
        Long userId = AuthContextUtil.getUserInfo().getId();
        String cartKey = getCartKey(userId);
        List<Object> objectList = redisTemplate.opsForHash().values(cartKey);       // 删除选中的购物项数据
        if(!CollectionUtils.isEmpty(objectList)) {
            objectList.stream().map(object ->JSON.parseObject(object.toString(),CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked() == 1)
                    .forEach(cartInfo -> redisTemplate.opsForHash().delete(cartKey
                            ,String.valueOf(cartInfo.getSkuId())));
        }
    }
}
