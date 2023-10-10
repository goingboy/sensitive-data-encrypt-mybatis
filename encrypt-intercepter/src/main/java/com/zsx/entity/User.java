package com.zsx.entity;

import com.zsx.annotation.EncryptTransaction;
import com.zsx.annotation.SensitiveData;
import lombok.*;
import java.io.Serializable;


/**
 * @author lenovo
 */
@With
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@SensitiveData // 插件只对加了该注解的类进行扫描，只有加了这个注解的类才会生效
public class User implements Serializable {
    private Integer id;
    private String name;

    // 表明对该字段进行加密
    @EncryptTransaction
    private String email;
    // 表明对该字段进行加密
    @EncryptTransaction
    private String phone;

}

