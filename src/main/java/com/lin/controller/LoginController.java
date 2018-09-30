package com.lin.controller;

import com.lin.model.User;
import com.lin.service.UserService;
import com.lin.utils.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lkmc2
 * @date 2018/9/28
 * @description 登陆控制器
 */
@Api(value = "用户登陆的接口", tags = {"登陆的Controller"})
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登陆", notes = "用户登陆的接口")
    @PostMapping("/login")
    public JsonResult login(@RequestBody User user) {

        // 1.判断用户名和密码必须不能为空
        if (StringUtils.isBlank(user.getUsername())
                || StringUtils.isBlank(user.getPassword())) {
            return JsonResult.errorMsg("用户名和密码不能为空");
        }

        // 2.判断用户名和密码是否存在
        User resultUser = userService.queryUserForLogin(user.getUsername(), user.getPassword());

        // 3.登陆失败
        if (resultUser == null) {
            return JsonResult.errorMsg("用户名或密码错误");
        }

        // 清空密码，返回用户信息
        user.setPassword("");
        return JsonResult.ok(resultUser);
    }

}