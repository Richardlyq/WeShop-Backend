package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SysRoleMenuMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-18 9:11
 **/
@Mapper
public interface SysRoleMenuMapper {
    List<Long> findSysRoleMenuByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

    void doAssign(AssignMenuDto assignMenuDto);

//    将父菜单的id改为半开状态
    void updateSysRoleMenuIsHalf(Long id);
}
