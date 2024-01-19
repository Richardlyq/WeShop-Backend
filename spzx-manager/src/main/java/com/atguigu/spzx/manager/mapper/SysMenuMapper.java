package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @ClassName SysMenuMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-17 20:26
 **/

@Mapper
public interface SysMenuMapper {
    List<SysMenu> selectAll();

    void insert(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void deleteById(Long id);

    int countByParentId(Long id);

    List<SysMenu> findMenuByUserId(Long id);

    SysMenu selectById(Long parentId);
}
