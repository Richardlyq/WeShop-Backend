package com.atguigu.spzx.manager.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SysRoleUserMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-17 17:07
 **/

@Mapper
public interface SysRoleUserMapper {
    void deleteById(Long userId);

    void doAssign(Long userId, Long roleId);

    List<Long> findSysRoleByUserID(Long userId);
}
