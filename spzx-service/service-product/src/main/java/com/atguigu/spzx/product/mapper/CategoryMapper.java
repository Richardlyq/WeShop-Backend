package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName CategoryMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-24 16:22
 **/
@Mapper
public interface CategoryMapper {
    // 1. 查询所有的一级分类
    List<Category> selectOneCategory();
    //1.查询数据库中的所有分类
    List<Category> finaAllCategory();
}
