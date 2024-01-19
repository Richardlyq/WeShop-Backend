package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName ProductService
 * @Description
 * @Author Richard
 * @Date 2023-12-21 22:32
 **/

public interface ProductService {
    //商品列表功能（条件分页查询）
    PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto);
    //商品添加功能
    void save(Product product);
    //商品修改功能-根据id查询商品使得数据回显
    Product getById(Long id);
    //商品修改功能，保存修改数据
    void updateById(Product product);
    //商品删除功能
    void deleteById(Long id);
    //商品审核功能
    void updateAuditStatus(Long id, Integer auditStatus);
    //商品上下架功能
    void updateStatus(Long id, Integer status);
}
