package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductSpecMapper;
import com.atguigu.spzx.manager.service.ProductSpecService;
import com.atguigu.spzx.model.entity.product.ProductSpec;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductSpecServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-21 21:48
 **/
@Service
public class ProductSpecServiceImpl implements ProductSpecService {

    @Autowired
    private ProductSpecMapper productSpecMapper;

    //商品规格列表功能
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<ProductSpec> productSpecList = productSpecMapper.findByPage();
        return new PageInfo<>(productSpecList);
    }

    //商品规格添加功能
    @Override
    public void save(ProductSpec productSpec) {
        productSpecMapper.save(productSpec) ;
    }

    //商品规格修改功能
    @Override
    public void updateById(ProductSpec productSpec) {
        productSpecMapper.undateById(productSpec);
    }

    //商品规格删除功能
    @Override
    public void deleteById(Long id) {
        productSpecMapper.deleteById(id);
    }

    //查询所有的商品规格数据（商品添加时加载商品规格数据）
    @Override
    public List<ProductSpec> findAll() {
        return productSpecMapper.findAll();
    }



}
