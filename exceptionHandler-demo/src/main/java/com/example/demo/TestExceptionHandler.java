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
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @description: 测试springboot提供的restController切面，实现异常捕获和处理
 *
 * @RestControllerAdvice 是一个组合注解，由 @ControllerAdvice、@ResponseBody组成，而 @ControllerAdvice继承了@Component，
 * 因此 @RestControllerAdvice本质上是个Component，用于定义 @ExceptionHandler，@InitBinder和 @ModelAttribute方法，
 * 适用于所有使用 @RequestMapping方法。
 *
 * @author: zhoushaoxiong
 * @date: 2023/8/23 16:15
 */
@RestControllerAdvice
public class TestExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST) //设置响应码
    @ExceptionHandler(NumberFormatException.class)
    public String handleExeption(Exception ex) {
        return "接收到数字格式异常";
    }

    //（1）全局数据绑定
    //应用到所有@RequestMapping注解方法
    //此处将键值对添加到全局，注解了@RequestMapping的方法都可以获得此键值对
    @ModelAttribute
    public void addUser(Model model) {
        model.addAttribute("msg", "此处将键值对添加到全局，注解了@RequestMapping的方法都可以获得此键值对");
    }
    //（2）全局数据预处理
    //应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
    //用来设置WebDataBinder
    @InitBinder("user")
    public void initBinder(WebDataBinder binder) {
    }

    // （3）全局异常处理
    //应用到所有@RequestMapping注解的方法，在其抛出Exception异常时执行
    //定义全局异常处理，value属性可以过滤拦截指定异常，此处拦截所有的Exception
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "error";
    }

}
