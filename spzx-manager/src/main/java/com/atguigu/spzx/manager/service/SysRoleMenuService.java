package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.dto.system.AssignMenuDto;

import java.util.Map;

/**
 * @ClassName SysRoleMenuService
 * @Description
 * @Author Richard
 * @Date 2023-12-18 9:10
 **/

public interface SysRoleMenuService {
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    void doAssign(AssignMenuDto assignMenuDto);
}
