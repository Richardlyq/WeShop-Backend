package com.atguigu.spzx.product.utils;

import com.atguigu.spzx.model.entity.product.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CategoryTreeHelper
 * @Description
 * @Author Richard
 * @Date 2023-12-24 21:24
 **/

public class CategoryTreeHelper {

    //传入所有的分类，返回分类的树形结构
    public static List<Category> buildTree(List<Category> allCategoryList){
        List<Category> treeCategoryList = new ArrayList<>();
        for (Category category:allCategoryList){
            if (category.getParentId().longValue() == 0){ //1.递归入口，查找到第一层的分类
                treeCategoryList.add(findChildren(category,allCategoryList));
            }
        }
        return treeCategoryList;
    }

    private static Category findChildren(Category category, List<Category> allCategoryList) {
        //初始化
        category.setChildren(new ArrayList<>());
        for (Category category1 : allCategoryList){
            //2.条件，下一层的分类的parentId == 该分类的id
            if (category1.getParentId().longValue() == category.getId().longValue()) {
                category.getChildren().add(findChildren(category1, allCategoryList));
            }
        }
        return category;
    }
}
