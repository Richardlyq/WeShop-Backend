package com.atguigu.spzx.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.listener.ExcelListener;
import com.atguigu.spzx.manager.mapper.CategoryMapper;
import com.atguigu.spzx.manager.service.CategoryService;
import com.atguigu.spzx.model.entity.product.Category;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName CategoryServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-19 9:23
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //根据parentId获取下级分类
    @Override
    public List<Category> findCategoryByParentId(Long parentId) {
        //1.根据parentId获取下级分类，返回一个list
        List<Category> categoryList = categoryMapper.selectCategoryByParentId(parentId);
        //2.遍历返回的list集合，判断该节点下是否有子节点，如果有，则设置hasChildren属性为true（前端需要）
        if(!CollectionUtils.isEmpty(categoryList)){
            categoryList.forEach(category ->{
                int count = categoryMapper.selectCountByParentId(category.getId());
                if(count > 0) {
                    category.setHasChildren(true);
                }else {
                    category.setHasChildren(false);
                }
            });
        }
        return categoryList;
    }

    //将商品信息导出到excel中
    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //1.设置响应头及其他信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            //设置响应头信息Content-disposition，让文件以下载方式打开
            //response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //2.利用mapper查询数据库中的所有商品，返回一个list
            List<Category> categoryList = categoryMapper.findAllCategory();
            //遍历集合，将Category转换成CategoryExcelVo（将前者的属性值取出来赋值给后者）
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>();
            for (Category category:categoryList){
                //将前者的属性值取出来赋值给后者
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category,categoryExcelVo);
                categoryExcelVoList.add(categoryExcelVo);
            }
            //3.用EasyExcel写入数据到excel表格中
            EasyExcel.write(response.getOutputStream(),CategoryExcelVo.class)
                    .sheet("分类数据")
                    .doWrite(categoryExcelVoList);

        }catch (Exception e){
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

    }

    //用EasyExcel将商品信息导入
    @Override
    public void importData(MultipartFile file) {
        //Todo ExcelListener
        ExcelListener<CategoryExcelVo> excelVoExcelListener= new ExcelListener<>(categoryMapper);
        try {
            EasyExcel.read(file.getInputStream(), CategoryExcelVo.class, excelVoExcelListener)
                    .sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
    }
}
