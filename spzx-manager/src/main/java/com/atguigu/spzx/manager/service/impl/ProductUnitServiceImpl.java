package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductUnitMapper;
import com.atguigu.spzx.manager.service.ProductUnitService;
import com.atguigu.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductUnitServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-21 23:13
 **/
@Service
public class ProductUnitServiceImpl implements ProductUnitService {

    @Autowired
    private ProductUnitMapper productUnitMapper;

    //查询所有计量单位（商品添加时加载品牌单元数据）
    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll() ;
    }
}
