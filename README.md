## 基于Mybatis实现敏感数据加密功能

### 方法1： encrypt-intercepter项目 - 利用注解 + mybatis的插件功能

**原理：** 自定义加解密的注解，然后编写拦截器继承自Mybatis的插件intrcepter，在拦截器中判断类和字段是否带有该注解，实现字段自动加解密

**使用：**
1. 查询时，ResultSetInterceptor将要查询的结果解密
2. 插入和更新时，ParameterInterceptor将方法的参数进行加密



### 方法2：encrypt-typeHandler项目 - Myabtsi的Typehandler实现敏感字段加解密

**原理**：自定义一个typeHandler继承自Myabtis的BaseTypeHandler。使用时，只需要改动xml文件

**使用：**
1. 在插入或查询的sql中，需要加密的字段添加typeHandele,
```xml
    <insert id="insertEncrypt">
        insert into user (id, name, phone)
        values (#{id}, #{name}, #{phone,typeHandler=com.zsx.common.CryptoTypeHandler})
    </insert>

    <select id="findByPhone" resultMap="BaseResultMap">
        select id, name, phone
        from user
        where phone = #{phone,typeHandler=com.zsx.common.CryptoTypeHandler}
    </select>
```

2. 在返回结果的resultMap, 需要解密的字段添加typeHandler
```xml
    <resultMap id="BaseResultMap" type="com.zsx.entity.User">
        <id column="id" property="id" />
        <result column="name" property="name"/>
        <result column="phone" property="phone" typeHandler="com.zsx.common.CryptoTypeHandler" />
    </resultMap>
```

> **主要参考链接：**
> 注解 + mybatis插件方式：https://blog.csdn.net/relosy/article/details/123494036
> typeHandler方式：https://blog.csdn.net/qq_39052947/article/details/128148805