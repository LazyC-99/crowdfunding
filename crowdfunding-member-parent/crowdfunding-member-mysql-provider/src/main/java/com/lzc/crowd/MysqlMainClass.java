package com.lzc.crowd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(value = "com.lzc.crowd.mapper")
@SpringBootApplication
public class MysqlMainClass {
    public static void main(String[] args) {
        SpringApplication.run(MysqlMainClass.class,args);
    }
}
