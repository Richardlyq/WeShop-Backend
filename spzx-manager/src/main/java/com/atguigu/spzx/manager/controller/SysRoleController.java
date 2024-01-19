package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName SysRoleController
 * @Description
 * @Author Richard
 * @Date 2023-12-15 10:57
 **/

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //1. 角色列表的方法
    //current：当前页， limit：每页显示的记录数
    // Dto是封装页面传过来的数据， Vo表示返回的数据封装， 实体类表示与数据表的字段一一对应
    @PostMapping("/findByPage/{current}/{limit}")
    public Result findByPage(@PathVariable("current") Integer current,
                             @PathVariable("limit") Integer limit,
                             @RequestBody SysRoleDto sysRoleDto){
    PageInfo<SysRole> pageInfo= sysRoleService.findByPage(sysRoleDto, current, limit);
    return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    @Log(title = "角色添加",businessType = 0) //添加Log注解，设置属性
    // 2.角色添加的功能
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 3.角色修改的功能
    @PutMapping(value = "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 4. 角色删除功能
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 5. 分配角色功能--查询所有角色
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable(value = "userId") Long userId){
        Map<String, Object> roleMap = sysRoleService.findAllRoles(userId);
        return Result.build(roleMap, ResultCodeEnum.SUCCESS);
    }

}
