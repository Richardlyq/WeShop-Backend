package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.enums.OperatorType;
import com.atguigu.spzx.manager.service.BrandService;
import com.atguigu.spzx.model.entity.product.Brand;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

/**
 * @ClassName BrandController
 * @Description
 * @Author Richard
 * @Date 2023-12-21 12:29
 **/
@RestController
@RequestMapping(value="/admin/product/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;
    @Log(title = "品牌列表",businessType = 0,operatorType = OperatorType.MANAGE)
    //品牌的列表功能
    @GetMapping("/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page, @PathVariable Integer limit){
        PageInfo<Brand> pageInfo = brandService.findByPage(page, limit);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    // 品牌的添加功能
    @PostMapping("save")
    public Result save(@RequestBody Brand brand){
        brandService.save(brand);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    //品牌的修改功能
    @PutMapping("updateById")
    public Result updateById(@RequestBody Brand brand){
        brandService.updateById(brand);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //品牌的删除功能
    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Long id){
        brandService.deleteById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //查询所有品牌
    @GetMapping("/findAll")
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
