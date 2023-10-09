package com.zsx.annotation;

import java.lang.annotation.*;
/**
 * 该注解有两种使用方式
 * ①：配合@SensitiveData加在类中的字段上
 * ②：直接在Mapper中的方法参数上使用
 * @author 沈洋 邮箱:1845973183@qq.com
 * @create 2021/10/26-22:40
 **/
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncryptTransaction {

}
