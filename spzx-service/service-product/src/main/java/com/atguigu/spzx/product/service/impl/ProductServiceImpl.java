package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.dto.h5.ProductSkuDto;
import com.atguigu.spzx.model.dto.product.SkuSaleDto;
import com.atguigu.spzx.model.entity.product.Product;
import com.atguigu.spzx.model.entity.product.ProductDetails;
import com.atguigu.spzx.model.entity.product.ProductSku;
import com.atguigu.spzx.model.vo.h5.ProductItemVo;
import com.atguigu.spzx.product.mapper.ProductDetailsMapper;
import com.atguigu.spzx.product.mapper.ProductMapper;
import com.atguigu.spzx.product.mapper.ProductSkuMapper;
import com.atguigu.spzx.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ProductServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-24 16:21
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    // 2.查询畅销商品，（根据销量进行排序）
    @Override
    public List<ProductSku> selectProductSkuBySal() {
        List<ProductSku> productSkuList = productSkuMapper.selectProductSkuBySal();
        return productSkuList;
    }

    //根据搜索条件，分页查询
    @Override
    public PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList = productSkuMapper.findByPage(productSkuDto);
        PageInfo<ProductSku> pageInfo = new PageInfo<>(productSkuList);
        return pageInfo;
    }

    //查询商品详情信息，需要查询3个表product、sku和details
    @Override
    public ProductItemVo item(Long skuId) {
        //1.获取当前sku信息
        ProductSku productSku = productSkuMapper.getProductSkuById(skuId);
        //2.根据productId获取当前商品信息
        Product product = productMapper.getProductById(productSku.getProductId());

        //3.根据productId获取商品详情信息
        ProductDetails productDetails = productDetailsMapper.getProductDetailsById(productSku.getProductId());

        //4.封装Map集合Map<String,Object> skuSpecValueMap
        //获取同一个商品下面的sku信息列表
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(productSku.getProductId());
        //建立sku规格与skuId对应的关系
        Map<String,Object> skuSpecValueMap = new HashMap<>();
        productSkuList.forEach(item ->{
            skuSpecValueMap.put(item.getSkuSpec(),item.getId());
        });

        //5.用ProductItemVo封装数据，并返回
        ProductItemVo productItemVo = new ProductItemVo();
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        //轮播图列表
        productItemVo.setSliderUrlList(Arrays.asList(product.
                getSliderUrls().split(",")));
        //封装商品详情图片，列表
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails
                .getImageUrls().split(",")));
        //商品规格信息，JSONArray
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        //封装map集合
        productItemVo.setSkuSpecValueMap(skuSpecValueMap);

        return productItemVo;
    }

    //根据skuId获取sku信息
    @Override
    public ProductSku getBySkuId(Long skuId) {
        ProductSku productSku = productSkuMapper.getProductSkuById(skuId);
        return productSku;
    }

    //远程调用，更新商品sku销量
    @Override
    public Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList) {
        if(!CollectionUtils.isEmpty(skuSaleDtoList)) {
            for(SkuSaleDto skuSaleDto : skuSaleDtoList) {
                productSkuMapper.updateSale(skuSaleDto.getSkuId(), skuSaleDto.getNum());
            }
        }
        return true;
    }
}
