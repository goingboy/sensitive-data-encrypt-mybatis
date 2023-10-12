package com.zsx.service;

import com.zsx.mapper.IUserDao;
import com.zsx.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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

        userDao.insertBatch(users);
//        userDao.insertBatchByParam(users);
        return users;
    }

    @Override
    public int addUserByParam(){
        int result = userDao.insertUserByParam("小二", "111.com", "123411112222");
        return result;
    }

    /**
     * 测试解决防止重复加密问题
     * 重复加密问题：同一个对象在进行过dao层的更新后，进行了一次加密，后续如果再用该对象进行更新操作，又会被加密一次，这导致加密了两次，而且解密不出错
     * @return
     */
    @Override
    public int saveOrUpdateUser(){
        User user = new User(31,"小二", "111.com", "123411112222");
        int result = userDao.updateUserByPrimaryKey(user);
        log.info("user: {}", user);
        if (result != 1){
            result = userDao.addUser(user);
            log.info("user: {}", user);
        }
        return result;
    }
}
