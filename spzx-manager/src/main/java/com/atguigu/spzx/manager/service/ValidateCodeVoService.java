package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.vo.system.ValidateCodeVo;

/**
 * @ClassName ValidateCodeVoService
 * @Description
 * @Author Richard
 * @Date 2023-12-14 9:35
 **/

public interface ValidateCodeVoService {
    ValidateCodeVo generateValidateCode();
}
