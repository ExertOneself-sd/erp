package com.erp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.erp.mapper")  //扫描产生Mapper接口代理对象
@EnableCaching
public class ErpBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpBackApplication.class, args);
    }

}
