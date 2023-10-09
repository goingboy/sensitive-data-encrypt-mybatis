package com.zsx.mapper;

import com.zsx.annotation.Sensitive;
import com.zsx.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
public interface IUserDao {


    User getUserById(Long id);

    @Sensitive
    int addUser(User user);
}
