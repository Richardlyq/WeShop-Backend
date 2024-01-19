package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleMenuServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-18 9:10
 **/
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysMenuService sysMenuService;

    //1.查询所有菜单 和 查询角色分配过菜单id列表
    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {

        //查询分配过的菜单
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuService.findNodes();

        //封装数据并返回
        Map<String, Object> sysRoleMenuMap = new HashMap<>();
        sysRoleMenuMap.put("sysMenuList", sysMenuList);
        sysRoleMenuMap.put("roleMenuIds", roleMenuIds);

        return sysRoleMenuMap;
    }

    //2.保存角色分配菜单数据
    @Override
    public void doAssign(AssignMenuDto assignMenuDto) {
        //根据角色id先删除已分配的菜单
        sysRoleMenuMapper.deleteByRoleId(assignMenuDto.getRoleId());

        //获取选中的菜单id
        List<Map<String, Number>> menuIdList = assignMenuDto.getMenuIdList();
        if(menuIdList != null && menuIdList.size() > 0){ //判断是否给角色分配了菜单
            sysRoleMenuMapper.doAssign(assignMenuDto);
        }
    }
}
