/**
 * Project Name: test-zsx
 * File Name: TestController
 * Package Name: com.example.demo
 * Date: 2023/8/23 16:19
 * Copyright (c) 2023 天翼数字生活科技有限公司 All Rights Reserved.
 */
package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: zhoushaoxiong
 * @date: 2023/8/23 16:19
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        int a = Integer.valueOf("aaa");
        return "ok";
    }
}
