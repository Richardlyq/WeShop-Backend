package com.atguigu.spzx.user.service.impl;

import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.user.mapper.UserAddressMapper;
import com.atguigu.spzx.user.service.UserAddressService;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserAddressServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-29 10:01
 **/
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    //获取用户地址列表
    @Override
    public List<UserAddress> findUserAddressList() {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        Long userInfoId = userInfo.getId();
        List<UserAddress> userAddressList = userAddressMapper.findUserAddressListByUserId(userInfoId);
        return userAddressList;
    }

    //查询用户地址信息
    @Override
    public UserAddress getById(Long id) {
        UserAddress userAddress = userAddressMapper.getById(id);
        return userAddress;
    }
}
