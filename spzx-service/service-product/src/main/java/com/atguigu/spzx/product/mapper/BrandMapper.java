package com.atguigu.spzx.product.mapper;

import com.atguigu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName BrandMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-25 12:57
 **/
@Mapper
public interface BrandMapper {
    //查询所有品牌
    List<Brand> findAll();
}
