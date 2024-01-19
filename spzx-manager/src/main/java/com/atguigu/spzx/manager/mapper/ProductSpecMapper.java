package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ProductSpecMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-21 21:48
 **/
@Mapper
public interface ProductSpecMapper {
    //商品规格列表功能
    List<ProductSpec> findByPage();
    //商品规格添加功能
    void save(ProductSpec productSpec);
    //商品规格修改功能
    void undateById(ProductSpec productSpec);
    //商品规格删除功能
    void deleteById(Long id);
    //查询所有的商品规格数据（商品添加时加载商品规格数据）
    List<ProductSpec> findAll();
}
