package com.zsx.utils;

import com.zsx.annotation.SensitiveField;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author zhousx
 * @create 2023/10/01-22:45
 **/
@Component
public class DecryptImpl implements IDecryptUtil {

    /**
     * 解密
     *
     * @param result resultType的实例
     */
    @Override
    public <T> T decrypt(T result) throws IllegalAccessException {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = resultClass.getDeclaredFields();
        for (Field field : declaredFields) {
            //取出所有被DecryptTransaction注解的字段
            SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = field.get(result);
                //String的解密
                if (object instanceof String) {
                    String value = (String) object;
                    //对注解的字段进行逐一解密
                    try {
                        //修改：没有标识则不解密(防止重复解密)
                        if(value.startsWith(DBAESUtil.KEY_SENSITIVE)) {
                            value = value.substring(10);
                            value = DBAESUtil.decrypt(value);
                        }
                        //修改字段值
                        field.set(result, value);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return result;
    }
}
