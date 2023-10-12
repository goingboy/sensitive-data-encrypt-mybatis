package com.zsx.annotation;

import java.lang.annotation.*;
/**
 * 该注解有两种使用方式
 * ①：配合@SensitiveData加在类中的字段上
 * ②：直接在Mapper中的方法参数上使用
 * @author zhousx
 * @create 2023/10/01-22:45
 **/
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SensitiveField {

}
