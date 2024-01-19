package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.base.ProductUnit;

import java.util.List;

/**
 * @ClassName ProductUnitService
 * @Description
 * @Author Richard
 * @Date 2023-12-21 23:13
 **/

public interface ProductUnitService {
    //查询所有计量单位（商品添加时加载品牌单元数据）
    List<ProductUnit> findAll();
}
