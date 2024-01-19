package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.ProductDetailsMapper;
import com.atguigu.spzx.manager.mapper.ProductMapper;
import com.atguigu.spzx.manager.mapper.ProductSkuMapper;
import com.atguigu.spzx.manager.service.ProductService;
import com.atguigu.spzx.model.dto.product.ProductDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProductServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-21 22:32
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    //商品列表功能（条件分页查询）
    @Override
    public PageInfo<Product> findByPage(Integer page, Integer limit, ProductDto productDto) {
        PageHelper.startPage(page, limit);
        List<Product> productList = productMapper.findByPage(productDto);
        return new PageInfo<>(productList);
    }

    //商品添加功能
    @Override
    public void save(Product product) {
        //1.获取商品的基本信息，保存到product表中
        product.setStatus(0);              // 设置上架状态为0
        product.setAuditStatus(0);         // 设置审核状态为0
        productMapper.save(product);

        //2.获取商品的sku列表集合，保存sku信息，保存到product_sku表中
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(int i=0,size=productSkuList.size(); i<size; i++) {

            // 获取ProductSku对象
            ProductSku productSku = productSkuList.get(i);
            productSku.setSkuCode(product.getId() + "_" + i);       // 构建skuCode

            productSku.setProductId(product.getId());               // 设置商品id
            productSku.setSkuName(product.getName() + productSku.getSkuSpec());
            productSku.setSaleNum(0);                               // 设置销量
            productSku.setStatus(0);
            productSkuMapper.save(productSku);                    // 保存数据

        }

        //3.保存商品详情数据到product_details表中
        ProductDetails productDetails = new ProductDetails();
        productDetails.setProductId(product.getId());
        productDetails.setImageUrls(product.getDetailsImageUrls());
        productDetailsMapper.save(productDetails);
    }

    //商品修改功能-根据id查询商品使得数据回显
    @Override
    public Product getById(Long id) {
        //1.根据id查询商品的基本信息
        Product product = productMapper.findProductById(id);

        //2.根据id查询商品sku信息
        List<ProductSku> productSkuList = productSkuMapper.findProductSkuByProductId(id);
        product.setProductSkuList(productSkuList);

        //3.根据id查询商品详情数据
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(product.getId());
        String imageUrls = productDetails.getImageUrls();
        product.setDetailsImageUrls(imageUrls);
        return product;
    }

    //商品修改功能，保存修改数据
    @Override
    public void updateById(Product product) {
        //1.修改商品基本信息
        productMapper.updateProduct(product);

        //2.修改商品sku信息
        List<ProductSku> productSkuList = product.getProductSkuList();
        for(ProductSku productSku:productSkuList){
            productSkuMapper.updateProductSku(productSku);
        }

        //3.修改商品的详情信息
        ProductDetails productDetails = productDetailsMapper.findProductDetailsById(product.getId());
        String detailsImageUrls = product.getDetailsImageUrls();
        productDetails.setImageUrls(detailsImageUrls);
        productDetailsMapper.updateProductDetails(productDetails);
    }

    //商品删除功能
    @Override
    public void deleteById(Long id) {
        //1.删除商品基本信息product
        productMapper.deleteProductById(id);
        //2.删除商品sku信息
        productSkuMapper.deleteProductSkuByProductId(id);
        //3.删除商品的详情信息
        productDetailsMapper.deleteProductDetailsById(id);
    }

    //商品审核功能
    @Override
    public void updateAuditStatus(Long id, Integer auditStatus) {
        Product product = new Product();
        product.setId(id);
        if(auditStatus == 1) {
            product.setAuditStatus(1);
            product.setAuditMessage("审批通过");
        } else {
            product.setAuditStatus(-1);
            product.setAuditMessage("审批不通过");
        }
        productMapper.updateProduct(product);
    }

    //商品上下架功能
    @Override
    public void updateStatus(Long id, Integer status) {
        Product product = new Product();
        product.setId(id);
        if(status == 1) {
            product.setStatus(1);
        } else {
            product.setStatus(-1);
        }
        productMapper.updateProduct(product);
    }
}
