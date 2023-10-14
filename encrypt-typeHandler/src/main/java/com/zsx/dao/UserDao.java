package com.zsx.dao;
 
import com.zsx.entity.User;
import org.apache.ibatis.annotations.Mapper;
 
import java.util.List;

/**
 * userMapper
 */
@Mapper
public interface UserDao {
 
    int insertEncrypt(User user);
 
    List<User> findByName(User user);

    List<User> findByPhone(User user);

    List<User> findByPhone2(String phone);

}