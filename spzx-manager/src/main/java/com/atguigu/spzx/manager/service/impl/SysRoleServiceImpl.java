package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysRoleServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-15 10:58
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    //1.角色列表的方法
    @Override
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer current, Integer limit) {
        //设置分页参数
        PageHelper.startPage(current, limit);
        //根据条件查询所有数据
        List<SysRole> list = sysRoleMapper.findByPage(sysRoleDto);
        //封装pageInfo对象
        PageInfo<SysRole> pageInfo = new PageInfo<>(list);
        return pageInfo;

    }

    //2.角色添加的方法
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    //3.角色修改的方法
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    //4.角色删除的方法
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    // 5. 分配角色功能--查询所有角色
    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        //查询所有的角色数据
        List<SysRole> sysRoleList = sysRoleMapper.findAllRoles();
        //查询当前用户分配的角色数据
        List<Long> sysRoleIds = sysRoleUserMapper.findSysRoleByUserID(userId);
        //构建响应数据
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("allRolesList", sysRoleList);
        roleMap.put("sysRoleIds", sysRoleIds);
        return roleMap;
    }
}
