package com.zsx.mapper;



import com.zsx.annotation.EncryptTransaction;
import com.zsx.annotation.SensitiveData;
import com.zsx.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao {

    /**
     * 测试查询 普通参数 拦截器对结果对象进行解密
     * @param id
     * @return
     */
    User getUserById(Long id);

    /**
     * 测试插入 传参为对象，拦截器对含有加密注解的对象的属性 进行自动加密
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 测试查询 查询结果为list 需要对list结果里的对象属性进行解密
     * @return
     */
    List<User> getAllUsers();

    /**
     * 测试查询 普通参数加密
     * @param phone
     * @return
     */
    List<User> getUserByPhone(@EncryptTransaction @Param("phone") String phone);

    /**
     * 测试查询 传参为对象 对象中的phone参数需要拦截器进行加密才能查询
     * @param user
     * @return
     */
    List<User> getUserByUser(User user);


    /**
     * 测试插入 对list进行加密
     * @param users
     * @return
     */
    int insertBatch(List<User> users);

    /**
     * 测试插入 使用@Param注解 对list进行加密
     * @param users
     * @return
     */
    int insertBatchByParam(@Param("users") List<User> users);

    /**
     * 测试插入 普通参数加密，多个需要加密的字段
     * @param name
     * @param email
     * @param phone
     * @return
     */
    int insertUserByParam(@Param("name") String name, @EncryptTransaction @Param("email") String email, @EncryptTransaction @Param("phone") String phone);
}
