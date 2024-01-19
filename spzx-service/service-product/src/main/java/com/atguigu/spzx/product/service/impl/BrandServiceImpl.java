package com.atguigu.spzx.product.service.impl;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.product.mapper.BrandMapper;
import com.atguigu.spzx.product.service.BrandService;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BrandServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-25 12:57
 **/
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    //查询所有品牌
    @Override
    public List<Brand> findAll() {
        List<Brand> brandList = brandMapper.findAll();
        return brandList;
    }
}
