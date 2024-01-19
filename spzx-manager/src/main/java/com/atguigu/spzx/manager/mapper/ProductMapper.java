package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ProductMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-21 22:32
 **/
@Mapper
public interface ProductMapper {
    //商品列表功能（条件分页查询）
    List<Product> findByPage(ProductDto productDto);
    //商品添加功能, 1.获取商品的基本信息，保存到product表中
    void save(Product product);
    //1.商品修改功能，根据id查询商品的基本信息
    Product findProductById(Long id);
    //1.商品修改功能，修改商品基本信息
    void updateProduct(Product product);
    //1.商品删除功能，删除商品基本信息product
    void deleteProductById(Long id);
}
