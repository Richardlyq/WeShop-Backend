package com.atguigu.spzx.product.controller;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName ProductController
 * @Description
 * @Author Richard
 * @Date 2023-12-25 13:11
 **/
@Tag(name = "商品列表管理")
@RestController
@RequestMapping(value="/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //根据搜索条件，分页查询
    @Operation(summary = "分页查询")
    @GetMapping(value = "/{page}/{limit}")
    public Result findByPage(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             ProductSkuDto productSkuDto){
        PageInfo<ProductSku> pageInfo = productService.findByPage(page, limit, productSkuDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //查询商品详情信息，需要查询3个表product、sku和details
    @Operation(summary = "商品详情")
    @GetMapping("item/{skuId}")
    public Result item(@PathVariable Long skuId){
        ProductItemVo productItemVo = productService.item(skuId);
        return Result.build(productItemVo,ResultCodeEnum.SUCCESS);
    }

    //根据skuId获取sku信息
    @Operation(summary = "获取商品sku信息")
    @GetMapping("getBySkuId/{skuId}")
    public ProductSku getBySkuId(@Parameter(name = "skuId", description = "商品skuId", required = true) @PathVariable Long skuId) {
        ProductSku productSku = productService.getBySkuId(skuId);
        return productSku;
    }

    //远程调用，更新商品sku销量
    @Operation(summary = "更新商品sku销量")
    @PostMapping("updateSkuSaleNum")
    public Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList) {
        return productService.updateSkuSaleNum(skuSaleDtoList);
    }
}
