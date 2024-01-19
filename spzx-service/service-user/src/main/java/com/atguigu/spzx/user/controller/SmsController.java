package com.atguigu.spzx.user.controller;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.user.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SmsController
 * @Description
 * @Author Richard
 * @Date 2023-12-25 23:00
 **/
@RestController
@RequestMapping("api/user/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    //发送短信接口开发
    @GetMapping(value = "/sendCode/{phone}")
    public Result sendValidateCode(@PathVariable String phone){
        smsService.sendValidateCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
