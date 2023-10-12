/**
 * Project Name: test-zsx
 * File Name: IUserService
 * Package Name: com.zsx.service
 * Date: 2023/9/13 11:23
 * Copyright (c) 2023 天翼数字生活科技有限公司 All Rights Reserved.
 */
package com.zsx.service;

import com.zsx.entity.User;

import java.util.List;

/**
 * @description:
 * @author: zhoushaoxiong
 * @date: 2023/9/13 11:23
 */
public interface IUserService {

    User getUserById(Long id);

    void addUser(User user);

    List<User> getAllUser();

    List<User> getUserByPhone(String phone);

    List<User> getUser(String phone);

    List<User> addUserList();

    int addUserByParam();

    int saveOrUpdateUser();
}
