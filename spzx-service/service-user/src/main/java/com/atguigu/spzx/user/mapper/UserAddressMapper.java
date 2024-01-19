package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName UserAddressMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-29 10:01
 **/
@Mapper
public interface UserAddressMapper {
    //获取用户地址列表
    List<UserAddress> findUserAddressListByUserId(Long userInfoId);
    //查询用户地址信息
    UserAddress getById(Long id);
}
