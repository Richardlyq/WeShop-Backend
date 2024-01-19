package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryBrandService;
import com.atguigu.spzx.model.dto.product.CategoryBrandDto;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.entity.product.CategoryBrand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName CategoryBrandController
 * @Description
 * @Author Richard
 * @Date 2023-12-21 20:57
 **/
@RestController
@RequestMapping(value = "/admin/product/categoryBrand")
public class CategoryBrandController {


    @Autowired
    private CategoryBrandService categoryBrandService;

    //根据分类id查询对应的品牌功能(添加商品时加载品牌数据)
    @GetMapping("/findBrandByCategoryId/{categoryId}")
    public Result findBrandByCategoryId(@PathVariable Long categoryId){
        List<Brand> brandList = categoryBrandService.findBrandByCategoryId(categoryId);
        return Result.build(brandList,ResultCodeEnum.SUCCESS);
    }

    //分页列表品牌及分类
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer limit, CategoryBrandDto categoryBrandDto){
        PageInfo<CategoryBrand> pageInfo = categoryBrandService.findByPage(page,limit,categoryBrandDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //品牌和分类添加功能
    @PostMapping("/save")
    public Result save(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.save(categoryBrand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //品牌和分类修改功能
    @PutMapping("updateById")
    public Result updateById(@RequestBody CategoryBrand categoryBrand) {
        categoryBrandService.updateById(categoryBrand);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }

    //品牌和分类删除功能
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        categoryBrandService.deleteById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
