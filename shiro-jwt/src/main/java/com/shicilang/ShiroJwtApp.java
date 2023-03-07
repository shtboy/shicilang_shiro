package com.shicilang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author shicilang
 * @date 2023/3/6 16:58
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(value = "com.shicilang.dao")
public class ShiroJwtApp {
    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtApp.class);
        System.out.println("=======启动结束========");
    }
}
