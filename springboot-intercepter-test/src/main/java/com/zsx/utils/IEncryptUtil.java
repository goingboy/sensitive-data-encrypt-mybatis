package com.zsx.utils;

import java.lang.reflect.Field;

/**
 * @author 沈洋 邮箱:1845973183@qq.com
 * @create 2021/10/26-22:41
 **/
public interface IEncryptUtil {
    /**
     * 加密
     *
     * @param declaredFields 加密字段
     * @param paramsObject   对象
     * @param <T>            入参类型
     * @return 返回加密
     * @throws IllegalAccessException 不可访问
     */
    <T> T encrypt(Field[] declaredFields, T paramsObject) throws IllegalAccessException;
}
