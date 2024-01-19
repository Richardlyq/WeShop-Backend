package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName CategoryController
 * @Description
 * @Author Richard
 * @Date 2023-12-19 9:22
 **/
@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    //根据parentId获取下级分类
    @GetMapping(value = "/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable Long parentId){
        List<Category> categoryList = categoryService.findCategoryByParentId(parentId);
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }

    //将商品信息导出到excel中
    @GetMapping(value = "/exportData")
    public void exportData(HttpServletResponse response) {
        categoryService.exportData(response);
    }

    //将商品信息导入
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        categoryService.importData(file);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
