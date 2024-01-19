package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Brand;

import java.util.List;

/**
 * @ClassName BrandService
 * @Description
 * @Author Richard
 * @Date 2023-12-25 12:56
 **/

public interface BrandService {
    //查询所有品牌
    List<Brand> findAll();
}
