package com.zsx.utils;

/**
 * @author zhousx
 * @create 2023/10/01-22:45
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

