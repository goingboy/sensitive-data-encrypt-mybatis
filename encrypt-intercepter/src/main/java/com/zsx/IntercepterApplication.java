package com.zsx; /**
 * Project Name: test-zsx
 * File Name: com.zsx.IntercepterApplication
 * Package Name: PACKAGE_NAME
 * Date: 2023/9/13 10:48
 * Copyright (c) 2023 天翼数字生活科技有限公司 All Rights Reserved.
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: zhoushaoxiong
 * @date: 2023/9/13 10:48
 */
@SpringBootApplication
public class IntercepterApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntercepterApplication.class, args);
    }
}
