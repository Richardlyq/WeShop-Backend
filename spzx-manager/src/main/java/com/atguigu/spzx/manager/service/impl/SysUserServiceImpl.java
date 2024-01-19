package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssignRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName SysUserServiceImpl
 * @Description
 * @Author Richard
 * @Date 2023-12-07 20:43
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    //传入数据进行登录
    @Override
    public LoginVo login(LoginDto loginDto) {
        //校验验证码
        String captcha = loginDto.getCaptcha(); //用户输入的验证码
        String codeKey = loginDto.getCodeKey(); //redis中验证码的输入key
        //从redis中获取验证码并进行校验
        String redisCode = redisTemplate.opsForValue().get("user:login:validatecode:" + codeKey);
        if (StrUtil.isEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode , captcha)){
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //验证通过，删除redis内的验证码
        redisTemplate.delete("user:login:validatecode:" + codeKey);

        //1. 根据用户名查询用户
        SysUser sysUser = sysUserMapper.selectByUserName(loginDto.getUserName());
        if (sysUser == null){
//            throw new RuntimeException("用户名不存在");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //2. 验证密码是否正确
        String inputPassword = loginDto.getPassword();
        String md5InputPassword = DigestUtils.md5DigestAsHex(inputPassword.getBytes());
        if (!md5InputPassword.equals(sysUser.getPassword())){
//            throw new RuntimeException("密码不正确");
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //3. 生成令牌，保存数据到Redis中
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:login"+token, JSON.toJSONString(sysUser),
                7, TimeUnit.DAYS);

        //4. 构建响应结果对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);

        return loginVo;
    }

    //根据token获取用户信息
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    //退出登录
    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login" + token);
    }

    // 用户列表分页查询显示
    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum, pageSize);
        List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //用户添加功能
    @Override
    public void saveSysUser(SysUser sysUser) {
        //判断用户名不能存在
        SysUser dbSysUser = sysUserMapper.selectByUserName(sysUser.getUserName());
        if (dbSysUser != null){
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //对密码进行加密
        String password = sysUser.getPassword();
        String digestPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        sysUser.setPassword(digestPassword);
        sysUser.setStatus(1);
        sysUserMapper.saveSysUser(sysUser);
    }

    //用户删除功能
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    //用户修改功能
    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.updateSysUser(sysUser);
    }

    // 5. 为用户分配角色 - 保存角色数据
    @Override
    public void doAssign(AssignRoleDto assignRoleDto) {
        //根据用户id删除之前用户对应的角色数据
        sysRoleUserMapper.deleteById(assignRoleDto.getUserId());

        //分配新的数据角色
        List<Long> roleIdList = assignRoleDto.getRoleIdList();
        for(Long roleId:roleIdList){
            sysRoleUserMapper.doAssign(assignRoleDto.getUserId(), roleId);
        }

    }


}
