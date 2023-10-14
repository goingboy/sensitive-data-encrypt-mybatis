package com.zsx;

import com.zsx.dao.UserDao;
import com.zsx.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class EncryptHTypeHandlerTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void testInsert(){
        User user = new User();
        user.setPhone("11112222333");
        user.setName("zsx");
        int result = userDao.insertEncrypt(user);
        System.out.println(result);
    }

    @Test
    public void testSelectByName(){
        User user = new User();
        user.setName("zsx");
        List<User> userList = userDao.findByName(user);
        System.out.println(userList.toString());
    }

    @Test
    public void testSelectByPhone(){
        User user = new User();
        user.setPhone("11112222333");
        List<User> userList = userDao.findByPhone(user);
        System.out.println(userList.toString());
    }

    @Test
    public void testSelectByPhone2(){
        List<User> userList = userDao.findByPhone2("11112222333");
        System.out.println(userList.toString());
    }

}
