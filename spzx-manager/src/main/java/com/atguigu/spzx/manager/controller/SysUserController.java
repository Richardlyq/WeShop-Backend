package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SysUserController
 * @Description
 * @Author Richard
 * @Date 2023-12-15 20:21
 **/
@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //用户列表分页查询显示
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(@PathVariable("pageNum") Integer pageNum,
                             @PathVariable("pageSize") Integer pageSize,
                             SysUserDto sysUserDto){
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(pageNum, pageSize, sysUserDto);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }

    //用户添加功能
    @PostMapping(value = "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //用户修改功能
    @PutMapping(value = "/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //用户删除功能
    @DeleteMapping(value = "/deleteById/{userId}")
    public Result deleteById(@PathVariable("userId") Long userId){
        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    // 5. 为用户分配角色 - 保存角色数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignRoleDto assignRoleDto){
        sysUserService.doAssign(assignRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

}
