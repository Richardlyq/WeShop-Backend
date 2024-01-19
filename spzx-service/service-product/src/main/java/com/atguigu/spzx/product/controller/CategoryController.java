package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.product.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CategoryController
 * @Description
 * @Author Richard
 * @Date 2023-12-24 19:55
 **/
@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value="/api/product/category")
//@CrossOrigin //跨域
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //获取分类树形结构
    @Operation(summary = "获取分类树形数据")
    @GetMapping("/findCategoryTree")
    public Result findCategoryTree(){
        List<Category> categoryList = categoryService.findCategoryTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
