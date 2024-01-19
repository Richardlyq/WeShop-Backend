package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.manager.mapper.SysOperLogMapper;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName AsyncOperLogServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-23 16:51
 **/
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
