package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName ProductUnitMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-21 23:13
 **/
@Mapper
public interface ProductUnitMapper {
    //查询所有计量单位（商品添加时加载品牌单元数据）
    List<ProductUnit> findAll();
}
