package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Description
 * @Author Richard
 * @Date 2023-12-24 16:20
 **/

public interface CategoryService {
    // 1. 查询所有的一级分类
    List<Category> selectOneCategory();

    //获取分类树形结构
    List<Category> findCategoryTree();
}
