package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeVoService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName IndexController
 * @Description
 * @Author Richard
 * @Date 2023-12-07 20:41
 **/
@Tag(name = "用户接口")
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeVoService validateCodeVoService;

    @Autowired
    private SysMenuService sysMenuService;

    //查询用户可以操作的菜单
    @GetMapping("/menus")
    public Result menus(){
        List<SysMenuVo> sysMenuVoList = sysMenuService.findMenuByUserId();
        return Result.build(sysMenuVoList, ResultCodeEnum.SUCCESS);
    }

//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo(){
//        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS);//用户信息已放到ThreadLocal中
//    }
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token ){
        SysUser sysUser = sysUserService.getUserInfo(token);
        return Result.build(sysUser, ResultCodeEnum.SUCCESS);
    }

    @Operation(summary = "登录的方法")
    @PostMapping("login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo loginVo = sysUserService.login(loginDto);
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);

    }

    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeVoService.generateValidateCode();
        return Result.build(validateCodeVo, ResultCodeEnum.SUCCESS);
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(name = "token") String token){
        sysUserService.logout(token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


}
