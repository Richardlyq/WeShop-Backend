package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MenuHelper
 * @Description
 * @Author Richard
 * @Date 2023-12-17 21:40
 **/

    
public class MenuHelper {
    
    /*
     * @description: 使用递归的方法建立要求的树形数据（分层的菜单）
     * @author: liyuqi 
     * @date:  22:16 2023/12/17
     * @param: [sysMenuList]
     * @return: java.util.List<com.atguigu.spzx.model.entity.system.SysMenu>
     **/
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> treeList = new ArrayList<>();
        //找到递归的入口
        for (SysMenu sysMenu:sysMenuList){
            if (sysMenu.getParentId().longValue() == 0) {
                treeList.add(findChildren(sysMenu, sysMenuList));
            }
        }
        return treeList;
    }

    /*
     * @description: 递归查找子节点
     * @author: liyuqi
     * @date:  22:11 2023/12/17
     * @param: [sysMenu, sysMenuList]
     * @return: com.atguigu.spzx.model.entity.system.SysMenu
     **/
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //初始化,sysMenu中有属性List<SysMenu> children
        sysMenu.setChildren(new ArrayList<>());
        //遍历集合中属于当前sysMenu的子菜单
        for (SysMenu sysMenuChild:sysMenuList){
            if (sysMenu.getId().longValue() == sysMenuChild.getParentId().longValue()) {
                sysMenu.getChildren().add(findChildren(sysMenuChild, sysMenuList));
            }
        }
        return sysMenu;
    }
}
