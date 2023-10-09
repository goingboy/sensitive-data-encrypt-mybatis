/**
 * Project Name: test-zsx
 * File Name: UserController
 * Package Name: com.zsx.controller
 * Date: 2023/9/13 11:21
 * Copyright (c) 2023 天翼数字生活科技有限公司 All Rights Reserved.
 */
package com.zsx.controller;

import com.zsx.entity.User;
import com.zsx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhoushaoxiong
 * @date: 2023/9/13 11:21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody User user){
        userService.addUser(user);
        return "success";
    }

    @PostMapping("/get")
    public String getUser(Long id){
        User user = userService.getUserById(id);
        return user.toString();
    }
}
