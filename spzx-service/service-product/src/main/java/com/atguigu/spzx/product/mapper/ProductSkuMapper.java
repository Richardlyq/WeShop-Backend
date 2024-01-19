package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ProductSkuMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-24 16:23
 **/
@Mapper
public interface ProductSkuMapper {
    // 2.查询畅销商品，（根据销量进行排序）
    List<ProductSku> selectProductSkuBySal();
    //根据搜索条件，分页查询
    List<ProductSku> findByPage(ProductSkuDto productSkuDto);
    //1.获取当前sku信息
    ProductSku getProductSkuById(Long skuId);
    //获取同一个商品下面的sku信息列表
    List<ProductSku> findByProductId(Long productId);
    //远程调用，更新商品sku销量
    void updateSale(Long skuId, Integer num);
}
