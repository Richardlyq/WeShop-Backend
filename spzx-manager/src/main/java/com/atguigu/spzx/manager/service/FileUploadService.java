package com.atguigu.spzx.manager.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileUploadService
 * @Description
 * @Author Richard
 * @Date 2023-12-17 10:10
 **/

public interface FileUploadService {
    String fileUpload(MultipartFile file);
}
