/**
 * Project Name: test-zsx
 * File Name: TestExceptionHandler
 * Package Name: com.example.demo.test
 * Date: 2023/8/23 16:15
 * Copyright (c) 2023 天翼数字生活科技有限公司 All Rights Reserved.
 */
package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @description:
 * @author: zhoushaoxiong
 * @date: 2023/8/23 16:15
 */
@RestControllerAdvice
public class TestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public String handleExeption(Exception ex) {
        return "接收到异常";
    }

}
