package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

/**
 * @ClassName AsyncOperLogService
 * @Description
 * @Author Richard
 * @Date 2023-12-23 16:50
 **/

public interface AsyncOperLogService {
    public abstract void saveSysOperLog(SysOperLog sysOperLog) ;
}
