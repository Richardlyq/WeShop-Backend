package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.utils.MenuHelper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName SysMenuServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-17 20:26
 **/

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    //查询所有菜单，菜单列表接口(封装树形菜单的数据)
    @Override
    public List<SysMenu> findNodes() {
        //1.查询所有菜单，返回list集合
        List<SysMenu> sysMenuList = sysMenuMapper.selectAll();
        if (CollectionUtils.isEmpty(sysMenuList)) {
            return null;
        }
        //2.调用工具类的方法，将返回的list集合封装成要求的数据格式（菜单是分层的!）
        List<SysMenu> treeList = MenuHelper.buildTree(sysMenuList);
        return treeList;
    }

    //菜单添加接口
    @Override
    public void save(SysMenu sysMenu) {
        //添加新的菜单
        sysMenuMapper.insert(sysMenu);
        //新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenuIsHalf(sysMenu);
    }

    //将父菜单的id改为半开状态
    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {
        //查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectById(sysMenu.getParentId());
        if(parentMenu != null){
            //将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            //递归调用
            updateSysRoleMenuIsHalf(parentMenu);
        }
    }

    //菜单修改接口
    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    //菜单删除接口
    @Override
    public void removeById(Long id) {
        //先查询是否存在子菜单，如果存在，则不允许直接删除
        int count = sysMenuMapper.countByParentId(id);
        if (count > 0) {
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.deleteById(id);
    }

    //根据用户动态分配菜单
    @Override
    public List<SysMenuVo> findMenuByUserId() {
        //从拦截器绑定的threadLocal中得到用户
        SysUser sysUser = AuthContextUtil.get();
        //根据用户id得到该用户的菜单集合
        List<SysMenu> sysMenuList = sysMenuMapper.findMenuByUserId(sysUser.getId());
        //构建按要求返回的树形结构数据（菜单分层）
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        //将List<SysMenu>对象转换成List<SysMenuVo>对象
        List<SysMenuVo> sysMenuVoList = this.buildMenus(sysMenuTreeList);

        return sysMenuVoList;
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
