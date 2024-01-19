package com.atguigu.spzx.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.product.mapper.CategoryMapper;
import com.atguigu.spzx.product.service.CategoryService;
import com.atguigu.spzx.product.utils.CategoryTreeHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @ClassName CategoryServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-24 16:21
 **/
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String , String> redisTemplate ;

    // 1. 查询所有的一级分类
    //将数据放入Redis方式1：使用redisTemplate进行封装
    @Override
    public List<Category> selectOneCategory() {
        String categoryListJSON = redisTemplate.opsForValue().get("category::one");
        if(StringUtils.hasText(categoryListJSON)) {
            List<Category> existCategoryList = JSON.parseArray(categoryListJSON, Category.class);
            log.info("从Redis缓存中查询到了所有的一级分类数据");
            return existCategoryList ;
        }
        List<Category> categoryList = categoryMapper.selectOneCategory();
        log.info("从数据库中查询到了所有的一级分类数据");
        redisTemplate.opsForValue().set("category:one" , JSON.toJSONString(categoryList) , 7 , TimeUnit.DAYS);
        return categoryList;
    }

    //获取分类树形结构
    //将数据放入Redis方式2：使用Cacheable注解，步骤如下：
    //       （1）在pom文件中引入依赖spring-boot-starter-cache
    //       （2）编写RedisConfig配置类，配置Redis的key的序列化器
    //       （3）启动类上加上@EnableCaching开启注解支持
    //       （4）方法上加上Cacheable注解
    @Override
    @Cacheable(value = "category", key = "'all'") //放入redis缓存中，key的名字：category::all
    public List<Category> findCategoryTree() {
        //1.查询数据库中的所有分类
        List<Category> allCategoryList = categoryMapper.finaAllCategory();
        //2.自定义递归，将所有分类转换成树形结构
        List<Category> categoryTreeList = CategoryTreeHelper.buildTree(allCategoryList);

        //2. 用Stream流的方式进行遍历
//        //2.遍历所有分类，找出parentId = 0的一级分类，返回list集合
//        List<Category> oneCategoryList = allCategoryList.stream()
//                .filter(item -> item.getParentId().longValue() == 0)
//                .collect(Collectors.toList());
//        //3.遍历一级分类list集合，条件判断id = parentId
//        if (!CollectionUtils.isEmpty(oneCategoryList)){
//            oneCategoryList.forEach(oneCategory -> {
//                List<Category> twoCategoryList = allCategoryList.stream()
//                        .filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue())
//                        .collect(Collectors.toList());
//                //第一层分类下设置第二层分类的节点
//                oneCategory.setChildren(twoCategoryList);
//                if (!CollectionUtils.isEmpty(twoCategoryList)){
//                    twoCategoryList.forEach(twoCategory -> {
//                        List<Category> threeCategoryList = allCategoryList.stream()
//                                .filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue())
//                                .collect(Collectors.toList());
//                        //第二层分类下设置第三层分类的节点
//                        twoCategory.setChildren(threeCategoryList);
//                    });
//                }
//            });
//        }
        return categoryTreeList;
    }
}
