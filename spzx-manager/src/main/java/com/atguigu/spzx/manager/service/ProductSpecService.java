package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName ProductSpecService
 * @Description
 * @Author Richard
 * @Date 2023-12-21 21:48
 **/

public interface ProductSpecService {
    //商品规格列表功能
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);
    //商品规格添加功能
    void save(ProductSpec productSpec);
    //商品规格修改功能
    void updateById(ProductSpec productSpec);
    //商品规格删除功能
    void deleteById(Long id);
    //查询所有的商品规格数据（商品添加时加载商品规格数据）
    List<ProductSpec> findAll();
}
