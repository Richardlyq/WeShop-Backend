package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageInfo;

/**
 * @ClassName SysUserService
 * @Description
 * @Author Richard
 * @Date 2023-12-07 20:43
 **/

public interface SysUserService {
    LoginVo login(LoginDto loginDto);

    SysUser getUserInfo(String token);

    void logout(String token);

    PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void deleteById(Long userId);

    void updateSysUser(SysUser sysUser);

    void doAssign(AssignRoleDto assignRoleDto);
}
