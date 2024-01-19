package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SysUserMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-07 20:45
 **/
@Mapper
public interface SysUserMapper {
    SysUser selectByUserName(String userName);

    List<SysUser> findByPage(SysUserDto sysUserDto);

    SysUser findByUserName(String userName);

    void saveSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void updateSysUser(SysUser sysUser);
}
