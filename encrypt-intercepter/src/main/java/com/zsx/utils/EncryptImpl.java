package com.zsx.utils;

import com.zsx.annotation.SensitiveField;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author zhousx
 * @create 2023/10/01-22:45
 **/
@Component
public class EncryptImpl implements IEncryptUtil {

    @Override
    public <T> T encrypt(Field[] declaredFields, T paramsObject) throws IllegalAccessException {
            //取出所有被EncryptTransaction注解的字段
        for (Field field : declaredFields) {
            SensitiveField sensitiveField = field.getAnnotation(SensitiveField.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = field.get(paramsObject);
                //暂时只实现String类型的加密
                if (object instanceof String) {
                    String value = (String) object;

                    //修改: 如果有标识则不加密，没有则加密并加上标识前缀。（防止重复加密）
                    String encrypt = value;

                    //开始对字段加密使用自定义的AES加密工具
                    try {
                        if(!value.startsWith(DBAESUtil.KEY_SENSITIVE)) {
                            encrypt = DBAESUtil.encrypt(value);
                            encrypt = DBAESUtil.KEY_SENSITIVE + encrypt;
                        }
                        //修改字段值
                        field.set(paramsObject, encrypt);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return paramsObject;
    }
}

