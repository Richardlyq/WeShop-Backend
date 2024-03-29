package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.BrandMapper;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BrandServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-21 12:30
 **/
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    //品牌的列表功能
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<Brand> brandList = brandMapper.findByPage();
        return new PageInfo<>(brandList);
    }

    // 品牌的添加功能
    @Override
    public void save(Brand brand) {
        brandMapper.save(brand);
    }

    //品牌的修改功能
    @Override
    public void updateById(Brand brand) {
        brandMapper.updateById(brand);
    }

    //品牌的删除功能
    @Override
    public void deleteById(Long id) {
        brandMapper.deleteById(id);
    }

    //查询所有品牌
    @Override
    public List<Brand>  findAll() {
       List<Brand> brandList = brandMapper.findByPage();  //上面写过的，没有传分页数据，默认查全部
       return brandList;
    }
}
