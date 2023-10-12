/**
 * Project Name: test-zsx
 * File Name: UserController
 * Package Name: com.zsx.controller
 * Date: 2023/9/13 11:21
 * Copyright (c) 2023 天翼数字生活科技有限公司 All Rights Reserved.
 */
package com.zsx.controller;

import com.alibaba.fastjson2.JSON;
import com.zsx.entity.User;
import com.zsx.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description: 测试mybatis拦截器+注解 实现数据的自动加解密功能
 * @author: zhoushaoxiong
 * @date: 2023/9/13 11:21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 插入一条记录
     * @param user
     * @return
     */
    @PostMapping("/add")
    public String addUser(@RequestBody User user){
        userService.addUser(user);
        return "success";
    }

    /**
     * 查询一条记录
     * @param id
     * @return
     */
    @PostMapping("/get/one")
    public String getUser(Long id){
        User user = userService.getUserById(id);
        return user.toString();
    }

    /**
     * 查询全部
     * @return
     */
    @PostMapping("/get/list")
    public String getUserAll(){
        List<User> users = userService.getAllUser();
        return JSON.toJSONString(users);
    }

    /**
     * 通过手机号查询
     * @param phone
     * @return
     */
    @PostMapping("/get/phone")
    public String getUserByPhone(String phone){
        List<User> users = userService.getUserByPhone(phone);
        return JSON.toJSONString(users);
    }

    /**
     * 通过对象查询
     * @param phone
     * @return
     */
    @PostMapping("/get/user/phone")
    public String getUserByUserPhone(String phone){
        List<User> users = userService.getUser(phone);
        return JSON.toJSONString(users);
    }

    /**
     * 批量插入
     * @return
     */
    @PostMapping("/add/list")
    public String addUserList(){
        List<User> users = userService.addUserList();
        return JSON.toJSONString(users);
    }

    /**
     * 插入 dao使用@Param注解
     * @return
     */
    @PostMapping("/add/user/param")
    public String addUserParam(){
        int result = userService.addUserByParam();
        return JSON.toJSONString(result);
    }

    /**
     * 插入 dao层多次操作同一对象时，解决重复加密问题
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public String saveOrUpdateUser(){
        int result = userService.saveOrUpdateUser();
        return JSON.toJSONString(result);
    }
}
