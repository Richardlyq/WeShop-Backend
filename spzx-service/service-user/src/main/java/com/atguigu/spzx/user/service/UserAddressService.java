package com.atguigu.spzx.user.service;

import com.atguigu.spzx.model.entity.user.UserAddress;

import java.util.List;

/**
 * @ClassName UserAddressService
 * @Description
 * @Author Richard
 * @Date 2023-12-29 10:00
 **/

public interface UserAddressService {
    //获取用户地址列表
    List<UserAddress> findUserAddressList();
    //查询用户地址信息
    UserAddress getById(Long id);
}
