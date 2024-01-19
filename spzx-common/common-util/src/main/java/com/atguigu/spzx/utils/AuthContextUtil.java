package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;

/**
 * @ClassName AuthContextUtil
 * @Description
 * @Author Richard
 * @Date 2023-12-14 21:53
 **/

public class AuthContextUtil {

    //创建ThreadLocal<UserInfo>对象
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>() ;


    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }


    //创建threadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    //定义存储数据的静态方法
    public static void set(SysUser sysUser){
        threadLocal.set(sysUser);
    }

    //定义获取数据的方法
    public static SysUser get(){
        return threadLocal.get();
    }

    //删除数据的方法
    public static void remove(){
        threadLocal.remove();
    }
}
