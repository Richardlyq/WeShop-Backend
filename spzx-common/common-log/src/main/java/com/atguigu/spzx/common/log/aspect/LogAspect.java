package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName LogAspect
 * @Description
 * @Author Richard
 * @Date 2023-12-23 16:28
 **/
@Component
@Aspect
@Slf4j
public class LogAspect {            // 环绕通知切面类定义

    @Autowired
    private AsyncOperLogService asyncOperLogService ;

    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint , Log sysLog) {
//        String title = sysLog.title();
//        log.info("LogAspect...doAroundAdvice方法执行了"+title);
//        System.out.println("LogAspect...doAroundAdvice方法执行了"+title);
        // 构建前置参数
        SysOperLog sysOperLog = new SysOperLog() ;
        LogUtil.beforeHandleLog(sysLog , joinPoint , sysOperLog) ;

        Object proceed = null;
        try {
            proceed = joinPoint.proceed();              // 执行业务方法
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 0 , null) ;
//            System.out.println("在业务方法后执行了");
        } catch (Throwable e) {                         // 代码执行进入到catch中，业务方法执行产生异常
            e.printStackTrace();
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 1 , e.getMessage()) ;
            throw new RuntimeException();
        }
        // 保存日志数据
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed ;                                // 返回执行结果
    }
}