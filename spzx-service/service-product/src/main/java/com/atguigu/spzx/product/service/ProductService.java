package com.atguigu.spzx.product.service;

import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @ClassName ProductService
 * @Description
 * @Author Richard
 * @Date 2023-12-24 16:20
 **/

public interface ProductService {
    // 2.查询畅销商品，（根据销量进行排序）
    List<ProductSku> selectProductSkuBySal();
    //根据搜索条件，分页查询
    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);
    //查询商品详情信息，需要查询3个表product、sku和details
    ProductItemVo item(Long skuId);
    //根据skuId获取sku信息
    ProductSku getBySkuId(Long skuId);
    //远程调用，更新商品sku销量
    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}
