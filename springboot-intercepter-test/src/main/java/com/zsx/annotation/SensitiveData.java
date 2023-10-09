package com.zsx.annotation;

import java.lang.annotation.*;
/**
 * 该注解定义在类上
 * 插件通过扫描类对象是否包含这个注解来决定是否继续扫描其中的字段注解
 * 这个注解要配合EncryptTransaction注解
 * @author 沈洋 邮箱:1845973183@qq.com
 * @create 2021/10/26-22:38
 **/
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveData {

}

