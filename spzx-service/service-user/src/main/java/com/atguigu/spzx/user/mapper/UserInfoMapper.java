package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserInfoMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-26 12:57
 **/
@Mapper
public interface UserInfoMapper {
    //根据用户名查询用户信息，校验用户名（手机号）是否存在
    UserInfo getUserByUserName(String username);
    //保存用户信息
    void save(UserInfo userInfo);
}
