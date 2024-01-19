package com.atguigu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import com.atguigu.spzx.manager.service.ValidateCodeVoService;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ValidateCodeVoServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-14 9:36
 **/

@Service
public class ValidateCodeVoServiceImpl implements ValidateCodeVoService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //生成验证码
    @Override
    public ValidateCodeVo generateValidateCode() {
        //使用hutool生成验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(150, 48, 4, 20);
        String codeValue = circleCaptcha.getCode();
        String codeKey = UUID.randomUUID().toString().replace("-", "");
        String imageBase64 = circleCaptcha.getImageBase64();

        //存储到redis中
        redisTemplate.opsForValue().set("user:login:validatecode:"+codeKey,codeValue,5, TimeUnit.MINUTES);

        // 构建响应结果
        ValidateCodeVo validateCodeVo = new ValidateCodeVo();
        validateCodeVo.setCodeKey(codeKey);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);

        return validateCodeVo;
    }
}
