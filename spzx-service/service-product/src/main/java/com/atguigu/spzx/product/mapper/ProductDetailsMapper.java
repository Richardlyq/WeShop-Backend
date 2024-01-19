package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName ProductDetailsMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-25 14:50
 **/
@Mapper
public interface ProductDetailsMapper {
    //3.根据productId获取商品详情信息
    ProductDetails getProductDetailsById(Long productId);
}
