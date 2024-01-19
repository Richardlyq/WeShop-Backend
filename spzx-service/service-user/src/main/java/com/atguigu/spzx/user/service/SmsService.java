package com.atguigu.spzx.user.service;

/**
 * @ClassName SmsService
 * @Description
 * @Author Richard
 * @Date 2023-12-25 23:01
 **/

public interface SmsService {
    //发送短信接口开发
    void sendValidateCode(String phone);
}
