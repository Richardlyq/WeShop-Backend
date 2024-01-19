package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.FileUploadService;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName FileUploadController
 * @Description
 * @Author Richard
 * @Date 2023-12-17 10:10
 **/

@RestController
@RequestMapping("/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/fileUpload")
    public Result fileUpload(MultipartFile file){
        // 1.获取上传的文件
        // 2.调用service中的方法上传，返回minio路径
        String fileUrl = fileUploadService.fileUpload(file);
        return Result.build(fileUrl, ResultCodeEnum.SUCCESS);
    }

}
