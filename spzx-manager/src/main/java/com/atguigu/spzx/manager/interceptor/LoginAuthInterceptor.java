package com.atguigu.spzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoginAuthInterceptor
 * @Description
 * @Author Richard
 * @Date 2023-12-14 22:12
 **/
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断方法是不是预检方法，是：放行
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }

        if (request.getRequestURI().contains("favicon.ico")){
            return true;
        }

        if (handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //获取token,如果token为空，拦截
        String token = request.getHeader("token");
        System.out.println("token//////////////////////////////////////////"+token);
        System.out.println("request/////////////////////////////////////"+request.getServletPath());
        if(StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            return false;
        }

        //根据token获取用户信息，若用户信息为空，拦截
        String sysUserInfoJson = redisTemplate.opsForValue().get("user:login" + token);
        System.out.println("sysUserInfoJson///////////////////////////////////" + sysUserInfoJson);
        if(StrUtil.isEmpty(sysUserInfoJson)){
            responseNoLoginInfo(response);
            return false;
        }

        //得到用户信息之后，存入ThreadLocal中
        SysUser sysUser = JSON.parseObject(sysUserInfoJson, SysUser.class);
        AuthContextUtil.set(sysUser);

        //重置Redis中的用户数据的有效时间
        redisTemplate.expire("user:login" + token, 30, TimeUnit.MINUTES);
        //放行
        return true;
    }

    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        AuthContextUtil.remove(); //移除threadLocal中的数据
    }

}
