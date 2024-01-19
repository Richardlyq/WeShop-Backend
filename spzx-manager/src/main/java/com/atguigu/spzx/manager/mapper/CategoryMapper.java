package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName CategoryMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-19 9:23
 **/
@Mapper
public interface CategoryMapper {
    //1.根据parentId获取下级分类，返回一个list
    List<Category> selectCategoryByParentId(Long parentId);
    //2.判断该节点下是否有子节点
    int selectCountByParentId(Long id);
    //3.查询数据库中的所有商品，返回一个list
    List<Category> findAllCategory();

    void batchInsert(List<CategoryExcelVo> categoryList);
}
