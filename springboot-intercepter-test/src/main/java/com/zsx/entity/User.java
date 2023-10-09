/**
 * Project Name: test-zsx
 * File Name: User
 * Package Name: com.zsx.entity
 * Date: 2023/9/13 11:10
 * Copyright (c) 2023 天翼数字生活科技有限公司 All Rights Reserved.
 */
package com.zsx.entity;

import com.zsx.annotation.Sensitive;
import lombok.Data;

/**
 * @description:
 * @author: zhoushaoxiong
 * @date: 2023/9/13 11:10
 */
@Data
public class User {
    private Long id;

    private String name;

    @Sensitive
    private String phone;

}

