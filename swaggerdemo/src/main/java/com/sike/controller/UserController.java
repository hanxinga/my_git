package com.sike.controller;

import com.sike.entity.User;
import com.sike.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "User操作接口")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取所有用户对象", notes = "获取所有用户，无需参数")
    @RequestMapping(value = "/findAllUsers", method = {RequestMethod.POST, RequestMethod.GET})
    public List<User> findAllUsers() {
        //查出的所有用户信息
        return userService.findAllUsers();
    }

    @ApiOperation(value = "根据姓名查找用户对象", notes = "根据姓名查找用户对象")
    @RequestMapping(value = "/findByName", method = {RequestMethod.POST, RequestMethod.GET})
    @ApiImplicitParams({@ApiImplicitParam(name = "username", value = "用户名"),
            @ApiImplicitParam(name = "password", value = "密码")})
    public User findByName(@ApiParam(value = "姓名", example = "张三", required = true) String username) {
        return userService.findUserByName(username);
    }

}

