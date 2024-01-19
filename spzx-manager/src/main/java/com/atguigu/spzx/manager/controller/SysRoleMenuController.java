package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssignMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName SysRoleMenuController
 * @Description
 * @Author Richard
 * @Date 2023-12-18 9:10
 **/
@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    //1.查询所有菜单 和 查询角色分配过菜单id列表
    @GetMapping(value = "/findSysRoleMenuByRoleId/{roleId}")
    public Result findSysRoleMenuByRoleId(@PathVariable("roleId") Long roleId){
        Map<String, Object> sysRoleMenuMap = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(sysRoleMenuMap, ResultCodeEnum.SUCCESS);
    }

    //2.保存角色分配菜单数据
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignMenuDto assignMenuDto){
        sysRoleMenuService.doAssign(assignMenuDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
