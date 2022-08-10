package com.example.download1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
//@MapperScan("xx.xx.xx.dao")
public class Download1Application {

    public static void main(String[] args) {
        SpringApplication.run(Download1Application.class, args);
    }

}
