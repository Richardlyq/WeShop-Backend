package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.BrandService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName BrandController
 * @Description
 * @Author Richard
 * @Date 2023-12-25 12:56
 **/
@Tag(name = "品牌管理")
@RestController
@RequestMapping(value="/api/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    //查询所有品牌
    @GetMapping("findAll")
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
