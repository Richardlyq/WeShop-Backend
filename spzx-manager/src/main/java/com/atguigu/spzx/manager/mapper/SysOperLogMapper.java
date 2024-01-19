package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName SysOperLogMapper
 * @Description
 * @Author Richard
 * @Date 2023-12-23 16:52
 **/
@Mapper
public interface SysOperLogMapper {
    void insert(SysOperLog sysOperLog);
}
