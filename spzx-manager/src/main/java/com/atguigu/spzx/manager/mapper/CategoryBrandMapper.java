package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName CategoryBrandMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-21 20:59
 **/
@Mapper
public interface CategoryBrandMapper {
    //分页列表品牌及分类
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    //品牌和分类添加功能
    void save(CategoryBrand categoryBrand);

    //品牌和分类修改功能
    void updateById(CategoryBrand categoryBrand);

    //品牌和分类删除功能
    void deleteById(Long id);

    //根据分类id查询对应的品牌功能
    List<Brand> findBrandByCategoryId(Long categoryId);
}
