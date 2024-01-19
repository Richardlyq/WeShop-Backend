package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysMenuController
 * @Description
 * @Author Richard
 * @Date 2023-12-17 20:25
 **/

@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //菜单删除接口
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable("id") Long id){
        sysMenuService.removeById(id);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //菜单修改接口
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    //菜单添加接口
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    //菜单列表接口
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }
}
