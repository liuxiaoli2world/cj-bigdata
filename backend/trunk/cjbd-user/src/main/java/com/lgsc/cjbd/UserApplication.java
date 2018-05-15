package com.lgsc.cjbd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 用户模块
 * Created by zzq on 2017/3/7.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@RefreshScope
@MapperScan(basePackages = "com.lgsc.cjbd.user.mapper")
//启注解事务管理
@EnableTransactionManagement
public class UserApplication {

    public static void main(String[] args) {
    	SpringApplication.run(UserApplication.class, args);
    }

}