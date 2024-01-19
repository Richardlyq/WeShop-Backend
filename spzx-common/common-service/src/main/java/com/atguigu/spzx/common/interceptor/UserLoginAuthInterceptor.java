package com.atguigu.spzx.common.interceptor;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.utils.AuthContextUtil;
import io.netty.util.internal.UnstableApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @ClassName UserLoginAuthInterceptor
 * @Description
 * @Author Richard
 * @Date 2023-12-26 21:46
 **/
//定义一个拦截器
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String userInfoJSON = redisTemplate.opsForValue().get("user:spzx:" + token);
        //将用户信息放入ThreadLocal里面
        UserInfo userInfo = JSON.parseObject(userInfoJSON, UserInfo.class);
        AuthContextUtil.setUserInfo(userInfo);
        return true;
    }
}
