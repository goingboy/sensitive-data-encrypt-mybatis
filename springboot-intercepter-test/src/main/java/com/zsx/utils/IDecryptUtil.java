package com.zsx.utils;

/**
 * @author 沈洋 邮箱:1845973183@qq.com
 * @create 2021/10/26-22:43
 **/
public interface IDecryptUtil {
    /**
     * 解密
     *
     * @param result resultType的实例
     * @return T
     * @throws IllegalAccessException 字段不可访问异常
     */
    <T> T decrypt(T result) throws IllegalAccessException;
}

