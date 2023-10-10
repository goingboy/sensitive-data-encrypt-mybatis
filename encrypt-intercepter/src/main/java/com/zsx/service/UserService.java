package com.zsx.service;

import com.zsx.mapper.IUserDao;
import com.zsx.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> users = userDao.getAllUsers();
        return users;
    }

    @Override
    public List<User> getUserByPhone(String phone){
        List<User> users = userDao.getUserByPhone(phone);
        return users;
    }

    @Override
    public List<User> getUser(String phone){
        User user = new User();
        user.setPhone(phone);
        List<User> users = userDao.getUserByUser(user);
        return users;
    }

    @Override
    public List<User> addUserList(){
        List<User> users = new ArrayList();
        users.add(new User(4,"小二", "111.com", "123411112222"));
        users.add(new User(5,"小三", "222.com", "123411113333"));
        users.add(new User(6,"小四", "333.com", "123411114444"));

//        userDao.insertBatch(users);
        userDao.insertBatchByParam(users);
        return users;
    }

    @Override
    public int addUserByParam(){
        int result = userDao.insertUserByParam("小二", "111.com", "123411112222");
        return result;
    }
}
