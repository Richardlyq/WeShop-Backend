package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName ProductMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-25 14:50
 **/
@Mapper
public interface ProductMapper {
    //2.根据productId获取当前商品信息
    Product getProductById(Long productId);
}
