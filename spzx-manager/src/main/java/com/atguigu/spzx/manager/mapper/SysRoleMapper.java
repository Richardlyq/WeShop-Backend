package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SysRoleMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-15 10:59
 **/
@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void saveSysRole(SysRole sysRole);

    void updateSysRole(SysRole sysRole);

    void deleteById(Long roleId);

    List<SysRole> findAllRoles();
}
