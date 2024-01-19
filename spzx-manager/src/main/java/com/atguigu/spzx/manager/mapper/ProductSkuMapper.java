package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ProductSkuMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-21 23:29
 **/
@Mapper
public interface ProductSkuMapper {
    //2.商品添加功能，获取商品的sku列表集合，保存sku信息，保存到product_sku表中
    void save(ProductSku productSku);
    //2.商品修改功能，根据id查询商品sku信息
    List<ProductSku> findProductSkuByProductId(Long id);
    //2.商品修改功能，修改商品sku信息
    void updateProductSku(ProductSku productSku);
    //2.商品删除功能，删除商品sku信息
    void deleteProductSkuByProductId(Long id);
}
