package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @ClassName CategoryService
 * @Description
 * @Author Richard
 * @Date 2023-12-19 9:23
 **/

public interface CategoryService {
    List<Category> findCategoryByParentId(Long parentId);

    void exportData(HttpServletResponse response);

    void importData(MultipartFile file);
}
