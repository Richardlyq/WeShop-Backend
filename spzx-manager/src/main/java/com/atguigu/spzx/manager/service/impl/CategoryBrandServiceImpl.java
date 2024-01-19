package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.CategoryBrandMapper;
import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CategoryBrandServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-21 20:58
 **/
@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    //分页列表品牌及分类
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page,limit);
        List<CategoryBrand> categoryBrandList = categoryBrandMapper.findByPage(categoryBrandDto);
        return new PageInfo<>(categoryBrandList);
    }

    //品牌和分类添加功能
    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    //品牌和分类修改功能
    @Override
    public void updateById(CategoryBrand categoryBrand) {
        categoryBrandMapper.updateById(categoryBrand) ;
    }

    //品牌和分类删除功能
    @Override
    public void deleteById(Long id) {
        categoryBrandMapper.deleteById(id) ;
    }

    //根据分类id查询对应的品牌功能
    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        List<Brand> brandList = categoryBrandMapper.findBrandByCategoryId(categoryId);
        return brandList;
    }
}
