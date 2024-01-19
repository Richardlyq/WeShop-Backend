package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;

/**
 * @ClassName UserInfoService
 * @Description
 * @Author Richard
 * @Date 2023-12-26 12:56
 **/

public interface UserInfoService {
    //用户注册功能
    void register(UserRegisterDto userRegisterDto);
    //用户登录功能
    String login(UserLoginDto userLoginDto);
    //获取当前用户信息
    UserInfoVo getCurrentUserInfo(String token);
}
