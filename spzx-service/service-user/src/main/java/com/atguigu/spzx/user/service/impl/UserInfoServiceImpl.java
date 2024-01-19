package com.atguigu.spzx.user.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName UserInfoServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-26 12:57
 **/
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //用户注册功能
    @Override
    public void register(UserRegisterDto userRegisterDto) {
        //获取数据
        String username = userRegisterDto.getUsername();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();
        String password = userRegisterDto.getPassword();

        //校验参数
        if(!StringUtils.hasText(username) || !StringUtils.hasText(nickName)
           ||!StringUtils.hasText(code) || !StringUtils.hasText(password) ){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        //校验验证码
        String validateCode = redisTemplate.opsForValue().get("phone:code:" + username);
        if(!code.equals(validateCode)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //根据用户名查询用户信息，校验用户名（手机号）是否存在
        UserInfo userInfoExist = userInfoMapper.getUserByUserName(username);
        if(userInfoExist != null){//用户已经存在
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        userInfoMapper.save(userInfo);

        //删除redis中的验证码
        redisTemplate.delete("phone:code:" + username);
    }

    //用户登录功能
    @Override
    public String login(UserLoginDto userLoginDto) {
        //获得传过来的数据信息
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if(!StringUtils.hasText(username) || !StringUtils.hasText(password)){
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        //根据用户Dto查询用户信息
        UserInfo userInfo = userInfoMapper.getUserByUserName(username);
        if(userInfo == null){
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //校验密码
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!md5DigestAsHex.equals(userInfo.getPassword())){
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //校验是否被禁用
        if(userInfo.getStatus() == 0){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }

        //生成token并返回
        String token = UUID.randomUUID().toString().replaceAll("-","");
        String userInfoJson = JSON.toJSONString(userInfo);
        //将用户信息放入redis中
        redisTemplate.opsForValue().set("user:spzx:"+token,userInfoJson,7, TimeUnit.DAYS);
        return token;
    }

    //获取当前用户信息
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        //查询redis判断用户是否登录
//        String userInfoJson = redisTemplate.opsForValue().get("user:spzx:" + token);
//        if(!StringUtils.hasText(userInfoJson)){ //查不到，用户没登陆
//            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH) ;
//        }
//        //将获取的userInfoJson转成UserInfo
//        UserInfo userInfo = JSON.parseObject(userInfoJson, UserInfo.class);

        //定义拦截器，从ThreadLocal中获取用户信息
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        UserInfoVo userInfoVo = new UserInfoVo();
        //将userInfo中的对应的属性赋值给userInfoVo
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }
}
