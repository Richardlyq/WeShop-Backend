package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName ProductDetailsMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-21 23:30
 **/
@Mapper
public interface ProductDetailsMapper {
    //3.商品添加功能，保存商品详情数据到product_details表中
    void save(ProductDetails productDetails);
    //3.商品修改功能，根据id查询商品详情数据
    ProductDetails findProductDetailsById(Long id);
    //3.商品修改功能，修改商品的详情信息
    void updateProductDetails(ProductDetails productDetails);
    //3.商品删除功能，删除商品的详情信息
    void deleteProductDetailsById(Long id);
}
